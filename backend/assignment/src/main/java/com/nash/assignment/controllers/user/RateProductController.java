package com.nash.assignment.controllers.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.services.RateProductServiceImpl;

@RestController
@RequestMapping("/rate-product")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class RateProductController {

    @Autowired
    RateProductServiceImpl rateProductServiceImpl;

    @PatchMapping("/{id}/{star}")
    public ResponseEntity<RateProductDto> ratingProduct(@PathVariable int id, @PathVariable int star) {
        RateProductDto rating = rateProductServiceImpl.updateRate(id, star);
        return ResponseEntity.status(HttpStatus.OK).body(
                rating
        );
    }

    @GetMapping()
    public ResponseEntity<List<RateProductDto>> getRate() {
        return ResponseEntity.status(HttpStatus.OK).body(
                rateProductServiceImpl.getRate()
        );
    }
}
