package com.study.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// setter가 없는 이유
// 엔티티 클래스는 테이블 그 자체. 각각 멤버 변수는 해당 테이블의 컬럼이 된다.
// 컬럼에 대한 setter를 무작정 생성하는 경우, 객체의 값이 어느 시점에 변경되었는지 알 수 없다.

@NoArgsConstructor(access = AccessLevel.PROTECTED)
// access 속성을 이용, 동일한 패키지 내의 클래스에서만 객체를 생성할 수 있도록 제어

@Entity
// 해당 클래스가 테이블과 매핑되는 JPA의 엔티티 클래스임을 의미
// 기본적으로 클래스명(Camel Case)을 테이블명(Snake Case)로 매핑
// 이름이 다를경우, 클래스 레벨에 @Table을 선언,
// @Table(name="user_role")과 깉이 처리한다.
public class Board {


    @Id
    // 해당 멤버가 엔티티의 PK임을 의미한다.
    // 보통 MySql DB는 PK를 bigint 타입으로, 엔티티에서는 Long타입으로 선언한다

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK 생성 전략을 설정하는 어노테이션
    // MySql은 자동증가를 지원, pk 자동증가를 지원하는 db는 해당 어노테이션을 선언해야한다.
    // 오라클과 같이 시퀀스를 이용하는 db는 GenerationType.SEQUENCE를 이용
    // GenerationType.AUTO -> DB에서 제공하는 PK 생성 전략을 가져가게 된다.

    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    private String writer; // 작성자

    private int hits; // 조회 수

    private char deleteYn; // 삭제 여부

    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    private LocalDateTime modifiedDate; // 수정일

    @Builder
    // 롬복 제공, 생성자 대신 이용
    public Board(String title, String content, String writer, int hits, char deleteYn) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = LocalDateTime.now();
    }

}
