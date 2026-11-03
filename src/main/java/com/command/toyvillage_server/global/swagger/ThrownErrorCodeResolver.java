package com.command.toyvillage_server.global.swagger;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Handle;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.asm.Type;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 컨트롤러 메서드에서 시작해 호출 그래프를 바이트코드 수준으로 따라가며
 * 실제로 던질 수 있는 {@link ErrorCode} 를 수집한다.
 *
 * 수집 규칙
 * 1. GETSTATIC ErrorCode.X            -> X 수집 (throw new ToyVillageException(ErrorCode.X) 패턴)
 * 2. GETSTATIC XxxException.EXCEPTION  -> XxxException 클래스 전체를 스캔해 ErrorCode 수집
 * 3. NEW XxxException                  -> 위와 동일
 * 4. 프로젝트 패키지 내부 메서드 호출     -> 재귀 추적 (람다 포함)
 *
 * 프로젝트 외부(java.*, spring 등) 호출은 따라가지 않는다.
 */
@Slf4j
@Component
public class ThrownErrorCodeResolver {
    private static final String BASE_PACKAGE = "com/command/toyvillage_server/";
    private static final String ERROR_CODE_INTERNAL = Type.getInternalName(ErrorCode.class);
    private static final int MAX_DEPTH = 8;

    private final ClassLoader classLoader = ThrownErrorCodeResolver.class.getClassLoader();
    private final Map<String, byte[]> classBytesCache = new HashMap<>();
    private final Map<String, Boolean> exceptionTypeCache = new HashMap<>();
    private final Map<String, Set<ErrorCode>> exceptionClassCache = new HashMap<>();

    public Set<ErrorCode> resolve(Method handlerMethod) {
        Set<ErrorCode> result = EnumSet.noneOf(ErrorCode.class);
        Set<String> visited = new HashSet<>();
        Deque<MethodRef> queue = new ArrayDeque<>();

        queue.add(new MethodRef(
                Type.getInternalName(handlerMethod.getDeclaringClass()),
                handlerMethod.getName(),
                Type.getMethodDescriptor(handlerMethod),
                0
        ));

        while (!queue.isEmpty()) {
            MethodRef ref = queue.poll();
            if (ref.depth() > MAX_DEPTH || !visited.add(ref.key())) {
                continue;
            }
            scanMethod(ref, result, queue);
        }

        return result;
    }

    private void scanMethod(MethodRef ref, Set<ErrorCode> result, Deque<MethodRef> queue) {
        byte[] bytes = readClass(ref.owner());
        if (bytes == null) {
            return;
        }

        new ClassReader(bytes).accept(new ClassVisitor(SpringAsmInfo.ASM_VERSION) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (!name.equals(ref.name()) || !descriptor.equals(ref.descriptor())) {
                    return null;
                }
                return new CollectingMethodVisitor(ref, result, queue);
            }
        }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
    }

    private class CollectingMethodVisitor extends MethodVisitor {
        private final MethodRef current;
        private final Set<ErrorCode> result;
        private final Deque<MethodRef> queue;

        CollectingMethodVisitor(MethodRef current, Set<ErrorCode> result, Deque<MethodRef> queue) {
            super(SpringAsmInfo.ASM_VERSION);
            this.current = current;
            this.result = result;
            this.queue = queue;
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            if (opcode != Opcodes.GETSTATIC) {
                return;
            }
            if (owner.equals(ERROR_CODE_INTERNAL)) {
                addErrorCode(name, result);
                return;
            }
            if (isProjectClass(owner) && isToyVillageException(owner)) {
                result.addAll(errorCodesOfExceptionClass(owner));
            }
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW && isProjectClass(type) && isToyVillageException(type)) {
                result.addAll(errorCodesOfExceptionClass(type));
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (isProjectClass(owner) && !isToyVillageException(owner)) {
                queue.add(new MethodRef(owner, name, descriptor, current.depth() + 1));
            }
        }

        @Override
        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
                                           Object... bootstrapMethodArguments) {
            // 람다 본문(lambda$execute$0 등)은 같은 클래스의 synthetic 메서드로 컴파일된다.
            for (Object arg : bootstrapMethodArguments) {
                if (arg instanceof Handle handle && isProjectClass(handle.getOwner())) {
                    queue.add(new MethodRef(handle.getOwner(), handle.getName(), handle.getDesc(),
                            current.depth() + 1));
                }
            }
        }
    }

    /** 예외 클래스 전체(생성자, static 초기화 포함)를 스캔해 참조하는 ErrorCode 를 모두 수집한다. */
    private Set<ErrorCode> errorCodesOfExceptionClass(String internalName) {
        return exceptionClassCache.computeIfAbsent(internalName, owner -> {
            Set<ErrorCode> codes = EnumSet.noneOf(ErrorCode.class);
            byte[] bytes = readClass(owner);
            if (bytes == null) {
                return codes;
            }
            new ClassReader(bytes).accept(new ClassVisitor(SpringAsmInfo.ASM_VERSION) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                 String signature, String[] exceptions) {
                    return new MethodVisitor(SpringAsmInfo.ASM_VERSION) {
                        @Override
                        public void visitFieldInsn(int opcode, String fOwner, String fName, String fDesc) {
                            if (opcode == Opcodes.GETSTATIC && fOwner.equals(ERROR_CODE_INTERNAL)) {
                                addErrorCode(fName, codes);
                            }
                        }
                    };
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return codes;
        });
    }

    private void addErrorCode(String name, Set<ErrorCode> target) {
        try {
            target.add(ErrorCode.valueOf(name));
        } catch (IllegalArgumentException ignored) {
            // $VALUES 같은 enum 내부 필드
        }
    }

    private boolean isProjectClass(String internalName) {
        return internalName.startsWith(BASE_PACKAGE);
    }

    private boolean isToyVillageException(String internalName) {
        return exceptionTypeCache.computeIfAbsent(internalName, name -> {
            try {
                Class<?> clazz = Class.forName(name.replace('/', '.'), false, classLoader);
                return ToyVillageException.class.isAssignableFrom(clazz);
            } catch (ClassNotFoundException | LinkageError e) {
                return false;
            }
        });
    }

    private byte[] readClass(String internalName) {
        return classBytesCache.computeIfAbsent(internalName, name -> {
            try (InputStream in = classLoader.getResourceAsStream(name + ".class")) {
                return in == null ? null : in.readAllBytes();
            } catch (IOException e) {
                log.warn("클래스 바이트코드를 읽지 못했습니다: {}", name, e);
                return null;
            }
        });
    }

    private record MethodRef(String owner, String name, String descriptor, int depth) {
        String key() {
            return owner + "." + name + descriptor;
        }
    }
}
