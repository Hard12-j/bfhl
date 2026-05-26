package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    @PostMapping
    public ResponseEntity<BfhlResponse> process(@RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.processRequest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<java.util.Map<String, Integer>> getOperationCode() {
        return ResponseEntity.ok(java.util.Map.of("operation_code", 1));
    }
}
