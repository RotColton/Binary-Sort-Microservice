package com.API.BinarySort.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static com.API.BinarySort.exception.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BinarySorterServiceTest {

    private final BinarySorterServiceImp binarySorterService = new BinarySorterServiceImp();

    @Test
    public void testSortBinaryRepresentation_Success() {
        List<Integer> input = Arrays.asList(7, 2, 1, 9, 5, 3, 8, 25, 42);
        List<Integer> expectedOutput = Arrays.asList(1, 2, 8, 3, 5, 9, 7, 25, 42);

        Flux<Integer> result = binarySorterService.sortByBinaryRepresentation(input);

        StepVerifier.create(result)
                .expectNextSequence(expectedOutput)
                .verifyComplete();
    }

    @Test
    public void testSortBinaryRepresentation_EmptyListThrowsException() {
        List<Integer> input = List.of();
        assertThrows(IllegalArgumentException.class, () -> binarySorterService.sortByBinaryRepresentation(input), EMPTY_LIST);
    }

    @Test
    public void testSortBinaryRepresentation_SingleElement() {
        List<Integer> input = List.of(7);

        Flux<Integer> result = binarySorterService.sortByBinaryRepresentation(input);

        StepVerifier.create(result)
                .expectNext(7)
                .verifyComplete();
    }

    @Test
    public void testSortBinaryRepresentation_NegativeNumbers() {
        List<Integer> input = Arrays.asList(-3, -1, -7, 2, 1);
        List<Integer> expectedOutput = Arrays.asList(1, 2, -7, -3, -1);

        Flux<Integer> result = binarySorterService.sortByBinaryRepresentation(input);

        StepVerifier.create(result)
                .expectNextSequence(expectedOutput)
                .verifyComplete();
    }

    @Test
    public void testSortBinaryRepresentation_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> binarySorterService.sortByBinaryRepresentation(null), EMPTY_LIST);
    }

    //TODO agregar el comportamiento al readme
    @Test
    public void testSortBinaryRepresentation_ExceedsMaxAllowedSize() {
        Flux<Integer> input = Flux.range(1, 51);

        StepVerifier.create(input.collectList().flatMapMany(binarySorterService::sortByBinaryRepresentation))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals(LIST_TOO_LARGE))
                .verify();    }

    @Test
    public void testSortBinaryRepresentation_NonThreeDigitNumbers() {
        Flux<Integer> input = Flux.just(7, 250, 125, 999, 1001);

        StepVerifier.create(input.collectList().flatMapMany(binarySorterService::sortByBinaryRepresentation))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals(INVALID_THREE_DIGIT_NUMBER))
                .verify();
    }

}
