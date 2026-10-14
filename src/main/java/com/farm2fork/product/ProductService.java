package com.farm2fork.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(String search) {
        if (search == null || search.isBlank()) return repository.findAll();
        return repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    public Product update(Long id, Product input) {
        Product current = findById(id);
        current.setName(input.getName());
        current.setCategory(input.getCategory());
        current.setDescription(input.getDescription());
        current.setPrice(input.getPrice());
        current.setQuantity(input.getQuantity());
        current.setUnit(input.getUnit());
        current.setImageUrl(input.getImageUrl());
        return repository.save(current);
    }

    public void delete(Long id) {
        Product current = findById(id);
        repository.delete(current);
    }
}
