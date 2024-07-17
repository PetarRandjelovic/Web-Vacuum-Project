package rs.raf.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Vacuum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String name;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="date",nullable = false)
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name="removed")
    private String removed;

    @Column(name="version")
    @Version
    private Long version;


    @Override
    public String toString() {
        return user+" "+name+" "+status+" "+date+" "+active;
    }
}
