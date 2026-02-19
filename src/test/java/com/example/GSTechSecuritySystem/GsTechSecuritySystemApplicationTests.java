package com.example.GSTechSecuritySystem;

import com.example.GSTechSecuritySystem.service.GupshupInvoiceTextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GsTechSecuritySystemApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GupshupInvoiceTextService service;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(service, "apiKey", "TEST_API_KEY");
        ReflectionTestUtils.setField(service, "sourceNumber", "919999999999");
    }

    @Test
    void shouldSendInvoiceWithLineItems() {

        // given
        String mockResponse = "{\"status\":\"success\"}";

        when(restTemplate.postForEntity(
                eq("https://api.gupshup.io/sm/api/v1/msg"),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String items = """
        1. CCTV Camera - ₹3000
        2. DVR Unit - ₹2200
        """;

        // when
        String response = service.sendInvoiceWithItems(
                "919876543210",
                "Abhijeet",
                "INV-001",
                "06-Feb-2026",
                items,
                "5200"
        );

        // then
        assertNotNull(response);
        assertEquals(mockResponse, response);

        verify(restTemplate, times(1))
                .postForEntity(anyString(), any(HttpEntity.class), eq(String.class));
    }

}
