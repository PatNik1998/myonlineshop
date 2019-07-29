package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.entities.Orders;
import net.thumbtack.onlineshop.entities.Product;

import java.util.List;

public interface AdministratorDao extends CommonDao<Administrator> {
    List<Orders> getOrderList(Integer page);
    List<Orders> getOrdersOfClient(Integer clientId, Integer page);
    List<Orders> getOrdersOfProduct(Integer productId, Integer page);
    List<Orders> getTopOrdersByDate(Integer page);
    List<Product> getTopSalesByCategory(Integer categoryId, Integer count);
    List<Product> getMonthlyTopSales(Integer count);
    List<Product> getYearlyTopSales(Integer count);
    List<Category> getTopClientCategories(Integer clientId, Integer count);
}
