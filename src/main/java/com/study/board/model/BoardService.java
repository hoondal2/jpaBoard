package com.study.board.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardResponseDto;
import com.study.board.entity.Board;
import com.study.board.entity.BoardRepository;
import com.study.exception.CustomException;
import com.study.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final BoardRequestDto params) {

        Board entity = boardRepository.save(params.toEntity());
        return entity.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    public List<BoardResponseDto> findAll() {

        Sort sort = Sort.by(Direction.DESC, "id", "createdDate"); // order by
        List<Board> list = boardRepository.findAll(sort); // findAll() 메서드의 인자로 sort 객체를 전달해 전체 게시글 조회
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
        // list 변수에는 게시글 entity가 담겨있고,
        // 각각의 entity를 boardResponseDto 타입으로 변경(생성)해서 리턴

        // stream API 를 사용하지 않은 경우
//        List<BoardResponseDto> boardList = new ArrayList<>(); // 리스트 타입의 보드리스트
//
//        for (Board entity : list) {
//            boardList.add(new BoardResponseDto(entity)); //
//        }
//
//        return boardList;

    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final BoardRequestDto params) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.getTitle(), params.getContent(), params.getWriter());
        return id;
    }

}