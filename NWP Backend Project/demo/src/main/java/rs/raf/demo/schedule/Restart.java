package rs.raf.demo.schedule;

import rs.raf.demo.model.ErrorMsg;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.repositories.ErrorMsgRepository;
import rs.raf.demo.repositories.VacuumRepository;
import rs.raf.demo.services.Zawordo;

import java.util.Date;
import java.util.Optional;

public class Restart implements Runnable{

    private VacuumRepository vacuumRepository;
    private Long vacuumId;
    private Long userId;

    private Zawordo zawordo;

    private ErrorMsgRepository errorMsgRepository;

    public Restart(VacuumRepository vacuumRepository, Long vacuumId, Long userId, Zawordo zawordo, ErrorMsgRepository errorMsgRepository) {
        this.vacuumRepository = vacuumRepository;
        this.vacuumId = vacuumId;
        this.userId = userId;
        this.zawordo = zawordo;
        this.errorMsgRepository = errorMsgRepository;
    }

    @Override
    public void run() {
        Optional<Vacuum> vacuum = vacuumRepository.findById(this.vacuumId);
        if (vacuum.isPresent())
        {


            if(vacuum.get().isActive()){
                ErrorMsg errorMsg = new ErrorMsg(this.vacuumId, "Vacuum is already active","Start",new Date(),this.userId);
                errorMsgRepository.save(errorMsg);
                return;
            }
            if(vacuum.get().getStatus().equals(Status.DISCHARGING)){
                ErrorMsg errorMsg = new ErrorMsg(this.vacuumId, "Vacuum is stopped","Start",new Date(),this.userId);
                errorMsgRepository.save(errorMsg);
                return;
            }
            if(vacuum.get().getStatus().equals(Status.ON)){
                ErrorMsg errorMsg = new ErrorMsg(this.vacuumId, "Vacuum is is on!","Start",new Date(),this.userId);
                errorMsgRepository.save(errorMsg);
                return;
            }

            vacuumRepository.save(vacuum.get());
            zawordo.dischargeVacuum(vacuum.get());
        }
        else
        {
            ErrorMsg errorMsg = new ErrorMsg(this.vacuumId, "Not Found","Start",new Date(),this.userId);
            errorMsgRepository.save(errorMsg);
        }
    }
}