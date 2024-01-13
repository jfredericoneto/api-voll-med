package med.voll.api.doctor;

import med.voll.api.address.Address;

public record DoctorDetailData(

        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Specialty specialty,
        Address address

) {

    public DoctorDetailData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhone(),
                doctor.getSpecialty(), doctor.getAddress());
    }
}