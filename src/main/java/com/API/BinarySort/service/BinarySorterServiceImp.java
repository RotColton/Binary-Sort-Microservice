package com.API.BinarySort.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;

import static com.API.BinarySort.exception.ErrorMessages.*;

@Service
public class BinarySorterServiceImp implements BinarySorterService{

    @Override
    public Flux<Integer> sortByBinaryRepresentation(List<Integer> numbers) {
        validateInput(numbers);
        return Flux.fromIterable(numbers)
                .sort(Comparator.comparingInt(Integer::bitCount)
                        .thenComparingInt(num -> num));
    }


    private void validateInput(List<Integer> numbers) throws IllegalArgumentException{
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_LIST);
        }

        if (numbers.size() > 50) {
            throw new IllegalArgumentException(LIST_TOO_LARGE);
        }

        numbers.stream()
                .filter(number -> number == null || number < -999 || number > 999)
                .findAny()
                .ifPresent(invalid -> {
                    throw new IllegalArgumentException(INVALID_THREE_DIGIT_NUMBER);
                });
    }
}
