package com.example.demo.service.implementations;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.model.Reviews;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewsRepository;
import com.example.demo.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Reviews addReview(Long productId, Long customerId, Reviews review)
            throws CustomerNotFoundException, ProductNotFoundException {

        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }

        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id " + customerId);
        }

        review.setProduct(productOptional.get());
        review.setCustomer(customerOptional.get());
        review.setReviewDate(LocalDate.now());

        return reviewsRepository.save(review);
    }

    @Override
    public List<Reviews> getReviewsForProduct(Long productId)
    {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }

        return reviewsRepository.findByProductId(productId);
    }
}
