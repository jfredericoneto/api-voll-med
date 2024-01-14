package med.voll.api.domain.consultation.validations.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationScheduleData;

@Component
public class AdvanceTimeValidator implements ConsultationSchedulingValidator {

    @Override
    public void validate(ConsultationScheduleData data) {
        var consultationDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, consultationDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }

}
