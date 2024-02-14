package lab.spring.security.repository;

import lab.spring.security.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);

    Optional<Member> findByNickname(String nickname);

    boolean existsByEmail(String email);
}