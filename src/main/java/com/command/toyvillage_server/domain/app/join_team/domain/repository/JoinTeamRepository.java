package com.command.toyvillage_server.domain.app.join_team.domain.repository;

import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JoinTeamRepository extends JpaRepository<JoinTeam, Long> {
    Optional<JoinTeam> findByAppAdmin_Id(Long appAdminId);

    Optional<JoinTeam> findByAppAdmin_IdAndTeam_Id(Long appAdminId, Long teamId);

    List<JoinTeam> findAllByTeam_Id(Long teamId);

    @Query("""
            select j.team.id as teamId, count(j) as memberCount
            from JoinTeam j
            group by j.team.id
            """)
    List<TeamMemberCount> countMembersByTeam();

    interface TeamMemberCount {
        Long getTeamId();

        Long getMemberCount();
    }

    @Query("""
            select j from JoinTeam j
            join fetch j.appAdmin
            where j.team.id = :teamId
            order by j.appAdmin.id asc
            """)
    List<JoinTeam> findMembersByTeamId(@Param("teamId") Long teamId);

    @Query("""
            select j.team.id as teamId, count(j) as memberCount
            from JoinTeam j
            group by j.team.id
            """)
    List<TeamMemberCount> countMembersByTeam();

    interface TeamMemberCount {
        Long getTeamId();

        Long getMemberCount();
    }

    @Query("""
            select j from JoinTeam j
            join fetch j.team
            join fetch j.appAdmin
            """)
    List<JoinTeam> findAllWithTeamAndAppAdmin();
}
