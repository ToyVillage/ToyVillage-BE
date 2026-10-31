package com.command.toyvillage_server.domain.app.notification.domain.repository;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.notification.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface FcmTokenRepository extends JpaRepository<FcmToken, String> {
    @Modifying
    @Query(value = """
        insert into tbl_fcm_token (token, app_admin_id) values (:token, :appAdminId)
        on duplicate key update app_admin_id = :appAdminId
        """, nativeQuery = true)
    void register(@Param("token") String token, @Param("appAdminId") Long appAdminId);

    void deleteByTokenAndAppAdmin_Id(String token, Long appAdminId);

    List<FcmToken> findAllByAppAdmin_DeleteStatusFalseAndAppAdmin_RoleIn(Collection<AppAdminRole> roles);

    @Query("""
        select distinct f.token from FcmToken f
        join JoinTeam j on j.appAdmin = f.appAdmin
        where j.team.id in :teamIds
          and f.appAdmin.deleteStatus = false
          and f.appAdmin.role in :roles
        """)
    List<String> findTeamTokens(@Param("teamIds") List<Long> teamIds, @Param("roles") Collection<AppAdminRole> roles);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteAllByTokenIn(List<String> tokens);
}
