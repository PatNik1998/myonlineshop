package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(int id);
    CategoryDto editCategory(int id,CategoryDto categoryDto);
    String deleteCategory( int id);
    List<CategoryDto> getCategories();
}
