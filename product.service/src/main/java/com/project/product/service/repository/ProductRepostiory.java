package com.project.product.service.repository;

import com.project.product.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepostiory extends MongoRepository<Product,String> {
}
