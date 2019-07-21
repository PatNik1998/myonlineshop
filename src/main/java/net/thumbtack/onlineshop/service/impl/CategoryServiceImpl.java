package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.CategoryDao;
import net.thumbtack.onlineshop.dto.request.CategoryDto;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private Validator validator;

    @Autowired
    public CategoryServiceImpl(CategoryDaoImpl categoryDao, Validator validator) {
        this.categoryDao = categoryDao;
        this.validator = validator;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (validator.validateCategory(categoryDto)) {
            Category category = new Category();
            category.setName(categoryDto.getName());
            Category parentCategory = categoryDao.getById(categoryDto.getParentId());
            category.setParentCategory(parentCategory);
            categoryDao.add(category);

            categoryDto.setId(category.getIdCategory());
            categoryDto.setParentName(category.getParentCategory().getName());
            return categoryDto;
        }
        return categoryDto;
    }


    public CategoryDto getCategoryById(int id) {
        Category category = categoryDao.getById(id);
        CategoryDto categoryDto = new CategoryDto();
        if (category == null) {
            categoryDto.getErrors().add(new UserServiceError(UserErrorCode.CATEGORY_NOT_EXIST, "Нет такой категории", "error"));

        }
        categoryDto.setId(category.getIdCategory());
        categoryDto.setName(category.getName());

        if (category.getParentCategory() != null) {
            categoryDto.setParentId(category.getParentCategory().getIdCategory());
            categoryDto.setParentName(category.getParentCategory().getName());
        }
        return categoryDto;
    }

    public CategoryDto editCategory(int id, CategoryDto categoryDto) {
        Category category = categoryDao.getById(id);
        if (category == null) {
            validator.setNullForFieldsCategory(categoryDto);
            categoryDto.getErrors().add(new UserServiceError(UserErrorCode.CATEGORY_NOT_EXIST, "Нет такой категории", "error"));
        }
        String newName = categoryDto.getName();

        category.setName(newName);
        Category parentCategory = categoryDao.getById(categoryDto.getParentId());
        category.setParentCategory(parentCategory);

        categoryDao.update(category);

        categoryDto.setId(category.getIdCategory());
        categoryDto.setName(category.getName());
        categoryDto.setParentId(category.getParentCategory().getIdCategory());
        categoryDto.setParentName(category.getParentCategory().getName());
        return categoryDto;
    }


    public String deleteCategory(int id) {
        categoryDao.delete(id);
        return "{}";
    }

    public List<CategoryDto> getCategories() {
        List<CategoryDto> result = new ArrayList<>();
        for (Category category : categoryDao.getAll()) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getIdCategory());
            Category parentCategory = category.getParentCategory();

            if (parentCategory != null) {
                categoryDto.setParentName(parentCategory.getName());
                categoryDto.setParentId(parentCategory.getIdCategory());
            }

            result.add(categoryDto);
        }
        return result;
    }

}
