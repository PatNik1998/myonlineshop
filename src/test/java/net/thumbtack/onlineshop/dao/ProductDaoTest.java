package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.CategoryDao;
import net.thumbtack.onlineshop.dao.interfaces.ProductDao;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.entities.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDaoTest {

    ProductDao productDao = new ProductDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Before
    public void clear() {
        productDao.clear();
        categoryDao.clear();
    }

    @Test
    public void testGetProductById() {
        Product product = new Product(0,1000,"Product",2);
        productDao.add(product);

        Product productFromDb = productDao.getById(product.getIdProduct());

        Assert.assertEquals(product, productFromDb);
    }

    @Test
    public void testInsertProductToDb() {
        for(int i = 0; i < 5; i++) {
            Product product = new Product(0,1000,"Product" + i,2);
            productDao.add(product);
            Assert.assertTrue(productDao.getAll().contains(product));
        }
    }

    @Test
    public void testAddCategoryToProduct() {
        Product product = new Product(0,1000,"Product",2);
        Category category = new Category("category",null);

        categoryDao.add(category);
        product.addCategory(category);
        productDao.add(product);

        Product productFromDb = productDao.getById(product.getIdProduct());
        Set<Category> categoriesOfProduct = productFromDb.getCategories();
        Assert.assertTrue(categoriesOfProduct.contains(category));
        Assert.assertSame(1, categoriesOfProduct.size());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(0,1000,"Product",2);
        productDao.add(product);

        productDao.delete(product.getIdProduct());
        Product productFromDb = productDao.getById(product.getIdProduct());
        Assert.assertEquals(null, productFromDb);
    }

    @Test
    public void testDeleteCategoryOfProduct() {
        Product product = new Product(0,1000,"Product",2);
        Category category = new Category("category",null);

        categoryDao.add(category);
        product.addCategory(category);
        productDao.add(product);

        Product productFromDb = productDao.getById(product.getIdProduct());
        productFromDb.deleteCategory(category);
        productDao.update(productFromDb);

        productFromDb = productDao.getById(product.getIdProduct());
        Assert.assertTrue(productFromDb.getCategories().isEmpty());
    }

    @Test
    public void testGetAllProducts() {
        List <Product> expectedProducts = new ArrayList<Product>();

        for(int i = 0; i < 5; i++) {
            Product product = new Product(0,1000,"Product" + i,2);
            productDao.add(product);
            expectedProducts.add(product);
        }
        Assert.assertEquals(expectedProducts, productDao.getAll());
    }
}
