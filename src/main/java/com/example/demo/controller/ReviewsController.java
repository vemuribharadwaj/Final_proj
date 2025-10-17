package com.example.demo.controller;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Reviews;
import com.example.demo.repository.ReviewsRepository;
import com.example.demo.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController
{
    @Autowired
    private ReviewsService reviewsService;


        @PostMapping("/add")
        public Reviews addRating(@RequestParam Long productId, @RequestParam Long customerId, @RequestBody Reviews review) throws CustomerNotFoundException {
            return reviewsService.addReview(productId, customerId, review);
        }

        @GetMapping("/product/{productId}")
        public List<Reviews> getRatingsForProduct(@PathVariable Long productId) {
            return reviewsService.getReviewsForProduct(productId);
        }
    }


