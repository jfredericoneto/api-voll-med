package med.voll.api.domain.consultation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidationException;
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

    public void toSchedule(ConsultationScheduleData data) {
        if (!patientRepository.existsById(data.patientId())) {
            throw new ValidationException("Id do paciente informado não existe!");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new ValidationException("Id do médico informado não existe!");
        }

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);
        var consultation = new Consultation(null, doctor, patient, data.date(),null);
        consultationRepository.save(consultation);
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
