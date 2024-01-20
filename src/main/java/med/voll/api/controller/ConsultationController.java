package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultation.ConsultationCancelData;
import med.voll.api.domain.consultation.ConsultationDetailData;
import med.voll.api.domain.consultation.ConsultationSchedule;
import med.voll.api.domain.consultation.ConsultationScheduleData;

@RestController
@RequestMapping("consultations")
@SecurityRequirement(name = "bearer-key")
public class ConsultationController {

    @Autowired
    private ConsultationSchedule schedule;

    @PostMapping
    @Transactional
    public ResponseEntity toSchedule(@RequestBody @Valid ConsultationScheduleData data) {
        var dto = schedule.toSchedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid ConsultationCancelData data) {
        schedule.cancel(data);
        return ResponseEntity.noContent().build();
    }

}
