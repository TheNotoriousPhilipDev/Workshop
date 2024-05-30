package com.springcrud.springboot_crud;

import com.springcrud.springboot_crud.controllers.ProductController;
import com.springcrud.springboot_crud.entities.Product;
import com.springcrud.springboot_crud.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100);
    }

    @Test
    void testList() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productService.findAll()).thenReturn(products);

        List<Product> result = productController.list();
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
    }

    @Test
    void testView() {
        when(productService.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<?> response = productController.view(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testViewNotFound() {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productController.view(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
        when(productService.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.create(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdate() {
        when(productService.update(eq(1L), any(Product.class))).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.update(1L, product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateNotFound() {
        when(productService.update(eq(1L), any(Product.class))).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.update(1L, product);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDelete() {
        when(productService.delete(1L)).thenReturn(Optional.of(product));

        ResponseEntity<?> response = productController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testDeleteNotFound() {
        when(productService.delete(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productController.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}


