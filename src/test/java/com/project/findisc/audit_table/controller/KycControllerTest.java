package com.project.findisc.audit_table.controller;

import com.project.findisc.audit_table.service.KycService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KycController.class)
class KycControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KycService kycService;

    @Test
    void uploadKyc_success() throws Exception {

        MockMultipartFile photo =
                new MockMultipartFile(
                        "photo",
                        "test.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "dummy-image".getBytes()
                );

        // controller does not care about return value
        Mockito.doNothing().when(kycService)
                .saveKyc(
                        Mockito.anyLong(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any()
                );

        mockMvc.perform(
                        multipart("/api/v1/kyc")
                                .file(photo)
                                .param("accountId", "1")
                                .param("aadharNumber", "123412341234")
                                .param("panNumber", "ABCDE1234F")
                )
                .andExpect(status().isOk());
    }
}