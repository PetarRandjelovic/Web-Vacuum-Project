package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

import java.time.LocalDate;
import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {


    private final UserRepository userRepository;
    private final VacuumRepository vacuumRepository;

    private final ErrorMsgRepository errorMsgRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder, VacuumRepository vacuumRepository
    ,ErrorMsgRepository errorMsgRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.vacuumRepository = vacuumRepository;
        this.errorMsgRepository=errorMsgRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");



        User user1 = new User();
        user1.setUsername("giga");
        user1.setLastName("chad");
        user1.setPassword(this.passwordEncoder.encode("ss"));
        user1.setEmail("gigachad@gmail.com");



        user1.setCan_delete_users(true);
        user1.setCan_read_users(true);
        user1.setCan_update_users(true);
        user1.setCan_create_users(true);

        user1.setCan_add_vacuum(true);
        user1.setCan_discharge_vacuum(true);
        user1.setCan_remove_vacuums(true);
        user1.setCan_search_vacuum(true);
        user1.setCan_stop_vacuum(true);
        user1.setCan_start_vacuum(true);
        this.userRepository.save(user1);


        User user2 = new User();
        user2.setUsername("giga");
        user2.setLastName("chadica");
        user2.setPassword(this.passwordEncoder.encode("ss"));
        user2.setEmail("gigachadica@gmail.com");


        user2.setCan_delete_users(false);
        user2.setCan_read_users(true);
        user2.setCan_update_users(false);
        user2.setCan_create_users(false);

        user2.setCan_add_vacuum(false);
        user2.setCan_discharge_vacuum(false);
        user2.setCan_remove_vacuums(false);
        user2.setCan_search_vacuum(true);
        user2.setCan_stop_vacuum(false);
        user2.setCan_start_vacuum(false);
        this.userRepository.save(user2);


        User ss = new User();
        ss.setUsername("ss");
        ss.setLastName("ss");
        ss.setPassword(this.passwordEncoder.encode("ss"));
        ss.setEmail("ss@gmail.com");


        ss.setCan_delete_users(false);
        ss.setCan_read_users(false);
        ss.setCan_update_users(false);
        ss.setCan_create_users(false);

        ss.setCan_add_vacuum(false);
        ss.setCan_discharge_vacuum(false);
        ss.setCan_remove_vacuums(false);
        ss.setCan_search_vacuum(false);
        ss.setCan_stop_vacuum(false);
        ss.setCan_start_vacuum(false);
        this.userRepository.save(ss);



        Vacuum vacuum = new Vacuum();
        vacuum.setUser(user1);
        vacuum.setStatus(Status.ON);
        vacuum.setActive(true);
        vacuum.setName("Professional sucker 2000");

        LocalDate dates = LocalDate.of(2023, 12, 20);

        vacuum.setDate(dates);
        this.vacuumRepository.save(vacuum);

        Vacuum vacuums = new Vacuum();
        vacuums.setUser(user1);
        vacuums.setStatus(Status.OFF);
        vacuums.setActive(false);
        vacuums.setName("aaaa");

        LocalDate dates1ss = LocalDate.of(2023, 12, 20);

        vacuums.setDate(dates1ss);
        this.vacuumRepository.save(vacuums);



        Vacuum vacuum1 = new Vacuum();
        vacuum1.setUser(user2);
        vacuum1.setStatus(Status.OFF);
        vacuum1.setActive(false);
        vacuum1.setName("DDDD");

        LocalDate dates1 = LocalDate.of(2023,12,29);

        vacuum1.setDate(dates1);
        this.vacuumRepository.save(vacuum1);




        ErrorMsg errorMsg=new ErrorMsg();


        errorMsg.setUserId(Long.valueOf(1));
        errorMsg.setErrorMsg("Ja sam message");
        errorMsg.setErrorType("Ja sam error type");
        errorMsg.setDate(new Date(2023-1900,1,1));
        errorMsg.setVacuumId(Long.valueOf(1));

        this.errorMsgRepository.save(errorMsg);

        System.out.println("Data loaded! ");
    }




}