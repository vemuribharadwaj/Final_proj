package com.example.demo.service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Reviews;

import java.util.List;

public interface ReviewsService
{
    Reviews addReview(Long productId, Long customerId, Reviews review) throws CustomerNotFoundException;
    List<Reviews> getReviewsForProduct(Long productId);
}
