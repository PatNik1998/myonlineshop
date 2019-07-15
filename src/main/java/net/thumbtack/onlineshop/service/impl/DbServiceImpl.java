package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.CategoryDao;
import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbServiceImpl implements DbService {
    ClientDao clientDao;
    ProductDao productDao;
    AdministratorDao adminDao;
    CategoryDao categoryDao;

    @Autowired
    public DbServiceImpl(ClientDaoImpl clientDao, ProductDaoImpl productDao, AdministratorDaoImpl adminDao, CategoryDaoImpl categoryDao) {
        this.clientDao = clientDao;
        this.productDao = productDao;
        this.adminDao = adminDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void clear() {
        clientDao.clear();
        productDao.clear();
        adminDao.clear();
        categoryDao.clear();
    }
}
