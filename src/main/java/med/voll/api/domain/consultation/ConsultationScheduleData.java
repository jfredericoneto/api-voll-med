package med.voll.api.domain.consultation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.doctor.Specialty;

public record ConsultationScheduleData(

        Long doctorId,
        @NotNull Long patientId,
        @NotNull @Future LocalDateTime date,
        Specialty specialty

) {

}
