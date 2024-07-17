package rs.raf.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import rs.raf.demo.model.ErrorMsg;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.repositories.VacuumRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.Date;

@Component
public class Zawordo {

    private final VacuumRepository vacuumRepository;

    public Zawordo(VacuumRepository vacuumRepository) {
        this.vacuumRepository = vacuumRepository;
    }

    @Async
    public void startVacuum(Vacuum vacuum){

        System.out.println("START CEKANJE"+vacuum+" "+vacuum.getVersion());

        long time = 15000;
        try {
            Thread.sleep(time);
        }
        catch (Exception e){
            System.out.println("Start Error");
        }



        Vacuum existingVacuum = vacuumRepository.findByIdAndVersion(vacuum.getId(), vacuum.getVersion())
                .orElseThrow(() -> new OptimisticLockException("Optimist Vacuum start"));

        existingVacuum.setStatus(Status.ON);
        existingVacuum.setActive(true);
        vacuumRepository.save(existingVacuum);

    }

    @Async
    public void stopVacuum(Vacuum vacuum){
        System.out.println("ZA WORDO "+ vacuum);
        long time = 15000;

        try {

            Thread.sleep(time);
        }
        catch (Exception e){
            System.out.println("Stop Error");
        }

        Vacuum existingVacuum = vacuumRepository.findByIdAndVersion(vacuum.getId(), vacuum.getVersion())
                .orElseThrow(() -> new OptimisticLockException("Optimist Vacuum stop"));


      //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum is off");

        existingVacuum.setStatus(Status.OFF);
        existingVacuum.setActive(false);
        vacuumRepository.save(existingVacuum);

    }

    @Async
    public void dischargeVacuum(Vacuum vacuum){
        System.out.println("ULAZIM? "+ vacuum);
        long time = 15000;

        try {

            Vacuum existingVacuum = vacuumRepository.findByIdAndVersion(vacuum.getId(), vacuum.getVersion())
                    .orElseThrow(() -> new OptimisticLockException("Optimist Vacuum discharge"));

            System.out.println("HEEEEEEJ0 "+existingVacuum+" "+existingVacuum.getVersion());
            Thread.sleep(time);

            existingVacuum.setStatus(Status.DISCHARGING);
            existingVacuum.setActive(true);
            vacuumRepository.save(existingVacuum);
            existingVacuum.setVersion(existingVacuum.getVersion()+1);
            System.out.println("HEEEEEEJ "+existingVacuum+" "+existingVacuum.getVersion());
            Thread.sleep(time);

            existingVacuum.setStatus(Status.OFF);
            existingVacuum.setActive(false);
            vacuumRepository.save(existingVacuum);
            System.out.println("HEEEEEEJ1 "+existingVacuum);
        }

        catch (ObjectOptimisticLockingFailureException s){
            System.out.println("Optimistic discharge");
        }
        catch (Exception e){
            System.out.println("Restart2 Error");
            e.printStackTrace();

        }


    }

}
