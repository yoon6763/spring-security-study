package lab.spring.security.data.dto;

import lab.spring.security.data.Board;
import lombok.Data;

@Data
public class BoardInfoDto {

    private Long id;
    private String title;
    private String content;
    private UserInfoDto author;

    public static BoardInfoDto of(Board board) {
        BoardInfoDto boardInfoDto = new BoardInfoDto();
        boardInfoDto.id = board.getId();
        boardInfoDto.title = board.getTitle();
        boardInfoDto.content = board.getContent();
        boardInfoDto.author = UserInfoDto.of(board.getAuthor());
        return boardInfoDto;
    }

}
