package com.bfhl.service.impl;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.full-name:hardik joshi}")
    private String fullName;

    @Value("${bfhl.user.dob:12062005}")
    private String dob;

    @Value("${bfhl.user.email:joshi12hardik@gmail.com}")
    private String email;

    @Value("${bfhl.user.roll-number:0827AL231051}")
    private String rollNumber;

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        if (request == null || request.getData() == null) {
            return BfhlResponse.builder()
                    .isSuccess(false)
                    .build();
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;
        StringBuilder allAlphabets = new StringBuilder();

        for (String item : request.getData()) {
            if (item == null) {
                continue;
            }

            // Extract alphabetic characters for the concat string
            for (char c : item.toCharArray()) {
                if (Character.isLetter(c)) {
                    allAlphabets.append(c);
                }
            }

            // Categorization
            if (item.matches("^[0-9]+$")) {
                BigInteger num = new BigInteger(item);
                if (num.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
                sum = sum.add(num);
            } else if (item.matches("^[a-zA-Z]+$")) {
                alphabets.add(item.toUpperCase());
            } else {
                specialCharacters.add(item);
            }
        }

        // Calculate alternating caps reversed string
        String reversed = allAlphabets.reverse().toString();
        StringBuilder alternatingCaps = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                alternatingCaps.append(Character.toUpperCase(c));
            } else {
                alternatingCaps.append(Character.toLowerCase(c));
            }
        }

        String userId = String.format("%s_%s", fullName.toLowerCase().replace(" ", "_"), dob);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(userId)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum.toString())
                .concatString(alternatingCaps.toString())
                .build();
    }
}
