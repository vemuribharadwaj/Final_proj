package com.example.demo.service.implementations;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product onBoardProduct(Product product)
    {
        if(product.getAvailableQuantity() < 0 || product.getUnitPrice() < 0)
        {
            throw new IllegalArgumentException("Invalid data");
        }
        Product saved = productRepository.save(product);
        return saved;
    }

    //TODO implement an stockupdate product functionality
    @Override
    public Product stockUpdateProduct(Long productId,Long newQuantity)
    {
       Optional<Product> optionalproduct =  productRepository.findById(productId);
       if(optionalproduct.isPresent())
       {
           Product product = optionalproduct.get();
           product.setAvailableQuantity(newQuantity);
           productRepository.save(product);
       }
       else
       {
           throw new ProductNotFoundException("Product with id " + productId + " not found");
       }
        return null;
    }
}
