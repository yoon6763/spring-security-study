package lab.spring.security.service;

import lab.spring.security.data.Board;
import lab.spring.security.data.User;
import lab.spring.security.data.dto.BoardRegisterDto;
import lab.spring.security.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(User author, BoardRegisterDto boardRegisterDto) {
        return boardRepository.save(boardRegisterDto.toEntity(author));
    }

}
