package com.springcrud.springboot_crud.services;

import com.springcrud.springboot_crud.entities.Product;
import com.springcrud.springboot_crud.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> update(Long id, Product product) {
            Optional<Product> productOptional = repository.findById(id);
           if(productOptional.isPresent()){
               Product productdb = productOptional.orElseThrow();

               productdb.setName(product.getName());
               productdb.setDescription(product.getDescription());
               productdb.setPrice(product.getPrice());
               return Optional.of(repository.save(productdb));
           }

            return productOptional;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(repository::delete);
        return productOptional;
    }
}
