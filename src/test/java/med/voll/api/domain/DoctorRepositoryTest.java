package med.voll.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.consultation.Consultation;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRegistrationData;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRegistrationData;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DoctorRepositoryTest {
    
     @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void chooseFreeRandomDoctorOnTheDateScenario1() {
        //given ou arrange
        var nextMonday10Hours = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = doctorRegister("Medico", "medico@voll.med", "123456", Specialty.CARDIOLOGIA);
        var patient = patientRegister("Paciente", "paciente@email.com", "00000000000");
        consultationRegister(doctor, patient, nextMonday10Hours);

        //when ou act
        var freeDoctor = doctorRepository.chooseFreeRandomDoctorOnTheDate(Specialty.CARDIOLOGIA, nextMonday10Hours);

        //then ou assert
        assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void chooseFreeRandomDoctorOnTheDateScenario2() {
        //given ou arrange
        var nextMonday10Hours = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = doctorRegister("Medico", "medico@voll.med", "123456", Specialty.CARDIOLOGIA);

        //when ou act
        var freeDoctor = doctorRepository.chooseFreeRandomDoctorOnTheDate(Specialty.CARDIOLOGIA, nextMonday10Hours);

        //then ou assert
        assertThat(freeDoctor).isEqualTo(doctor);
    }

    private void consultationRegister(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Consultation(null, doctor, patient, date, null));
    }

    private Doctor doctorRegister(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(doctorData(name, email, crm, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient patientRegister(String name, String email, String cpf) {
        var patient = new Patient(dadosPaciente(name, email, cpf));
        em.persist(patient);
        return patient;
    }

    private DoctorRegistrationData doctorData(String name, String email, String crm, Specialty specialty) {
        return new DoctorRegistrationData(
                name,
                email,
                crm,
                "61999999999",
                specialty,
                addressData()
        );
    }

    private PatientRegistrationData dadosPaciente(String name, String email, String cpf) {
        return new PatientRegistrationData(
                name,
                email,
                "61999999999",
                cpf,
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}
