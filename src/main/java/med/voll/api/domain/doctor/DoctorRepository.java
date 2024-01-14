package med.voll.api.domain.doctor;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(Pageable page);

    @Query("""
            select d from Doctor d
            where
            d.active = true
            and
            d.specialty = :specialty
            and
            d.id not in(
                select c.doctor.id from Consultation c
                where
                c.date = :date
            )
            order by rand()
            limit 1
            """)
    Doctor chooseFreeRandomDoctorOnTheDate(Specialty specialty, LocalDateTime date);
}
