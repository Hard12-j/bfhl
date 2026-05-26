package com.bfhl;

import com.bfhl.controller.BfhlController;
import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlControllerTest {

    private BfhlController bfhlController;

    @BeforeEach
    void setUp() {
        BfhlServiceImpl bfhlService = new BfhlServiceImpl();
        // Inject properties
        ReflectionTestUtils.setField(bfhlService, "fullName", "john_doe");
        ReflectionTestUtils.setField(bfhlService, "dob", "17091999");
        ReflectionTestUtils.setField(bfhlService, "email", "john@xyz.com");
        ReflectionTestUtils.setField(bfhlService, "rollNumber", "ABCD123");

        bfhlController = new BfhlController(bfhlService);
    }

    @Test
    void testExampleA() {
        BfhlRequest request = new BfhlRequest();
        request.setData(List.of("a", "1", "334", "4", "R", "$"));

        ResponseEntity<BfhlResponse> responseEntity = bfhlController.process(request);
        BfhlResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("john@xyz.com", response.getEmail());
        assertEquals("ABCD123", response.getRollNumber());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("334", "4"), response.getEvenNumbers());
        assertEquals(List.of("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals(List.of("$"), response.getSepcialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testExampleB() {
        BfhlRequest request = new BfhlRequest();
        request.setData(List.of("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        ResponseEntity<BfhlResponse> responseEntity = bfhlController.process(request);
        BfhlResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(List.of("5"), response.getOddNumbers());
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void testExampleC() {
        BfhlRequest request = new BfhlRequest();
        request.setData(List.of("A", "ABCD", "DOE"));

        ResponseEntity<BfhlResponse> responseEntity = bfhlController.process(request);
        BfhlResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void testEmptyRequest() {
        BfhlRequest request = new BfhlRequest();

        ResponseEntity<BfhlResponse> responseEntity = bfhlController.process(request);
        BfhlResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertFalse(response.getIsSuccess());
    }

    @Test
    void testGetOperationCode() {
        ResponseEntity<java.util.Map<String, Integer>> responseEntity = bfhlController.getOperationCode();
        java.util.Map<String, Integer> response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(1, response.get("operation_code"));
    }
}
