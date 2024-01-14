package med.voll.api.domain.consultation;

import java.time.LocalDateTime;

public record ConsultationDetailData(

        Long id,
        Long doctorId,
        Long patientId,
        LocalDateTime date

) {

}
