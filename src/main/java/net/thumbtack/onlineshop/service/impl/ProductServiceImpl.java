package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.CategoryDao;
import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.entities.Product;
import net.thumbtack.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;
    private CategoryDao categoryDao;

    @Autowired
    public ProductServiceImpl(ProductDaoImpl productDao, CategoryDaoImpl categoryDao){
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public ProductDto getProduct(int idProduct){
        Product product = productDao.getById(idProduct);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getIdProduct());
        productDto.setName(product.getName());
        productDto.setCount(product.getCount());
        productDto.setPrice(product.getPrice());
        productDto.setCategories(product.getCategories());

        return productDto;
    }

    public ProductDto addProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCount(productDto.getCount());
        Set<Category> categories = productDto.getCategories();

        for(Category category : categories){
            Category cat = category;
            product.addCategory(category);
        }

        productDao.add(product);

        return productDto;
    }

    public String deleteProduct(int productId){
        productDao.delete(productId);
        return "{}";
    }
}

