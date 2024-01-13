package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.patient.DataListingPatient;
import med.voll.api.patient.DataRegistrationPatient;
import med.voll.api.patient.DataUpdatePatient;
import med.voll.api.patient.Patient;
import med.voll.api.patient.PatientRepository;

@RestController
@RequestMapping("patients")
public class PatientController {
    
      @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DataRegistrationPatient data) {
        repository.save(new Patient(data));
    }

    @GetMapping
    public Page<DataListingPatient> listar(@PageableDefault(size = 10, sort = {"name"}) Pageable page) {
        return repository.findAllByActiveTrue(page).map(DataListingPatient::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DataUpdatePatient data) {
        var paciente = repository.getReferenceById(data.id());
        paciente.updateInformation(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.remove();
    }

}
