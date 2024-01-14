package med.voll.api.domain.consultation.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationScheduleData;
import med.voll.api.domain.patient.PatientRepository;

@Component
public class ActivePatientValidator implements ConsultationSchedulingValidator{
    
    @Autowired
    private PatientRepository repository;

    public void validate(ConsultationScheduleData data) {
        var patientIsActive = repository.findActiveById(data.patientId());
        if (!patientIsActive) {
            throw new ValidationException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
