package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    private ClientDaoImpl mockClientDao;
    private ProductDaoImpl mockProductDao;
    private CartService service;

    @Before
    public void init() {
        mockClientDao = mock(ClientDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);
        service = new CartServiceImpl(mockClientDao, mockProductDao);
    }

    @Test
    public void testBuyProduct() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(10);
        dto.setName("Product1");
        dto.setPrice(1500);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        dto.setPrice(product.getPrice());
        dto.setCount(product.getCount() + 1);
        service.buyProduct(client, dto);

    }

    @Test
    public void testBuyProduct2() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(10);
        dto.setName("Product1");
        dto.setPrice(1500);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        dto.setCount(null);///if null dto.count = 1

        service.buyProduct(client, dto);

    }

    @Test
    public void testBuyProduct3() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(10);
        dto.setName("Product1");
        dto.setPrice(1500);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        client.setDeposit(10000);
        ProductDto actual = service.buyProduct(client, dto);
        ProductDto expected = new ProductDto(dto);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(8500, client.getDeposit());
    }

    @Test
    public void testBuyProduct4() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(10);
        dto.setName("Product1");
        dto.setPrice(1500);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        client.setDeposit(10000);
        dto.setCount(4);
        ProductDto expected = new ProductDto(dto);
        ProductDto actual = service.buyProduct(client, dto);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4000, client.getDeposit());
    }

    @Test
    public void testAddProductToCart() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(product.getIdProduct());
        dto.setCount(25);
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        ProductDto response1 = new ProductDto(product);
        response1.setCount(dto.getCount());
        List<ProductDto> expected = new ArrayList<>();
        expected.add(response1);

        when(mockProductDao.getById(anyInt())).thenReturn(product);
        List<ProductDto> actual = service.addProductToCart(client, dto);
        Assert.assertEquals(expected, actual);
        verify(mockClientDao, atLeastOnce()).update(any());
    }

    @Test
    public void testAddProductToCart2() {
        Product product = new Product(10, 1500, "Product1", 100);
        Product product2 = new Product(11, 4500, "Product2", 12);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        ProductDto dto = new ProductDto();
        dto.setId(product.getIdProduct());
        dto.setCount(25);
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        ProductDto response1 = new Product(product);
        response1.setCount(dto.getCount());
        List<ProductDto> expected = new ArrayList<>();
        expected.add(response1);
        ProductDto dto2 = new ProductDto();
        dto2.setId(product2.getIdProduct());
        dto2.setName(product2.getName());
        dto2.setPrice(product2.getPrice());

        ProductDto response2 = new ProductDto(product2);
        response2.setCount(1);
        expected.add(response2);
        List<ProductDto> actual;

        when(mockProductDao.getById(anyInt())).thenReturn(product);
        service.addProductToCart(client, dto);

        when(mockProductDao.getById(anyInt())).thenReturn(product2);
        actual = service.addProductToCart(client, dto2);

        Assert.assertEquals(expected, actual);
        verify(mockClientDao, atLeastOnce()).update(any());
    }

    @Test
    public void testRemoveProductFromCart() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);

        //case1
        when(mockProductDao.getById(anyInt())).thenReturn(null);
        service.deleteProductFromCart(client, -10);
        fail();


    }

    @Test
    public void testRemoveProductFromCart2() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        service.deleteProductFromCart(client, product.getIdProduct());
        fail();
    }

    @Test
    public void testRemoveProductFromCart3() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);
        client.addItem(product, 10);
        when(mockProductDao.getById(anyInt())).thenReturn(product);
        String result = service.deleteProductFromCart(client, product.getIdProduct());
        Assert.assertEquals(true, client.getItems().isEmpty());
        Assert.assertEquals("{}", result);
    }

    @Test
    public void testEditCountProductFromCart() {
        Client client = new Client("", "", "", "", "", "", "", "", 0);

        ProductDto dto = new ProductDto();
        when(mockProductDao.getById(anyInt())).thenReturn(null);
        service.editProductOfCart(client, dto);
        fail();


    }

    @Test
    public void testEditCountProductFromCart2() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);

        when(mockProductDao.getById(anyInt())).thenReturn(product);
        service.deleteProductFromCart(client, product.getIdProduct());
        fail();

    }

    @Test
    public void testEditCountProductFromCart3() {
        Product product = new Product(10, 1500, "Product1", 100);
        Client client = new Client("", "", "", "", "", "", "", "", 0);

        ProductDto dto = new ProductDto();
        dto.setId(product.getIdProduct());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCount(10);
        when(mockProductDao.getById(anyInt())).thenReturn(product);

        client.addItem(product, 1);
        List<ProductDto> expected = new ArrayList<>();
        expected.add(
                new ProductDto(product) {{
                    setCount(dto.getCount());
                }}
        );
        List<ProductDto> actual = service.editProductOfCart(client, dto);
        Assert.assertEquals(expected, actual);
    }


}

