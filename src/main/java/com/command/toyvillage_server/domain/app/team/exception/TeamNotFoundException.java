package com.command.toyvillage_server.domain.app.team.exception;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;

public class TeamNotFoundException extends ToyVillageException {
    public static final ToyVillageException EXCEPTION = new TeamNotFoundException();

    private TeamNotFoundException() {
        super(ErrorCode.TEAM_NOT_FOUND);
    }
}
