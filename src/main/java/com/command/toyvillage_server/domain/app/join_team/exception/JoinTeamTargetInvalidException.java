package com.command.toyvillage_server.domain.app.join_team.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class JoinTeamTargetInvalidException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new JoinTeamTargetInvalidException();

    private JoinTeamTargetInvalidException() {
        super(ErrorCode.JOIN_TEAM_TARGET_INVALID);
    }
}
