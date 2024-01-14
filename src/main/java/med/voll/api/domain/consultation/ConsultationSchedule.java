package med.voll.api.domain.consultation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consultation.validations.cancellation.ConsultationCancellationValidator;
import med.voll.api.domain.consultation.validations.scheduling.ConsultationSchedulingValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;

@Service
public class ConsultationSchedule {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ConsultationSchedulingValidator> schedulingValidators;

    @Autowired
    private List<ConsultationCancellationValidator> cancellationValidators;

    public ConsultationDetailData toSchedule(ConsultationScheduleData data) {
        if (!patientRepository.existsById(data.patientId())) {
            throw new ValidationException("Id do paciente informado não existe!");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new ValidationException("Id do médico informado não existe!");
        }

        schedulingValidators.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);
        if (doctor == null) {
            throw new ValidationException("Não existe médico disponível nessa data!");
        }

        var consultation = new Consultation(null, doctor, patient, data.date(), null);
        consultationRepository.save(consultation);

        return new ConsultationDetailData(consultation);
    }

    private Doctor chooseDoctor(ConsultationScheduleData data) {
        if (data.doctorId() != null) {
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.specialty() == null) {
            throw new ValidationException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return doctorRepository.chooseFreeRandomDoctorOnTheDate(data.specialty(), data.date());
    }

    public void cancel(ConsultationCancelData data) {
        if (!consultationRepository.existsById(data.consultationId())) {
            throw new ValidationException("Id da consulta informado não existe!");
        }

        var consultation = consultationRepository.getReferenceById(data.consultationId());
        consultation.cancel(data.reason());
    }

}
