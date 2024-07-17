package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.ErrorMsg;
import rs.raf.demo.repositories.ErrorMsgRepository;

import java.util.List;
@Service
public class ErrorMsgService {

    private final ErrorMsgRepository errorMsgRepository;

    public ErrorMsgService(ErrorMsgRepository errorMsgRepository) {
        this.errorMsgRepository = errorMsgRepository;
    }

    public List<ErrorMsg> findAll(Long userId) {
        return this.errorMsgRepository.findAllByUserId(userId);
    }
}
