package rs.raf.demo.responses;

import lombok.Getter;
import lombok.Setter;
import rs.raf.demo.model.Status;

import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
public class VacuumSearchResponse {


    private String vacuumName;
    private List<Status> status;
    private LocalDate dateFrom;
    private LocalDate dateTo;


    @Override
    public String toString() {
        return vacuumName+status+dateFrom+dateTo;
    }
}