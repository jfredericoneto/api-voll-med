package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientDetailData;
import med.voll.api.domain.patient.PatientListingData;
import med.voll.api.domain.patient.PatientRegistrationData;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.domain.patient.PatientUpdateData;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid PatientRegistrationData data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListingData>> lists(
            @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
        var page = repository.findAllByActiveTrue(pagination).map(PatientListingData::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailData(patient));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid PatientUpdateData data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateInformation(data);

        return ResponseEntity.ok(new PatientDetailData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remove(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.remove();

        return ResponseEntity.noContent().build();
    }

}
