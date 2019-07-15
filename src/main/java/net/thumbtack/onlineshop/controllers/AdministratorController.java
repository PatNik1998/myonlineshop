package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dto.request.CategoryDto;
import net.thumbtack.onlineshop.dto.request.ProductDto;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.CategoryServiceImpl;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.impl.ProductServiceImpl;
import net.thumbtack.onlineshop.service.AdministratorService;
import net.thumbtack.onlineshop.service.CategoryService;
import net.thumbtack.onlineshop.service.ClientService;
import net.thumbtack.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdministratorController {
    private AdministratorService adminService;
    private ClientService clientService;
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public AdministratorController(AdministratorServiceImpl adminService, ClientServiceImpl clientService, CategoryServiceImpl categoryService,ProductServiceImpl productService ) {
        this.adminService = adminService;
        this.clientService = clientService;
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @PostMapping(value = "/admins",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO registerAdmin(@RequestBody UserDTO dto) {
        UserDTO response = adminService.registerAdmin(dto);
        return response;
    }

    @GetMapping(value = "/clients")
    public List<UserDTO> getClients(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        User user = Sessions.getUser(cookie.getValue());
            List<UserDTO> response = clientService.getClients(cookie.getValue());
            return response;

    }

    @PutMapping(value = "/admins")
    public UserDTO editAdmin(@RequestBody UserDTO dto, @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        User user = Sessions.getUser(cookie.getValue());
        if (user instanceof Administrator) {
            UserDTO response = adminService.editAdminProfile(cookie.getValue(), dto);
            return response;
        }
        dto.addError(new UserServiceError(UserErrorCode.ACCESS_DENIED, "Доступ запрещен", "cookie"));
        Validator.clearField(dto);
        return dto;

    }

    @PostMapping(value = "/categories")
    public CategoryDto addCategory(@RequestBody CategoryDto dto, @CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        User user = Sessions.getUser(cookie.getValue());
        if (user instanceof Administrator) {
            return categoryService.addCategory(dto);
        }
        dto.addError(new UserServiceError(UserErrorCode.ACCESS_DENIED, "Доступ запрещен", "cookie"));
        Validator.setNullForFieldsCategory(dto);
        return dto;
    }

    @GetMapping(value = "/categories/{categoryId}")
    public CategoryDto getCategory(@PathVariable int categoryId, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
            return categoryService.getCategoryById(categoryId);

    }

    @PutMapping(value = "/categories/{categoryId}")
    public CategoryDto editCategory(@PathVariable int categoryId,@RequestBody CategoryDto categoryDto, @CookieValue(value = "JAVASESSIONID")Cookie cookie ){
        User user = Sessions.getUser(cookie.getValue());
        if (user instanceof Administrator) {
            return categoryService.addCategory(categoryDto);
        }
        categoryDto.addError(new UserServiceError(UserErrorCode.ACCESS_DENIED, "Доступ запрещен", "cookie"));
        Validator.setNullForFieldsCategory(categoryDto);
        return categoryDto;
    }

    @DeleteMapping(value = "/categories/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId, @CookieValue(value = "JAVASESSIONID")Cookie cookie){
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> getCategories(@CookieValue(value = "JAVASESSIONID")Cookie cookie){
        return categoryService.getCategories();
    }

   @DeleteMapping(value = "/products/{productId}")
    public String deleteProduct(@PathVariable int productId, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        return productService.deleteProduct(productId);
   }

   @PostMapping(value = "/products")
    public ProductDto addProduct(@RequestBody ProductDto productDto, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        return productService.addProduct(productDto);
   }

}
