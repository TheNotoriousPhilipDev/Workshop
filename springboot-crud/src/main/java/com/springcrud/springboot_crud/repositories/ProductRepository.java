package com.springcrud.springboot_crud.repositories;

import com.springcrud.springboot_crud.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
