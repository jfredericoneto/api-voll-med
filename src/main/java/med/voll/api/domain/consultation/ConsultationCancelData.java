package med.voll.api.domain.consultation;

import jakarta.validation.constraints.NotNull;

public record ConsultationCancelData(

        @NotNull Long consultationId,
        @NotNull CancellationReason reason

) {

}
