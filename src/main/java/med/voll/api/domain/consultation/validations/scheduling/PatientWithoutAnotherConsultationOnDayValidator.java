package med.voll.api.domain.consultation.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationRepository;
import med.voll.api.domain.consultation.ConsultationScheduleData;

@Component
public class PatientWithoutAnotherConsultationOnDayValidator implements ConsultationSchedulingValidator {

    @Autowired
    private ConsultationRepository repository;

    public void validate(ConsultationScheduleData data) {
        var firstTime = data.date().withHour(7);
        var lastTime = data.date().withHour(18);
        var patientWithAnotherConsultationOnDay = repository.existsByPatientIdAndDateBetween(data.patientId(),
                firstTime, lastTime);
        if (patientWithAnotherConsultationOnDay) {
            throw new ValidationException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
