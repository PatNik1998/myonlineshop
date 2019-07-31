package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.UserDao;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dto.*;
import net.thumbtack.onlineshop.entities.*;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.AdministratorService;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private AdministratorDao adminDao;
    private Validator validator;
    private Sessions sessions;
    private UserService userService;
    private UserDao userDao;

    @Autowired
    public AdministratorServiceImpl(AdministratorDaoImpl adminDao,Validator validator, Sessions sessions, UserDao userDao) {
        this.adminDao = adminDao;
        this.validator = validator;
        this.sessions = sessions;
        this.userDao = userDao;
    }


    public UserDTO registerAdmin(UserDTO dto) {
        validator.registerAdminValidate(dto);
        if (dto.getErrors().isEmpty()) {
            Administrator admin = new Administrator();
            admin.setFirstName(dto.getFirstName());
            admin.setLastName(dto.getLastName());
            admin.setPatronymic(dto.getPatronymic());
            admin.setLogin(dto.getLogin());
            admin.setPassword(dto.getPassword());
            admin.setPosition(dto.getPosition());
            adminDao.add(admin);
            dto.setId(admin.getId());
            dto.setPassword(null);
            dto.setLogin(null);
            return dto;
        }
        return  dto;
    }

    public UserDTO editAdminProfile(String sessionId, UserDTO adminDTO) {
        Administrator admin = (Administrator) sessions.getUser(sessionId);
        if(admin== null){
            validator.clearField(adminDTO);
            adminDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "client"));
            return adminDTO;
        }
        validator.editAdminValidate(adminDTO);
        if (adminDTO.getErrors().isEmpty()) {
            admin.setPassword(adminDTO.getNewPassword());
            admin.setFirstName(adminDTO.getFirstName());
            admin.setLastName(adminDTO.getLastName());
            admin.setPatronymic(adminDTO.getPatronymic());
            admin.setPosition(adminDTO.getPosition());
            adminDTO.setCookie(null);
            adminDao.update(admin);

            adminDTO.setId(admin.getId());
            adminDTO.setOldPassword(null);
            adminDTO.setNewPassword(null);
            return adminDTO;
        }
        return adminDTO;
    }

    public OrdersListDTOWithTotal getOrdersListByClient(Integer clientId, Integer page){
        List<Orders> orders = adminDao.getOrdersOfClient(clientId, page);
        return getDtoByOrders(orders);
    }

    public OrdersListDTOWithTotal getOrdersListByProduct(Integer productId, Integer page){
        List<Orders> orders = adminDao.getOrdersOfProduct(productId, page);
        return getDtoByOrders(orders);
    }

    public TopSalesDto getTopCategorySales(Integer categoryId, Integer count){
        List<Product> products = adminDao.getTopSalesByCategory(categoryId, count);
        return getTopSalesByProductList(products);
    }

    public CategoriesListDto getTopClientCategories(Integer clientId, Integer count){
        List<Category> categories = adminDao.getTopClientCategories(clientId, count);
        CategoriesListDto top = new CategoriesListDto();
        top.setCategories(new ArrayList<>());

        for(Category category : categories){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getIdCategory());
            if(category.getParentCategory() != null)
                categoryDto.setParentId(category.getParentCategory().getIdCategory());

            top.getCategories().add(categoryDto);
        }
        return top;
    }


    public TopSalesDto getMonthlyTopSales(Integer count){
        List<Product> products = adminDao.getMonthlyTopSales(count);
        return getTopSalesByProductList(products);
    }

    public TopSalesDto getYearlyTopSales(Integer count){
        List<Product> products = adminDao.getYearlyTopSales(count);
        return getTopSalesByProductList(products);
    }

    public OrdersListDTOWithTotal getOrderList(Integer page){
        List<Orders> orders = adminDao.getOrderList(page);
        return getDtoByOrders(orders);
    }

    public OrdersListDTOWithTotal getTopOrdersByDate(Integer page){
        List<Orders> orders = adminDao.getTopOrdersByDate(page);
        return getDtoByOrders(orders);
    }

    OrdersListDTOWithTotal getDtoByOrders(List<Orders> orders){
        OrdersListDTOWithTotal dto = new OrdersListDTOWithTotal();
        dto.setSum(0.0);
        dto.setOrders(new ArrayList<>());

        for(Orders o : orders){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setDate(o.getDate());

            orderDTO.setItems(new ArrayList<>());
            for (Item i: o.getItems()) {
                ItemDto itemDto = new ItemDto();
                ProductDto productDto = new ProductDto();
                productDto.setCount(i.getProduct().getCount());
                productDto.setName(i.getProduct().getName());
                productDto.setId(i.getProduct().getIdProduct());
                productDto.setCategories(new HashSet<>());
                productDto.setPrice(i.getProduct().getPrice());

                itemDto.setAmount(i.getAmount());
                itemDto.setId(i.getId());
                itemDto.setProduct(productDto);

                orderDTO.getItems().add(itemDto);
            }

            orderDTO.setSum(o.getSum());
            orderDTO.setId(o.getId());
            dto.setSum(dto.getSum() + o.getSum());
            dto.getOrders().add(orderDTO);
        }

        if(dto.getSum()==0)dto.setSum(null);
        return dto;
    }

    TopSalesDto getTopSalesByProductList(List<Product> products){
        TopSalesDto top = new TopSalesDto();
        top.setProducts(new ArrayList<>());

        for(Product p : products){
            ProductDto product = new ProductDto();
            product.setCount(p.getCount());
            product.setName(p.getName());
            product.setId(p.getIdProduct());
            //pr.setCategories(p.getCategories());
            product.setPrice(p.getPrice());

            top.getProducts().add(product);
        }
        return top;
    }

}
