package com.API.BinarySort.controller;

import com.API.BinarySort.service.BinarySorterServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import org.springframework.web.server.ServerWebInputException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.API.BinarySort.exception.ErrorMessages.*;


@WebFluxTest(controllers = BinarySorterController.class)
public class BinarySortControllerTest {


    @MockitoBean
    private BinarySorterServiceImp binarySorterServiceImp;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testSortNumbers_Success() {
        List<Integer> inputNumbers = Arrays.asList(3, 5, 2, 8);
        List<Integer> sortedNumbers = Arrays.asList(2, 8, 3, 5);

        Mockito.when(binarySorterServiceImp.sortByBinaryRepresentation(inputNumbers))
               .thenReturn(Flux.fromIterable(sortedNumbers));

        webTestClient.post()
                .uri("/binary-sort")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inputNumbers)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .isEqualTo(sortedNumbers);

        Mockito.verify(binarySorterServiceImp, Mockito.times(1)).sortByBinaryRepresentation(inputNumbers);
    }

    @Test
    public void testSortNumbers_EmptyList() {
        List<Integer> emptyInput = List.of();

        Mockito.when(binarySorterServiceImp.sortByBinaryRepresentation(emptyInput))
                .thenThrow(new IllegalArgumentException(EMPTY_LIST));

        webTestClient.post()
                .uri("/binary-sort")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(emptyInput)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo(EMPTY_LIST);
    }

    @Test
    public void testSortNumbers_ExceedsMaxAllowedSize() {
        List<Integer> exceedsInput = Flux.range(100, 51).collectList().block();

        Mockito.when(binarySorterServiceImp.sortByBinaryRepresentation(exceedsInput))
                .thenThrow(new IllegalArgumentException(LIST_TOO_LARGE));

        webTestClient.post()
                .uri("/binary-sort")
                .bodyValue(Objects.requireNonNull(exceedsInput))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo(LIST_TOO_LARGE);
    }

    @Test
    public void testSortNumbers_NonThreeDigitNumbers() {
        List<Integer> input = Arrays.asList(99, 250, 1000);

        Mockito.when(binarySorterServiceImp.sortByBinaryRepresentation(input))
                .thenThrow(new IllegalArgumentException(INVALID_THREE_DIGIT_NUMBER));

        webTestClient.post()
                .uri("/binary-sort")
                .bodyValue(input)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo(INVALID_THREE_DIGIT_NUMBER);
    }

}
