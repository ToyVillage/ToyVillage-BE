package com.command.toyvillage_server.domain.web.faq.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_faq")
@Entity
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Lob
    @Column(name = "question_content", nullable = false)
    private String content;

    @Lob
    @Column(name = "question_answer", nullable = false)
    private String answer;

    @Builder
    public Faq(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }

    public void update(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }
}