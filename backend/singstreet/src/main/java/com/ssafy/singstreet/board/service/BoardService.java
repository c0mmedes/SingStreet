package com.ssafy.singstreet.board.service;

import com.ssafy.singstreet.board.db.entity.Board;
import com.ssafy.singstreet.board.db.repo.BoardCommentRepository;
import com.ssafy.singstreet.board.db.repo.BoardRepository;
import com.ssafy.singstreet.board.model.board.BoardDetailResponseDto;
import com.ssafy.singstreet.board.model.board.BoardListResponseDto;
import com.ssafy.singstreet.board.model.board.BoardRequestDto;
import com.ssafy.singstreet.board.model.board.BoardUpdateRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository commentRepository;
    private final UserRepository userRepository;

    // Read
    public Page<BoardListResponseDto> readBoardList(char type, int page, int size){
        Page<Board> boardList;
        if(type == 'A') { boardList = boardRepository.findAllByDeleted(PageRequest.of(page, size));}
        else {boardList = boardRepository.findByType(type, PageRequest.of(page,size));}

        return boardList.map(this::convertBoardToDto);
    }

    public BoardDetailResponseDto readBoardDetail(int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id: "+boardId));
        board.updateHit();
        boardRepository.save(board);

        return convertBoardDetailToDto(board);
    }


    @Transactional
    // Save
    public Boolean saveBoard(BoardRequestDto requestDto){
        Board board = Board.builder()
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .title(requestDto.getTitle())
                .type(requestDto.getType())
                .hitCount(0)
                .content(requestDto.getContent())
                .build();

        boardRepository.save(board);
        return true;
    }

    @Transactional
    // Update
    public Boolean updateBoard(BoardUpdateRequestDto requestDto){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다."+requestDto.getBoardId()));

        board.update(requestDto.getTitle(), requestDto.getContent());
        boardRepository.save(board);
        return true;
    }

    @Transactional
    // Delete
    public Boolean deleteBoard(int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글 없습니다. : "+ boardId));

        board.delete();
        boardRepository.save(board);
        return true;
    }



    // Convert
    public BoardListResponseDto convertBoardToDto(Board board){
        return BoardListResponseDto.builder()
                .boardId(board.getBoardId())
                .userId(board.getUser().getUserId())
                .nickname(board.getUser().getNickname())
                .userImg(board.getUser().getUserImg())
                .type(board.getType())
                .title(board.getTitle())
                .hitCount(board.getHitCount())
                .build();
    }
    public BoardDetailResponseDto convertBoardDetailToDto(Board board){
        return BoardDetailResponseDto.builder()
                .boardId(board.getBoardId())
                .userId(board.getUser().getUserId())
                .userImg(board.getUser().getUserImg())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .type(board.getType())
                .content(board.getContent())
                .answerText(board.getAnswerText())
                .anseredAt(board.getAnseredAt())
                .build();
    }
}
