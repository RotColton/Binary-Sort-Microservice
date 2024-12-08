package com.API.BinarySort.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.List;

public interface BinarySorterService {

    public Flux<Integer> sortByBinaryRepresentation(List<Integer> numbers);
}
