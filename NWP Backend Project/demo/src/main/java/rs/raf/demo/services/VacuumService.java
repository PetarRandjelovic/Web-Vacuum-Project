package rs.raf.demo.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.repositories.ErrorMsgRepository;
import rs.raf.demo.repositories.VacuumRepository;
import rs.raf.demo.schedule.Restart;
import rs.raf.demo.schedule.Start;
import rs.raf.demo.schedule.Stop;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacuumService {

    private final VacuumRepository vacuumRepository;
    private final ErrorMsgRepository errorMsgRepository;


    private final TaskScheduler taskScheduler;
    private final Zawordo zawordo;
    public VacuumService(VacuumRepository vacuumRepository,  ErrorMsgRepository errorMsgRepository,Zawordo zawordo, TaskScheduler taskScheduler) {
        this.vacuumRepository = vacuumRepository;
        this.errorMsgRepository = errorMsgRepository;
        this.zawordo = zawordo;
        this.taskScheduler = taskScheduler;
    }


    public List<Vacuum> findAll(Long id) {
        return this.vacuumRepository.findVacuumsByUserId(id);
    }

    public List<Vacuum> findAllwithEmail(String email) {
        return this.vacuumRepository.findVacuumsByUserEmail(email);
    }


    public List<Vacuum> search (String email, List<Status> status, String machineName, LocalDate dateFrom, LocalDate dateTo){
        System.out.println(status+" STATUS HOHO");

        List<String> statusNames = status.stream().map(Status::name).collect(Collectors.toList());

        System.out.println(statusNames+" STATUSNAMES");
       // List<Status> statusList = Arrays.asList(Status.ON, Status.OFF);
      //  Status status1 = statusList.size() > 0 ? statusList.get(0) : null;
     //   Status status2 = statusList.size() > 1 ? statusList.get(1) : null;


    //    LocalDate localDateFrom = convertToLocalDate(dateFrom);
    //    LocalDate localDateTo = convertToLocalDate(dateTo);

     //   System.out.println(localDateFrom+" "+localDateTo+" EEEEE");

        System.out.println(status+" EEEEE");

        return this.vacuumRepository.searchVacuum(email,status,machineName,dateFrom,dateTo);
    }

    private LocalDate convertToLocalDate(Date date) {
        return (date == null) ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public Vacuum createEntity(Vacuum vacuum){


        return this.vacuumRepository.save(vacuum);
    }

    public Optional<Vacuum> findbyID(Long id){
        return this.vacuumRepository.findById(id);
    }
    public void delete(Long id){
        Optional<Vacuum> vacuum1 = this.vacuumRepository.findById(id);
        Vacuum vacuum;
        if(vacuum1.isPresent())
        {
            vacuum = vacuum1.get();
            vacuum.setActive(false);
            this.vacuumRepository.save(vacuum);
        }
    }
    public ResponseEntity<?> start(Long id){
        Optional<Vacuum> vacuum = vacuumRepository.findById(id);

        if(vacuum.isPresent())
        {

            System.out.println("START ULAZIM"+" "+vacuum.get().isActive());
/*
            if(vacuum.get().isActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum, is working");
            }*/
            System.out.println("START ULAZIM1"+" "+vacuum.get().isActive());

            Vacuum vacuum1 = vacuum.get();
            vacuum1.setActive(true);
            vacuumRepository.save(vacuum1);
            zawordo.startVacuum(vacuum1);
            return ResponseEntity.ok(vacuum1);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vacuum not found");

    }
    public ResponseEntity<?> remove(Long id){
        Optional<Vacuum> vacuum = vacuumRepository.findById(id);
        if(vacuum.isPresent())
        {
            if(vacuum.get().isActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum, is working");
            }

            Vacuum vacuum1 = vacuum.get();
            vacuum1.setRemoved("removed");
            vacuumRepository.save(vacuum1);
            return ResponseEntity.ok(vacuum1);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vacuum not found");

    }
    public ResponseEntity<?> stop(Long id){
        Optional<Vacuum> vacuum = vacuumRepository.findById(id);
        System.out.println("STOP ULAZIM" + vacuum.get().getStatus().equals(Status.OFF));
        if(vacuum.isPresent())
        {

            if(vacuum.get().getStatus().equals(Status.OFF)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum must be on to turn off ");
            }
            if(!vacuum.get().isActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum is off");
            }

            Vacuum vacuum1 = vacuum.get();
            vacuum1.setActive(true);
            vacuumRepository.save(vacuum1);
            zawordo.stopVacuum(vacuum1);
            return ResponseEntity.ok(vacuum1);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vacuum not found");

    }

    public ResponseEntity<?> discharge(Long id){
        Optional<Vacuum> vacuum = vacuumRepository.findById(id);
        System.out.println("DISCHARGE");
        if(vacuum.isPresent())
        {

            if(vacuum.get().getStatus().equals(Status.ON)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum must be on to discharge it");
            }
            if(vacuum.get().isActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vacuum is on!");
            }

            Vacuum vacuum1 = vacuum.get();
            //System.out.println("DA LI SI OVDE? "+" "+vacuum1+" "+vacuum1.getVersion());
         //  vacuum1.setActive(true);
          //  vacuumRepository.save(vacuum1);
            zawordo.dischargeVacuum(vacuum1);
            return ResponseEntity.ok(vacuum1);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vacuum not found");

    }
    public ResponseEntity<?> scheduleStart(Long machineId, Long userId, Date date){

        taskScheduler.schedule(new Start(vacuumRepository,machineId,userId,zawordo,errorMsgRepository),date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> scheduleStop(Long machineId, Long userId, Date date){

        taskScheduler.schedule(new Stop(vacuumRepository,machineId,userId,zawordo,errorMsgRepository),date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> scheduleRestart(Long machineId, Long userId, Date date){

        taskScheduler.schedule(new Restart(vacuumRepository,machineId,userId,zawordo,errorMsgRepository),date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
