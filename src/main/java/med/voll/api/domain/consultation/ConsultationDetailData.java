package med.voll.api.domain.consultation;

import java.time.LocalDateTime;

public record ConsultationDetailData(

                Long id,
                Long doctorId,
                Long patientId,
                LocalDateTime date

) {

        public ConsultationDetailData(Consultation consultation) {
                this(consultation.getId(), consultation.getDoctor().getId(), consultation.getPatient().getId(),
                                consultation.getDate());
        }

}
