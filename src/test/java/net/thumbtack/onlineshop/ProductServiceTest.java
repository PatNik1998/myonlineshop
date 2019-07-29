

import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;

import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.entities.Product;

import net.thumbtack.onlineshop.service.ProductService;
import net.thumbtack.onlineshop.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductDaoImpl mockProductDao;
    private CategoryDaoImpl mockCategoryDao;
    private ProductService service;

    @Before
    public void init() {
        mockProductDao = mock(ProductDaoImpl.class);
        mockCategoryDao = mock(CategoryDaoImpl.class);
        service = new ProductServiceImpl(mockProductDao, mockCategoryDao);
    }




    @Test
    public void testGetProducts() {
        int id = 1234;
        Product product = new Product();
        product.setIdProduct(id);
        product.setName("product");
        product.setCount(10);

        Product product2 = new Product();
        product2.setIdProduct(id);
        product2.setName("product2");
        product2.setCount(100);

        Product product3 = new Product();
        product3.setIdProduct(id);
        product3.setName("product3");
        product3.setCount(123);
        List<Product> products = Arrays.asList(product, product2, product3);
        List<ProductDto> expected = new ArrayList<>();
        for (Product p : products) {
            expected.add(new ProductDto(p));
        }
        when(mockProductDao.getAll()).thenReturn(products);
        List<ProductDto> actual = service.getProducts(null, "");
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testAddProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("prod");
        dto.setCount(100);
        dto.setPrice(100);
        int[] categories = {1, 2, 3, 4};
        dto.setCategories(categories);
        when(mockCategoryDao.getById(anyInt())).thenReturn(null);
            service.addProduct(dto);
            fail();

    }

    @Test
    public void testAddProduct2(){
        ProductDto dto = new ProductDto();
        dto.setName("prod");
        dto.setCount(100);
        dto.setPrice(100);
        int[] categories = {1, 2, 3, 4};
        dto.setCategories(categories);

        int id = 123;
        Category category = new Category();
        category.setIdCategory(id);
        category.setName("category1");
        Product product = new Product();
        product.setCount(dto.getCount());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        ProductDto expected = new ProductDto(product);
        expected.setCategories(categories);

        when(mockCategoryDao.getById(anyInt())).thenReturn(category);
        ProductDto actual = service.addProduct(dto);
        Assert.assertEquals(expected, actual);
    }


    }
    @Test
    public void testDeleteProduct2(){
        int productId = 123;
        Product product = new Product();
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        String actual = service.deleteProduct(productId);
        String expected = "{}";
        Assert.assertEquals(expected, actual);
        verify(mockProductDao, atLeastOnce()).update(any());
    }

   }
