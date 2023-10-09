package com.SpringBoot.clientsNotification.Service;

import com.SpringBoot.clientsNotification.Entities.ProductEntity;

import java.util.List;

public interface ProductService {
    String createNewProduct(ProductEntity product);
    ProductEntity getProductById(Long id);
    String deleteProduct(Long id);
    void updateProduct(ProductEntity product);
    List<ProductEntity> getAllProducts();
}
