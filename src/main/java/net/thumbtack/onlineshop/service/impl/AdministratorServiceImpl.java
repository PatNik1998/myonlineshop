package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.UserDao;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dto.*;
import net.thumbtack.onlineshop.entities.*;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.AdministratorService;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
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
        List<Orders> os = adminDao.getOrdersOfClient(clientId, page);
        OrdersListDTOWithTotal dto = new OrdersListDTOWithTotal();
        dto.setOrders(new ArrayList<>());

        for(Orders o : os){
            OrderDTO o1 = new OrderDTO();
            o1.setDate(o.getDate());

            o1.setItems(new ArrayList<>());
            for (Item i: o.getItems()) {
                ItemDto it = new ItemDto();
                ProductDto pr = new ProductDto();
                pr.setCount(i.getProduct().getCount());
                pr.setName(i.getProduct().getName());
                pr.setId(i.getProduct().getIdProduct());
                pr.setCategories(i.getProduct().getCategories());
                pr.setPrice(i.getProduct().getPrice());

                it.setAmount(i.getAmount());
                it.setId(i.getId());
                it.setProduct(pr);

                o1.getItems().add(it);
            }

            o1.setSum(o.getSum());
            dto.setSum(dto.getSum() + o.getSum());
            dto.getOrders().add(o1);
        }

        return dto;
    }

    public OrdersListDTOWithTotal getOrdersListByProduct(Integer productId, Integer page){
        List<Orders> os = adminDao.getOrdersOfProduct(productId, page);
        OrdersListDTOWithTotal dto = new OrdersListDTOWithTotal();
        dto.setOrders(new ArrayList<>());

        for(Orders o : os){
            OrderDTO o1 = new OrderDTO();
            o1.setDate(o.getDate());

            o1.setItems(new ArrayList<>());
            for (Item i: o.getItems()) {
                ItemDto it = new ItemDto();
                ProductDto pr = new ProductDto();
                pr.setCount(i.getProduct().getCount());
                pr.setName(i.getProduct().getName());
                pr.setId(i.getProduct().getIdProduct());
                pr.setCategories(i.getProduct().getCategories());
                pr.setPrice(i.getProduct().getPrice());

                it.setAmount(i.getAmount());
                it.setId(i.getId());
                it.setProduct(pr);

                o1.getItems().add(it);
            }

            o1.setSum(o.getSum());
            dto.setSum(dto.getSum() + o.getSum());
            dto.getOrders().add(o1);
        }

        return dto;
    }

    public TopSalesDto getTopCategorySales(Integer categoryId, Integer count){
        List<Product> pp = adminDao.getTopSalesByCategory(categoryId, count);
        TopSalesDto top = new TopSalesDto();
        top.setProducts(new ArrayList<>());

        for(Product p : pp){
            ProductDto pr = new ProductDto();
            pr.setCount(p.getCount());
            pr.setName(p.getName());
            pr.setId(p.getIdProduct());
            pr.setCategories(p.getCategories());
            pr.setPrice(p.getPrice());

            top.getProducts().add(pr);
        }
        return top;
    }

    public CategoriesListDto getTopClientCategories(Integer clientId, Integer count){
        List<Category> pp = adminDao.getTopClientCategories(clientId, count);
        CategoriesListDto top = new CategoriesListDto();
        top.setCategories(new ArrayList<>());

        for(Category p : pp){
            CategoryDto pr = new CategoryDto();
            pr.setName(p.getName());
            pr.setId(p.getIdCategory());
            if(p.getParentCategory() != null)
                pr.setParentId(p.getParentCategory().getIdCategory());

            top.getCategories().add(pr);
        }
        return top;
    }


    public TopSalesDto getMonthlyTopSales(Integer count){
        List<Product> pp = adminDao.getMonthlyTopSales(count);
        TopSalesDto top = new TopSalesDto();
        top.setProducts(new ArrayList<>());

        for(Product p : pp){
            ProductDto pr = new ProductDto();
            pr.setCount(p.getCount());
            pr.setName(p.getName());
            pr.setId(p.getIdProduct());
            pr.setCategories(p.getCategories());
            pr.setPrice(p.getPrice());

            top.getProducts().add(pr);
        }
        return top;
    }

    public TopSalesDto getYearlyTopSales(Integer count){
        List<Product> pp = adminDao.getYearlyTopSales(count);
        TopSalesDto top = new TopSalesDto();
        top.setProducts(new ArrayList<>());

        for(Product p : pp){
            ProductDto pr = new ProductDto();
            pr.setCount(p.getCount());
            pr.setName(p.getName());
            pr.setId(p.getIdProduct());
            pr.setCategories(p.getCategories());
            pr.setPrice(p.getPrice());

            top.getProducts().add(pr);
        }
        return top;
    }

    public OrdersListDTOWithTotal getOrderList(Integer page){
        List<Orders> os = adminDao.getOrderList(page);
        OrdersListDTOWithTotal dto = new OrdersListDTOWithTotal();
        dto.setOrders(new ArrayList<>());

        for(Orders o : os){
            OrderDTO o1 = new OrderDTO();
            o1.setDate(o.getDate());

            o1.setItems(new ArrayList<>());
            for (Item i: o.getItems()) {
                ItemDto it = new ItemDto();
                ProductDto pr = new ProductDto();
                pr.setCount(i.getProduct().getCount());
                pr.setName(i.getProduct().getName());
                pr.setId(i.getProduct().getIdProduct());
                pr.setCategories(i.getProduct().getCategories());
                pr.setPrice(i.getProduct().getPrice());

                it.setAmount(i.getAmount());
                it.setId(i.getId());
                it.setProduct(pr);

                o1.getItems().add(it);
            }

            o1.setSum(o.getSum());
            dto.setSum(dto.getSum() + o.getSum());
            dto.getOrders().add(o1);
        }
        return dto;
    }

    public OrdersListDTOWithTotal getTopOrdersByDate(Integer page){
        List<Orders> os = adminDao.getTopOrdersByDate(page);
        OrdersListDTOWithTotal dto = new OrdersListDTOWithTotal();
        dto.setOrders(new ArrayList<>());

        for(Orders o : os){
            OrderDTO o1 = new OrderDTO();
            o1.setDate(o.getDate());

            o1.setItems(new ArrayList<>());
            for (Item i: o.getItems()) {
                ItemDto it = new ItemDto();
                ProductDto pr = new ProductDto();
                pr.setCount(i.getProduct().getCount());
                pr.setName(i.getProduct().getName());
                pr.setId(i.getProduct().getIdProduct());
                pr.setCategories(i.getProduct().getCategories());
                pr.setPrice(i.getProduct().getPrice());

                it.setAmount(i.getAmount());
                it.setId(i.getId());
                it.setProduct(pr);

                o1.getItems().add(it);
            }

            o1.setSum(o.getSum());
            dto.setSum(dto.getSum() + o.getSum());
            dto.getOrders().add(o1);
        }
        return dto;
    }

}
