package com.command.toyvillage_server.domain.app.join_team.domain.repository;

import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JoinTeamRepository extends JpaRepository<JoinTeam, Long> {
    Optional<JoinTeam> findByAppAdmin_Id(Long appAdminId);

    List<JoinTeam> findAllByTeam_Id(Long teamId);

    @Query("""
            select j from JoinTeam j
            join fetch j.team
            join fetch j.appAdmin
            """)
    List<JoinTeam> findAllWithTeamAndAppAdmin();
}
