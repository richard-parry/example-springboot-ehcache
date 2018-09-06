package my.examples.springboot.ehcache.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import my.examples.springboot.ehcache.model.Product;
import my.examples.springboot.ehcache.repository.ProductRepository;

@Repository
public class ProductDao implements ProductRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

    private static final List<Product> ALL_PRODUCTS = Arrays.asList(
            new Product("1", "2l Paint", "paint"),
            new Product("2", "4l Paint", "paint"),
            new Product("3", "Roller", "tools"),
            new Product("4", "Brush", "tools"),
            new Product("5", "AAA Batteries", "Batteries")
    );

    @Cacheable("byId")
    @Override
    public Product findById(String productId)
    {
        LOGGER.info("datastore - Find by id: {}" , productId);
        return ALL_PRODUCTS.parallelStream()
                    .filter(p -> productId.equalsIgnoreCase(p.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("product not found"));
    }

    @Cacheable("byType")
    @Override
    public List<Product> findByType(String type)
    {
        LOGGER.info("datastore - Find by type: {}" , type);
        return ALL_PRODUCTS.parallelStream()
                    .filter(p -> type.equalsIgnoreCase(p.getType()))
                    .collect(Collectors.toList());
    }

}
