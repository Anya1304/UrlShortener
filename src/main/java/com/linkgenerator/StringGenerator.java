package com.linkgenerator;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StringGenerator {
    private final char[] lettersAndNumbers = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

    public String generateString(int length) {
        Random random = new Random();

        return IntStream.range(1, length)
                .mapToObj(i ->  String.valueOf(lettersAndNumbers[random.nextInt(lettersAndNumbers.length)]))
                .collect(Collectors.joining());
    }
}
