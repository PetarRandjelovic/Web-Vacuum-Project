package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.ErrorMsg;

import java.util.List;

@Repository
public interface ErrorMsgRepository extends JpaRepository<ErrorMsg, Long> {


    List<ErrorMsg> findAllByUserId(Long userId);
}
