package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.dto.*;
import net.thumbtack.onlineshop.entities.User;
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
        Cookie cookie = new Cookie("JAVASESSIONID",UUID.randomUUID().toString());
        response.addCookie(cookie);
        sessions.addSession(cookie.getValue(),response1.getId());
        return response1;
    }

    @GetMapping(value = "/clients")
    public List<UserDTO> getClients(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        List<UserDTO> response = clientService.getClients(cookie.getValue());
        return response;

    }

    @PutMapping(value = "/admins")
    public UserDTO editAdmin(@RequestBody UserDTO dto,@RequestHeader @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
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

    @GetMapping(value = "/userOrders/")
    public OrdersListDTOWithTotal getOrdersByClient(@RequestParam(name = "clientId") Integer clientId, @RequestParam(name = "page") Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getOrdersListByClient(clientId, page);
        }
        return new OrdersListDTOWithTotal();
    }

    @GetMapping(value = "/productOrders/")
    public OrdersListDTOWithTotal getOrdersByProduct(@RequestParam(name = "productId") Integer productId, @RequestParam(name = "page") Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getOrdersListByProduct(productId, page);
        }
        return new OrdersListDTOWithTotal();
    }

    @GetMapping(value = "/topCategorySales/")
    public TopSalesDto getTopCategorySales(@RequestParam(name = "categoryId") Integer categoryId, @RequestParam(name = "count") Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getTopCategorySales(categoryId, count);
        }
        return new TopSalesDto();
    }

    @GetMapping(value = "/monthlyTopSales/")
    public TopSalesDto getMonthlyTopSales(@RequestParam(name = "count") Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getMonthlyTopSales(count);
        }
        return new TopSalesDto();
    }

    @GetMapping(value = "/yearlyTopSales/")
    public TopSalesDto getYearlyTopSales(@RequestParam(name = "count") Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getYearlyTopSales(count);
        }
        return new TopSalesDto();
    }

    @GetMapping(value = "/topClientCategories/")
    public CategoriesListDto getTopClientCategories(@RequestParam(name = "clientId") Integer clientId, @RequestParam(name = "count") Integer count, @CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getTopClientCategories(clientId,count);
        }
        return new CategoriesListDto();
    }

    @GetMapping(value = "/ordersList/")
    public OrdersListDTOWithTotal getOrderList(@RequestParam(name = "page") Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getOrderList(page);
        }
        return new OrdersListDTOWithTotal();
    }

    @GetMapping(value = "/topOrdersByDates/")
    public OrdersListDTOWithTotal getTopOrdersByDate(@RequestParam(name = "page") Integer page, @CookieValue(value = "JAVASESSIONID") Cookie cookie){
        User u = sessions.getUser(cookie.getValue());
        if(u != null){//TODO: проверка на админа
            return adminService.getTopOrdersByDate(page);
        }
        return new OrdersListDTOWithTotal();
    }

}



