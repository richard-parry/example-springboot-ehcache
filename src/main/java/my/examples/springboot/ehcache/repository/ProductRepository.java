package my.examples.springboot.ehcache.repository;

import java.util.List;

import my.examples.springboot.ehcache.model.Product;

public interface ProductRepository
{
    Product findById(String productId);
    List<Product> findByType(String type);
}
