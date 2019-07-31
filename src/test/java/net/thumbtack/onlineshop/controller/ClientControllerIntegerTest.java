package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Product;
import net.thumbtack.onlineshop.service.DbService;
import net.thumbtack.onlineshop.service.Sessions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerIntegerTest {
    private TestRestTemplate testRestTemplate;
    private DbService dbService;
    private Sessions sessions;
    private ProductDao productDao;

    @Autowired
    public ClientControllerIntegerTest(TestRestTemplate testRestTemplate, DbService dbService, Sessions sessions, ProductDaoImpl productDao){
        this.testRestTemplate = testRestTemplate;
        this.dbService = dbService;
        this.sessions = sessions;
        this.productDao = productDao;
    }


    @After
    public void clearAfter(){
        dbService.clear();
    }

    public UserDTO createUserDto(String firstName, String lastName, String patronymic,String email, String address,String phone){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPatronymic(patronymic);
        userDTO.setEmail(email);
        userDTO.setAddress(address);
        userDTO.setPhone(phone);

        return userDTO;

    }

   @Test
    public void testRegisterClient(){
       UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","ya.patnik@yandex.ru","Саратов","89379620822");
       userDTO.setLogin("patnik");
       userDTO.setPassword("1234567890");

       ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/api/clients",userDTO,UserDTO.class);
       assertThat(responseEntity.getBody().getErrors().isEmpty(),is(true));
       assertThat(responseEntity.getBody().getId(),notNullValue());
       assertThat(responseEntity.getBody().getFirstName(),is(userDTO.getFirstName()));
   }


   @Test
    public void testUpdateClient(){
       UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","ya.patnik@yandex.ru","Саратов","89379620822");
       userDTO.setLogin("patnik");
       userDTO.setPassword("1234567890");

       ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/api/clients",userDTO,UserDTO.class);

       UserDTO userDTO1 = createUserDto("Ваня","Иванов","Сергеевич","ya.pnik@yandex.ru","Саратов","89379620822");
       userDTO.setLogin("pat");
       userDTO.setPassword("34fg567890");

       HttpEntity<UserDTO> httpEntity = new HttpEntity<>(userDTO1);
       ResponseEntity<UserDTO> responseEntity1 = testRestTemplate.exchange("/api/clients",HttpMethod.PUT,httpEntity,UserDTO.class);
       assertThat(responseEntity1.getBody().getErrors().isEmpty(),is(true));
       assertThat(responseEntity.getBody().getFirstName(),is(userDTO1.getFirstName()));

   }

   @Test
    public void testDeposit(){
       UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","ya.patnik@yandex.ru","Саратов","89379620822");
       userDTO.setLogin("patnik");
       userDTO.setPassword("1234567890");

       ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/api/clients",userDTO,UserDTO.class);
        userDTO.setDeposit(1000);

        HttpEntity<UserDTO> httpEntity = new HttpEntity<UserDTO>(userDTO);
        ResponseEntity<UserDTO> responseEntity1 = testRestTemplate.exchange("/api/deposits",HttpMethod.PUT,httpEntity,UserDTO.class);
        assertThat(responseEntity1.getBody().getErrors().isEmpty(),is(true));
        assertThat(responseEntity1.getBody().getDeposit(),is(userDTO.getDeposit()));

   }

   @Test
    public void testGetDeposit(){
       UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","ya.patnik@yandex.ru","Саратов","89379620822");
       userDTO.setLogin("patnik");
       userDTO.setPassword("1234567890");

       ResponseEntity<UserDTO> responseEntity = testRestTemplate.getForEntity("/api/clients",UserDTO.class);
       userDTO.setDeposit(1000);
       UserDTO userDTO1 = testRestTemplate.getForObject("/api/deposits",UserDTO.class);

       assertThat(userDTO1.getErrors().isEmpty(),is(true));

    }

    @Test
    public void testByProduct(){

        Product product = new Product();
        product.setName("Мороженное");
        product.setPrice(35);
        product.setCount(65);
        productDao.add(product);

        ProductDto productDto = createProductDto(product.getIdProduct(),product.getName(),product.getPrice(),3);
        ResponseEntity<ProductDto> responseEntity1 = testRestTemplate.postForEntity("/api/purchases",productDto,ProductDto.class);
        assertThat(responseEntity1.getBody().getName(),is(productDto.getName()));

    }

    public ProductDto createProductDto(int id, String name, int price, int count){
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setCount(count);

        return productDto;

    }

    @Test
    public void testAddToCart(){
        Product product = new Product();
        product.setName("Мороженное");
        product.setPrice(35);
        product.setCount(65);
        productDao.add(product);

        ProductDto productDto = createProductDto(product.getIdProduct(),product.getName(),product.getPrice(),4);
        ResponseEntity<ProductDto[]> responseEntity = testRestTemplate.postForEntity("/api/baskets", productDto, ProductDto[].class);
        assertThat(responseEntity.getBody().length>0,is(true));
    }
}
