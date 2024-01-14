package med.voll.api.domain.consultation.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationScheduleData;
import med.voll.api.domain.doctor.DoctorRepository;

@Component
public class ActiveDoctorValidator implements ConsultationSchedulingValidator {

    @Autowired
    private DoctorRepository repository;

    public void validate(ConsultationScheduleData data) {
        if (data.doctorId() == null) {
            return;
        }

        var doctorIsActive = repository.findActiveById(data.doctorId());
        if (!doctorIsActive) {
            throw new ValidationException("Consulta não pode ser agendada com médico excluído");
        }
    }
}