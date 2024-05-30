package com.springcrud.springboot_crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcrud.springboot_crud.entities.Product;
import com.springcrud.springboot_crud.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100);
        product = productRepository.save(product);
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Test Product")));
    }

    @Test
    void testView() throws Exception {
        mockMvc.perform(get("/products/{id}", product.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Product")));
    }

    @Test
    void testCreate() throws Exception {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setDescription("New Description");
        newProduct.setPrice(200);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    void testUpdate() throws Exception {
        product.setName("Updated Product");

        mockMvc.perform(put("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Updated Product")));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/products/{id}", product.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Product> deletedProduct = productRepository.findById(product.getId());
        assert(deletedProduct.isEmpty());
    }
}
