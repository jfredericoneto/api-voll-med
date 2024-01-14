package med.voll.api.domain.consultation.validations.cancellation;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.ConsultationCancelData;
import med.voll.api.domain.consultation.ConsultationRepository;

@Component
public class MinimumAdvanceNoticeValidator implements ConsultationCancellationValidator {

    @Autowired
    private ConsultationRepository repository;

    @Override
    public void validate(ConsultationCancelData data) {
        var consultation = repository.getReferenceById(data.consultationId());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, consultation.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new ValidationException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
