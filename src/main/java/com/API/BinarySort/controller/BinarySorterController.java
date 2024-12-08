package com.API.BinarySort.controller;

import com.API.BinarySort.service.BinarySorterServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class BinarySorterController {

    private final BinarySorterServiceImp binarySorterServiceImp;

    public BinarySorterController(BinarySorterServiceImp binarySorterServiceImp) {
        this.binarySorterServiceImp = binarySorterServiceImp;
    }


    @PostMapping("/binary-sort")
    public ResponseEntity<Flux<Integer>> sortNumbers(@RequestBody List<Integer> numbers){
        return ResponseEntity.ok(binarySorterServiceImp.sortByBinaryRepresentation(numbers));
    }
}
