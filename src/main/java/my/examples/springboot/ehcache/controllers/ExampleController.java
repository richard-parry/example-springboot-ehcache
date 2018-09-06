package my.examples.springboot.ehcache.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.examples.springboot.ehcache.model.Product;
import my.examples.springboot.ehcache.repository.ProductRepository;

@RestController
public class ExampleController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);

    @Autowired ProductRepository repo;
    
    
    @RequestMapping("/product/{productId}")
    public Product findById(@PathVariable("productId") String productId) {
        LOGGER.info("Find by {}: " , productId);
        return repo.findById(productId);
    }
    
    @Cacheable("byType")
    @RequestMapping("/product/type/{type}")
    public List<Product> findByType(@PathVariable("type") String type) {
        LOGGER.info("Find by typpe: {}" , type);
        return repo.findByType(type);
    }
    

    @RequestMapping("/clear-cache")
    @CacheEvict(cacheNames = {"byId","byType"}, allEntries=true)
    public void clearCache() {
    }
}
