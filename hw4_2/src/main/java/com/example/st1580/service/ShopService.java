package com.example.st1580.service;

import com.example.st1580.controller.ShopController;
import com.example.st1580.controller.dto.ProductDto;
import com.example.st1580.document.Product;
import com.example.st1580.document.User;
import com.example.st1580.repository.ProductRepository;
import com.example.st1580.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShopService implements ShopController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ShopService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Product> addProduct(ProductDto product) {
        Product newProduct = new Product(product.getName(), product.getRublePrice());
        return productRepository.save(newProduct);
    }

    @Override
    public Flux<Product> getAllProductInUserCurrency(String userId) {
        Mono<User> user = userRepository.findById(userId);
        Flux<Product> products = productRepository.findAll();
        return products.flatMap(p -> user.map(u -> p.getProductWithPriceInCurrency(p, u.getCurrencyType())));
    }
}
