package rs.raf.demo.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "LAST_NAME",nullable = false)
    private String lastName;

    @Column(name = "FIRST_NAME",nullable = false)
    private String username;

    @Column(name = "EMAIL",unique = true,nullable = false)
    private String email;
    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean can_read_users;
    @Column(nullable = false)
    private boolean can_update_users;
    @Column(nullable = false)
    private boolean can_create_users;
    @Column(nullable = false)
    private boolean can_delete_users;


    private boolean can_search_vacuum;
    private boolean can_start_vacuum;
    private boolean can_stop_vacuum;
    private boolean can_discharge_vacuum;
    private boolean can_add_vacuum;
    private boolean can_remove_vacuums;


    @Override
    public String toString() {
        return lastName+" "+username+" "+email+" "+password+" "+can_create_users+" "+can_delete_users+" "+can_read_users
                +can_update_users;
    }
}
