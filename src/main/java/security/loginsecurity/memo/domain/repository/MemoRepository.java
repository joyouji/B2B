package security.loginsecurity.memo.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import security.loginsecurity.memo.domain.entity.Memo;

import java.time.LocalDate;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // 특정 날짜에 해당하는 모든 메모를 조회하는 메소드
    List<Memo> findAllByDate(LocalDate date);
}
