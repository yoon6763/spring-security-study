package lab.spring.security.controller;

import lab.spring.security.data.Board;
import lab.spring.security.data.User;
import lab.spring.security.data.dto.BoardInfoDto;
import lab.spring.security.data.dto.BoardRegisterDto;
import lab.spring.security.service.BoardService;
import lab.spring.security.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final SignService signService;
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> registerBoard(
            @RequestHeader("Authorization") String token,
            @RequestBody BoardRegisterDto boardRegisterDto
    ) throws BadRequestException {
        User author = signService.getUserFromToken(token);
        log.info("[registerBoard] author : {}", author);
        return ResponseEntity.ok(boardService.save(author, boardRegisterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardInfoDto> getBoard(@PathVariable Long id) {
        log.info("[getBoard] id : {}", id);
        log.info("[getBoard] board : {}", boardService.getBoard(id).toString());
        return ResponseEntity.ok(BoardInfoDto.of(boardService.getBoard(id)));
    }

}
