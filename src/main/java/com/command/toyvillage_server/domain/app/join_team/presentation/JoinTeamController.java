package com.command.toyvillage_server.domain.app.join_team.presentation;

import com.command.toyvillage_server.domain.app.join_team.service.JoinTeamService;
import com.command.toyvillage_server.domain.app.join_team.service.LeaveTeamService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join-team")
@RequiredArgsConstructor
public class JoinTeamController {
    private final JoinTeamService joinTeamService;
    private final LeaveTeamService leaveTeamService;

    @PutMapping("/{appAdminId}/{teamId}")
    public ResponseEntity<MessageResponse> joinTeam(
            @PathVariable Long appAdminId,
            @PathVariable Long teamId
    ) {
        joinTeamService.execute(appAdminId, teamId);

        return ResponseEntity.ok(
                MessageResponse.of("유저가 팀에 배정되었습니다.")
        );
    }

    @DeleteMapping("/{appAdminId}")
    public ResponseEntity<MessageResponse> leaveTeam(@PathVariable Long appAdminId) {
        leaveTeamService.execute(appAdminId);

        return ResponseEntity.ok(
                MessageResponse.of("팀 배정이 해제되었습니다.")
        );
    }
}
