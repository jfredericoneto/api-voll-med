package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorDetailData;
import med.voll.api.domain.doctor.DoctorRegistrationData;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.Specialty;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DoctorRegistrationData> doctorRegistrationDataJson;

    @Autowired
    private JacksonTester<DoctorDetailData> doctorDetailDataJson;

    @MockBean
    private DoctorRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void register_scenario1() throws Exception {
        var response = mvc
                .perform(post("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void register_scenario2() throws Exception {
        var registerData = new DoctorRegistrationData(
                "Medico",
                "medico@voll.med",
                "123456",
                "61999999999",
                Specialty.CARDIOLOGIA,
                addressData());

        when(repository.save(any())).thenReturn(new Doctor(registerData));

        var response = mvc
                .perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorRegistrationDataJson.write(registerData).getJson()))
                .andReturn().getResponse();

        var detailData = new DoctorDetailData(
                null,
                registerData.name(),
                registerData.email(),
                registerData.crm(),
                registerData.phone(),
                registerData.specialty(),
                new Address(registerData.address()));
        var jsonEsperado = doctorDetailDataJson.write(detailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private AddressData addressData() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}
