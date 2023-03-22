package com.example.st1580.controller;

import com.example.st1580.controller.dto.ProductDto;
import com.example.st1580.document.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shop")
public interface ShopController {
    @PostMapping
    Mono<Product> addProduct(@RequestBody ProductDto product);

    @GetMapping("/{userId}")
    Flux<Product> getAllProductInUserCurrency(@PathVariable("userId") String userId);
}
