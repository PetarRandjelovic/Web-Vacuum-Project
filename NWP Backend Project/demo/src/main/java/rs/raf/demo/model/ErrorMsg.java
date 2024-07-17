package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "errors")
@Getter
@Setter
public class ErrorMsg {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="VACUUM_ID")

    private Long vacuumId;
    @Column(name="USER_ID")

    private Long userId;
    @Column(name="DATE")

    private Date date;
    @Column(name="ERROR_TYPE")

    private String errorType;
    @Column(name="ERROR_MSG")

    private String errorMsg;


    public ErrorMsg(Long vacuumId,String errorMsg, String errorType, Date date, Long userId) {
        this.vacuumId = vacuumId;
        this.date = date;
        this.errorType = errorType;
        this.errorMsg = errorMsg;
        this.userId = userId;
    }

    public ErrorMsg() {

    }

}
