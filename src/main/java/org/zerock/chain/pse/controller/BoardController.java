package org.zerock.chain.pse.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.chain.pse.dto.BoardDTO;
import org.zerock.chain.pse.dto.BoardRequestDTO;
import org.zerock.chain.pse.model.Board;
import org.zerock.chain.pse.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/cafeteria")
    public String boardCafeteria() {
        return "board/cafeteria";
    }


    // 경조사 게시판 전체 목록 조회
    @GetMapping("/list")
    public String boardList(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "board/list";
    }

    // 경조사  문서  개별  확인 페이지
    @GetMapping("/detail/{boardNo}")
    public String boardDetail(@PathVariable("boardNo") Long boardNo, Model model) {
        BoardDTO boardDTO = boardService.getBoardById(boardNo);
        System.out.println("Board Location: " + boardDTO.getBoardLocation());
        model.addAttribute("board", boardDTO);
        return "board/detail";
    }

    // 경조사 문서 생성 페이지
    @GetMapping("/register")
    public String boardRegister(Model model) {
        model.addAttribute("boardRequestDTO", new BoardRequestDTO());
        return "board/register";
    }

    // 경조사 생성
    @PostMapping("/register")
    public String createBoard(@Valid @ModelAttribute BoardRequestDTO boardRequestDTO, BindingResult result, Model model) {
        BoardDTO createdBoard = boardService.createBoard(boardRequestDTO);
        return "redirect:/board/detail/" + createdBoard.getBoardNo();
    }


    // 경조사  문서수정 페이지
    @GetMapping("/modify/{boardNo}")
    public String boardModify(@PathVariable("boardNo") Long boardNo, Model model) {
        model.addAttribute("board", boardService.getBoardById(boardNo));
        return "board/modify";
    }

    // 경조사 게시글 수정
    @PostMapping("/modify/{boardNo}")
    public String updateBoard(@PathVariable("boardNo") Long boardNo, @ModelAttribute BoardRequestDTO boardRequestDTO) {
        // 기존 board 데이터를 가져옴
        BoardDTO existingBoard = boardService.getBoardById(boardNo);

        // boardRequestDTO의 boardCategory를 기존의 값으로 무조건 덮어씀
        boardRequestDTO.setBoardCategory(existingBoard.getBoardCategory());

        // 서비스 계층에서 수정 처리
        boardService.updateBoard(boardNo, boardRequestDTO);
        return "redirect:/board/detail/" + boardNo;
    }

    //  경조사 게시글 삭제
    @PostMapping("/delete/{boardNo}")
    public String deleteNotice(@PathVariable("boardNo") Long boardNo) {
        boardService.deleteBoard(boardNo);

        return "redirect:/board/list";
    }

}
