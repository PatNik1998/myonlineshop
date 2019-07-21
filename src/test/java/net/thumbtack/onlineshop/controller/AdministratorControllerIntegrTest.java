package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.dto.request.CategoryDto;
import net.thumbtack.onlineshop.dto.request.ProductDto;
import net.thumbtack.onlineshop.dto.request.UserDTO;

import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.entities.Product;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.UserServiceImpl;
import net.thumbtack.onlineshop.service.interfaces.DbService;
import net.thumbtack.onlineshop.service.interfaces.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministratorControllerIntegrTest {

    private TestRestTemplate testRestTemplate;
    private DbService dbService;
    private Sessions sessions;

    @Autowired
    public AdministratorControllerIntegrTest(TestRestTemplate testRestTemplate, DbService dbService, Sessions sessions){
        this.testRestTemplate = testRestTemplate;
        this.dbService = dbService;
        this.sessions = sessions;
    }

    @After
    public void clearAfter(){
        dbService.clear();
    }
    @Test
    public void testRegisterAdmin(){
        UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","Разработчик");
        userDTO.setLogin("patnik");
        userDTO.setPassword("1234567890");
        ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/api/admins",userDTO,UserDTO.class);
        assertThat(responseEntity.getBody().getErrors().isEmpty(), is(true));
        assertThat(responseEntity.getBody().getFirstName(), is(userDTO.getFirstName()));
        assertThat(responseEntity.getBody().getId(), notNullValue());

    }

    public UserDTO createUserDto(String firstName, String lastName, String patronymic, String position){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPatronymic(patronymic);
        userDTO.setPosition(position);


        return userDTO;

    }

    @Test
    public void testUpdateAdmin(){
        UserDTO userDTO = createUserDto("Никита","Патраков","Сергеевич","Разработчик");
        userDTO.setLogin("patnik");
        userDTO.setPassword("1234567890");
        ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/api/admins",userDTO,UserDTO.class);
        Cookie sessionId = sessions.getToken(responseEntity.getBody().getId());

        UserDTO userDTO1 = createUserDto("Иван", "Иванов","Иванович","Разработчик");
       userDTO1.setOldPassword(userDTO.getPassword());
       userDTO1.setNewPassword("5555555555");
       userDTO1.setCookie(sessionId);
        ResponseEntity<UserDTO> responseEntity1 = testRestTemplate.postForEntity("/api/admins",userDTO1,UserDTO.class);
        assertThat(responseEntity1.getBody().getErrors().isEmpty(),is(true));
        assertThat(responseEntity1.getBody().getFirstName(),is(userDTO1.getFirstName()));

    }

    @Test
    public void testAddCategory(){
        CategoryDto categoryDto = createCategoryDto("Техника",null);
        ResponseEntity<CategoryDto> responseEntity = testRestTemplate.postForEntity("/api/categories",categoryDto,CategoryDto.class);
        assertThat(responseEntity.getBody().getErrors().isEmpty(),is(true));
        assertThat(responseEntity.getBody().getName(),is(categoryDto.getName()));

    }

   @Test
   public void testGetCategory(){
       CategoryDto categoryDto = createCategoryDto("Техника",null);
       ResponseEntity<CategoryDto> responseEntity = testRestTemplate.postForEntity("/api/categories",categoryDto,CategoryDto.class);
       int id = responseEntity.getBody().getId();
       CategoryDto categoryDto1 = testRestTemplate.getForObject("/api/categories/{categoryId}",CategoryDto.class,id);
       assertThat(responseEntity.getBody().getName(),is(categoryDto1.getName()));
   }


    public CategoryDto createCategoryDto(String name, Integer id){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Техника");
        categoryDto.setParentId(id);
        return categoryDto;

    }

    @Test
    public void testDeleteCategory(){
        CategoryDto categoryDto = createCategoryDto("Техника",null);
        ResponseEntity<CategoryDto> responseEntity = testRestTemplate.postForEntity("/api/categories",categoryDto,CategoryDto.class);
        int id = responseEntity.getBody().getId();
        ResponseEntity<String> responseEntity1 = testRestTemplate.delete("/api/categories/{categoryId}", id);
        assertThat(responseEntity,is("{}"));
    }

    @Test
    public void testAddProduct(){
        ProductDto productDto = createProductDto("Мороженное",35);
        ResponseEntity<ProductDto> responseEntity = testRestTemplate.postForEntity("/api/products",productDto,ProductDto.class);
        assertThat(responseEntity.getBody().getName(),is(productDto.getName()));
        assertThat(responseEntity.getBody().getPrice(),is(productDto.getPrice()));
    }


   @Test
   public void testDeleteProduct(){
       ProductDto productDto = createProductDto("Мороженное",35);
       ResponseEntity<ProductDto> responseEntity = testRestTemplate.postForEntity("/api/products",productDto,ProductDto.class);
       int id = responseEntity.getBody().getId();
       ResponseEntity<String> responseEntity1 = testRestTemplate.delete("/api/products/{productId}", id);
       assertThat(responseEntity,is("{}"));
   }


    public ProductDto createProductDto(String name, int price){
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setPrice(price);
        return productDto;
    }




}
