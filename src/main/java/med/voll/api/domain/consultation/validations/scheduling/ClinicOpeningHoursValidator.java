package med.voll.api.domain.consultation.validations.scheduling;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationScheduleData;

@Component
public class ClinicOpeningHoursValidator implements ConsultationSchedulingValidator {

    @Override
    public void validate(ConsultationScheduleData data) {
        var consultationDate = data.date();

        var sunday = consultationDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpening = consultationDate.getHour() < 7;
        var afterClosingTheClinic = consultationDate.getHour() > 18;

        if (sunday || beforeClinicOpening || afterClosingTheClinic) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
