package net.thumbtack.onlineshop.service;


import net.thumbtack.onlineshop.dto.CategoriesListDto;
import net.thumbtack.onlineshop.dto.OrdersListDTOWithTotal;
import net.thumbtack.onlineshop.dto.TopSalesDto;
import net.thumbtack.onlineshop.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdministratorService {
    UserDTO registerAdmin(UserDTO administrator) ;
    UserDTO editAdminProfile(String sessionId, UserDTO adminDTO);
    OrdersListDTOWithTotal getOrdersListByClient(Integer clientId, Integer page);
    OrdersListDTOWithTotal getOrdersListByProduct(Integer productId, Integer page);
    TopSalesDto getTopCategorySales(Integer categoryId, Integer count);
    TopSalesDto getMonthlyTopSales(Integer count);
    TopSalesDto getYearlyTopSales(Integer count);
    CategoriesListDto getTopClientCategories(Integer clientId, Integer count);
    OrdersListDTOWithTotal getOrderList(Integer page);
    OrdersListDTOWithTotal getTopOrdersByDate(Integer page);


    //  Administrator getBySession(String sessionId);
}
