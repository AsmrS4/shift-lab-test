package com.shift.crm.api.controller;

import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.api.models.responses.SellerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SellerResponse> retrieveSellers() {
        return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SellerResponse retrieveSellerDetails(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerResponse createSeller(@RequestBody CreateSellerRequest createRequest) {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SellerResponse updateSellerInfo(@PathVariable Long id, @RequestBody UpdateSellerRequest updateRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable Long id) {

    }
}
