package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.dto.CategoryDto;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.service.*;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.CategoryServiceImpl;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.impl.ProductServiceImpl;
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
    private Sessions sessions;

    @Autowired
    public AdministratorController(AdministratorServiceImpl adminService, ClientServiceImpl clientService, CategoryServiceImpl categoryService, ProductServiceImpl productService, Sessions sessions ) {
        this.adminService = adminService;
        this.clientService = clientService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.sessions = sessions;
    }


    @PostMapping(value = "/admins",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO registerAdmin(@RequestBody UserDTO dto) {
        UserDTO response = adminService.registerAdmin(dto);
        return response;
    }

    @GetMapping(value = "/clients")
    public List<UserDTO> getClients(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        List<UserDTO> response = clientService.getClients(cookie.getValue());
        return response;

    }

    @PutMapping(value = "/admins")
    public UserDTO editAdmin(@RequestBody UserDTO dto,@RequestHeader @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        UserDTO response = adminService.editAdminProfile(dto.getCookie().getValue(), dto);
        return response;


    }

    @PostMapping(value = "/categories")
    public CategoryDto addCategory(@RequestBody CategoryDto dto, @CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        return categoryService.addCategory(dto);


    }

    @GetMapping(value = "/categories/{categoryId}")
    public CategoryDto getCategory(@PathVariable int categoryId, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        return categoryService.getCategoryById(categoryId);

    }

    @PutMapping(value = "/categories/{categoryId}")
    public CategoryDto editCategory(@PathVariable int categoryId,@RequestBody CategoryDto categoryDto, @CookieValue(value = "JAVASESSIONID")Cookie cookie ){
        return categoryService.addCategory(categoryDto);

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



