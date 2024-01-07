package lab.spring.security.data.dto;

import lab.spring.security.data.Board;
import lab.spring.security.data.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardRegisterDto {
    private String title;
    private String content;

    public Board toEntity(User author) {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
