package med.voll.api.domain.consultation.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationRepository;
import med.voll.api.domain.consultation.ConsultationScheduleData;

@Component
public class DoctorWithAnotherConsultationAtTheSameTimeValidator implements ConsultationSchedulingValidator{
    
      @Autowired
    private ConsultationRepository repository;

    public void validate(ConsultationScheduleData data) {
        var doctorHasAnotherConsultationAtTheSameTime = repository.existsByDoctorIdAndDate(data.doctorId(), data.date());
        if (doctorHasAnotherConsultationAtTheSameTime) {
            throw new ValidationException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
