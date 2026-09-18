package com.command.toyvillage_server.domain.app.notice.domain;

import com.command.toyvillage_server.domain.app.team.domain.Team;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "tbl_notice_team",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_notice_team",
        columnNames = {"notice_id", "team_id"}
    )
)
public class NoticeTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private NoticeTeam(Notice notice, Team team) {
        this.notice = notice;
        this.team = team;
    }

    public static NoticeTeam create(Notice notice, Team team) {
        return new NoticeTeam(notice, team);
    }

    public void clearTeam() {
        this.team = null;
    }
}
