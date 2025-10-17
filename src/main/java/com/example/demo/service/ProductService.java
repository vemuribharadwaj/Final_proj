package com.example.demo.service;

import com.example.demo.model.Product;

public interface ProductService
{
    Product onBoardProduct(Product product);

    Product stockUpdateProduct(Long productId,Long newQuantity);


}
