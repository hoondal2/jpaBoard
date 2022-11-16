package com.study.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.board.entity.Board;
import com.study.board.entity.BoardRepository;

@SpringBootTest
// 기존 레거시 프로젝트와 달리 스프링부트는 해당 어노테이션만 선언하면 테스팅 가능
public class BoardTests {

    @Autowired
            // 스프링 컨테이너에 등록된 BoardRepository 객체 주입
    BoardRepository boardRepository;

    @Test
    void save() {

        // 1. 게시글 파라미터 생성
        Board params = Board.builder()
                // params는 빌더 패턴을 통해 생성된 객체
                // 생성자와 달리, 빌더 패턴을 이용하면 어떤 멤버에 어떤 값을 세팅하는지 직관적으로 확인 가능
                // 생성자를 통해 생성한다면 -> Board entity = new Board("1번 게시글 제목", "1번 게시글 내용", "훈달이", 0, 'N');
                // 빌더 패턴은 인자 순서에 관계없이 객체 생성 가능
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .writer("훈달이")
                .hits(0)
                .deleteYn('N')
                .build();

        // 2. 게시글 저장
        // 빌더를 통해 생성한 객체를 저장
        boardRepository.save(params);

        // 3. 1번 게시글 정보 조회
        Board entity = boardRepository.findById((long) 4).get();
        assertThat(entity.getTitle()).isEqualTo("1번 게시글 제목");
        assertThat(entity.getContent()).isEqualTo("1번 게시글 내용");
        assertThat(entity.getWriter()).isEqualTo("훈달이");
    }

    @Test
    void findAll() {
        // boardRepository의 count()와 findAll()메서드를 이용해 전체 게시글 수와 전체 게시글 리스트를 조회하는 쿼리 실행

        // 1. 전체 게시글 수 조회
        long boardsCount = boardRepository.count();

        // 2. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
    }

    @Test
    void delete() {

        // 1. 게시글 조회
        Board entity = boardRepository.findById((long) 1).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }

}