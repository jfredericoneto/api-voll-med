package med.voll.api.domain.consultation.validations.cancellation;

import med.voll.api.domain.consultation.ConsultationCancelData;

public interface ConsultationCancellationValidator {

    void validate(ConsultationCancelData data);

}
