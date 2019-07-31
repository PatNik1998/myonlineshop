package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.dto.*;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.*;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.CategoryServiceImpl;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AdministratorController {
    private AdministratorService adminService;
    private ClientService clientService;
    private CategoryService categoryService;
    private ProductService productService;
    private Sessions sessions;
    private UserService userService;

    @Autowired
    public AdministratorController(AdministratorServiceImpl adminService, ClientServiceImpl clientService, CategoryServiceImpl categoryService, ProductServiceImpl productService, Sessions sessions, UserService userService ) {
        this.adminService = adminService;
        this.clientService = clientService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.sessions = sessions;
        this.userService = userService;
    }


    @PostMapping(value = "/admins",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO registerAdmin(@RequestBody UserDTO dto, HttpServletResponse response) {
        UserDTO response1 = adminService.registerAdmin(dto);
        if(response1.getErrors().size() ==0){
            Cookie cookie = new Cookie("JAVASESSIONID",UUID.randomUUID().toString());
            sessions.addSession(cookie.getValue(),response1.getId());
            sessions.addTokens(response1.getId(),cookie);
            response.addCookie(cookie);
        }
        return response1;
    }

    @GetMapping(value = "/clients")
    public List<UserDTO> getClients(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        List<UserDTO> response = clientService.getClients(cookie.getValue());
        return response;

    }

    @PutMapping(value = "/admins")
    public UserDTO editAdmin(@RequestBody UserDTO dto, @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        UserDTO response = adminService.editAdminProfile(cookie.getValue(), dto);
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
        return categoryService.editCategory(categoryId,categoryDto);

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
    public ProductDTOWithIdCategories addProduct(@RequestBody ProductDTOWithIdCategories productDto, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        return productService.addProduct(productDto);
    }

    @GetMapping(value = "/userOrders/{clientId}/{page}")
    public OrdersListDTOWithTotal getOrdersByClient(@PathVariable Integer clientId, @PathVariable Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u != null){//
            return adminService.getOrdersListByClient(clientId, page);
        }else{
            OrdersListDTOWithTotal list = new OrdersListDTOWithTotal();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/productOrders/{productId}/{page}")
    public OrdersListDTOWithTotal getOrdersByProduct(@PathVariable Integer productId, @PathVariable Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u!=null){
            return adminService.getOrdersListByProduct(productId, page);
        }else{
            OrdersListDTOWithTotal list = new OrdersListDTOWithTotal();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/topCategorySales/{categoryId}/{count}")
    public TopSalesDto getTopCategorySales(@PathVariable Integer categoryId, @PathVariable Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u!=null){
            return adminService.getTopCategorySales(categoryId, count);
        }else {
            TopSalesDto list = new TopSalesDto();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/monthlyTopSales/{count}")
    public TopSalesDto getMonthlyTopSales(@PathVariable  Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u != null){
            return adminService.getMonthlyTopSales(count);
        }else {
            TopSalesDto list = new TopSalesDto();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/yearlyTopSales/{count}")
    public TopSalesDto getYearlyTopSales(@PathVariable Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u != null){
            return adminService.getYearlyTopSales(count);
        }else {
            TopSalesDto list = new TopSalesDto();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/topClientCategories/{clientId}/{count}")
    public CategoriesListDto getTopClientCategories(@PathVariable Integer clientId, @PathVariable Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u != null){
            return adminService.getTopClientCategories(clientId,count);
        }else{
            CategoriesListDto list = new CategoriesListDto();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/ordersList/{page}")
    public OrdersListDTOWithTotal getOrderList(@PathVariable Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(u != null){
            return adminService.getOrderList(page);
        }else{
            OrdersListDTOWithTotal list = new OrdersListDTOWithTotal();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

    @GetMapping(value = "/topOrdersByDates/{page}")
    public OrdersListDTOWithTotal getTopOrdersByDate(@PathVariable Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        Administrator u = (Administrator) sessions.getUser(cookie.getValue());
        if(true){//u != null){
            return adminService.getTopOrdersByDate(page);
        }else{
            OrdersListDTOWithTotal list = new OrdersListDTOWithTotal();
            list.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "user"));
            return list;
        }
    }

}



