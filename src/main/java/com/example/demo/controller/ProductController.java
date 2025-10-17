package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;


    @PostMapping("/create")
    public Product onBoardProduct(@RequestBody Product product)
    {
        return productService.onBoardProduct(product);
    }

    //TODO Updating stock of product

    @PutMapping("/{id}/updateStock")
    public ResponseEntity<String> updatingStock(@PathVariable Long id, @RequestBody Map<String,Long> incomingStockData)
    {
        Long newQuantity = incomingStockData.get("newStock");

        productService.stockUpdateProduct(id, newQuantity);
        return new ResponseEntity<>("Stock updated successfully.", HttpStatus.OK);

    }
}
