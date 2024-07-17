package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Vacuum;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

 /*   @Query("SELECT n FROM Vacuum n WHERE" +
            " ((:name IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
            " (:dateFrom IS NULL OR :dateTo IS NULL OR n.date BETWEEN :dateFrom AND :dateTo) AND" +
            " ((:status IS NULL) OR" +
            " (n.status = :status) OR" +
            " (n.status = :status2))" +
            ") AND n.user.email = :email")*/
 /*@Query("select n from Vacuum n where" +
         " ((n.name like %:name% or :name is null) and" +
         " ((n.date between :dateFrom and :dateTo) or (:dateFrom is null or :dateTo is null)) and" +
         " (:status IS NULL) OR (n.status = :status) OR (n.status = :status2)  ) and n.user.email = :email")*/
 @Query("SELECT n FROM Vacuum n WHERE" +
         " ((:name IS NULL OR LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
         " (:status IS NULL OR n.status IN :status) AND" +
         " (:dateFrom IS NULL OR :dateTo IS NULL OR n.date BETWEEN :dateFrom AND :dateTo)" +
         ") AND n.user.email = :email")
    List<Vacuum> searchVacuum(String email, List<Status>  status, String name, LocalDate dateFrom, LocalDate dateTo);

/*    @Query("SELECT n FROM Vacuum n WHERE n.name LIKE %:name% AND n.user.email = :email")
    List<Vacuum> searchVacuum(String email, List<Status> status, String name, Date dateFrom, Date dateTo);
*/

    Optional<Vacuum> findByIdAndVersion(Long id, Long version);

    List<Vacuum> findVacuumsByUserId(Long id);

    @Query("SELECT v FROM Vacuum v WHERE v.user.email = :email AND v.removed IS NULL")
    List<Vacuum> findVacuumsByUserEmail(String email);
}
