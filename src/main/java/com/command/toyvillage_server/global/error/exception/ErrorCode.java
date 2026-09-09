package com.command.toyvillage_server.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    METHOD_NOT_ALLOWED(405, "지원하지 않는 메서드 형식입니다."),

    // popup
    POPUP_NOT_FOUND(404, "존재하지 않는 팝업입니다."),

    // auth
    LOGIN_INFO_NOT_MATCHED(401, "아이디 또는 비밀번호를 확인해주세요"),
    EMAIL_NOT_MATCHED(400, "요청의 이메일이 인증된 사용자의 이메일과 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(404, "인증 코드가 만료되었습니다."),
    VERIFICATION_CODE_NOT_MATCHED(400, "인증 코드가 일치하지 않습니다."),
    RESET_TOKEN_NOT_FOUND(401, "유효하지 않은 비밀번호 재설정 토큰 입니다."),
    MANY_REQUEST(429, "5분 뒤에 다시 시도해주세요."),

    // jwt
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "refreshToken이 존재하지 않습니다."),

    //faq
    FAQ_NOT_FOUND(404, "존재하지 않는 질문 입니다."),

    // animal
    ANIMAL_NOT_FOUND(404, "존재하지 않는 동물소개 입니다."),

    // web administrator
    WEB_ADMIN_NOT_FOUND(404, "해당 웹 관리자가 존재하지 않습니다."),
    WEB_ADMIN_EXIST(409, "이미 가입된 웹 관리자 이메일입니다."),

    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),

    //event
    EVENT_NOT_FOUND(404, "해당 이벤트가 존재하지 않습니다."),

    //news
    NEWS_NOT_FOUND(404, "해당 뉴스가 존재하지 않습니다."),

    // general
    BAD_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류가 발생했습니다."),

    //aws s3
    FILE_EMPTY(400, "파일이 비어있습니다."),
    KEY_EMPTY(400, "해당 파일의 키가 비어있습니다."),
    FILE_UPLOAD_FAIL(500, "파일을 업로드하는중 문제가 발생했습니다."),
    FILE_DELETE_FAIL(500, "파일을 삭제하던 중 문제가 발생했습니다."),

    //partnership
    PARTNERSHIP_NOT_FOUND(404, "해당 제휴문의를 찾을 수 없습니다."),

    //file
    FILE_NOT_FOUND(404, "파일을 찾을 수 없습니다."),

    //gallery
    GALLERY_NOT_FOUND(404, "존재하지 않는 갤러리입니다."),

    //notice
    NOTICE_NOT_FOUND(404, "존재하지 않는 공지사항입니다."),

    // reservation
    RESERVATION_NOT_FOUND(404, "존재하지 않는 단체예약 목록입니다."),
    RESERVATION_PERMISSION_NOT_FOUND(404, "존재하지 않는 단체예약 조회 권한입니다."),
    RESERVATION_INVALID_TIME(400, "퇴장 시간은 입장 시간보다 빠를 수 없습니다."),
    RESERVATION_INVALID_DATE(400, "사전답사일은 방문일보다 늦을 수 없습니다."),

    // app admin
    APP_ADMIN_NOT_FOUND(404, "존재하지 않는 앱 관리자입니다."),
    APP_ADMIN_EXIST(409, "이미 사용 중인 앱 관리자 아이디입니다."),

    //document
    DOCUMENT_NOT_FOUND(404, "존재하지 않는 자료입니다."),
  
    // close day
    CLOSE_DAY_NOT_FOUND(404, "존재하지 않는 휴관일입니다."),
    CLOSE_DAY_INVALID_PERIOD(400, "휴관 종료일은 휴관 시작일보다 빠를 수 없습니다."),

    // open time
    OPEN_TIME_NOT_FOUND(404, "존재하지 않는 운영시간입니다."),
    OPEN_TIME_INVALID_PERIOD(400, "운영 종료시간은 운영 시작시간보다 빠를 수 없습니다."),

    // team
    TEAM_NOT_FOUND(404, "존재하지 않는 팀입니다."),
    JOIN_TEAM_TARGET_INVALID(400, "팀에 배정할 수 없는 사용자입니다."),

    // work
    WORK_NOT_FOUND(404, "존재하지 않는 업무관리입니다."),
    WORK_REPORT_ALREADY_EXISTS(409, "해당 업무에 대한 업무 보고가 이미 존재합니다."),
    WORK_ALREADY_APPROVED(409, "이미 승인된 업무관리입니다."),
    WORK_ALREADY_REJECTED(409, "이미 반려된 업무관리입니다."),

    // task
    TASK_NOT_FOUND(404, "존재하지 않는 업무지시입니다."),
    TASK_TARGET_INVALID(400, "업무지시 대상이 올바르지 않습니다.");

    private final int statusCode;
    private final String errorMessage;
}
