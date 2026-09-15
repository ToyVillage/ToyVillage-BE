package com.command.toyvillage_server.domain.app.join_team.presentation;

import com.command.toyvillage_server.domain.app.join_team.presentation.dto.request.TeamRequest;
import com.command.toyvillage_server.domain.app.join_team.service.JoinTeamService;
import com.command.toyvillage_server.domain.app.join_team.service.LeaveTeamService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/join-team")
@RequiredArgsConstructor
public class JoinTeamController {
    private final JoinTeamService joinTeamService;
    private final LeaveTeamService leaveTeamService;

    @PostMapping("/{teamId}")
    public ResponseEntity<MessageResponse> joinTeam(
            @RequestBody @Valid TeamRequest request,
            @PathVariable Long teamId
    ) {
        joinTeamService.execute(request, teamId);

        return ResponseEntity.ok(
                MessageResponse.of("유저가 팀에 배정되었습니다.")
        );
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<MessageResponse> leaveTeam(
            @RequestBody @Valid TeamRequest request,
            @PathVariable Long teamId
    ) {
        leaveTeamService.execute(request, teamId);

        return ResponseEntity.ok(
                MessageResponse.of("팀 배정이 해제되었습니다.")
        );
    }
}
