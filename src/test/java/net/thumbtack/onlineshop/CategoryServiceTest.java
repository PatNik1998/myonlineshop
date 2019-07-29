


import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dto.CategoryDto;
import net.thumbtack.onlineshop.entities.Category;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private CategoryDaoImpl mockCategoryDao;
    private CategoryService categoryService;

    @Before
    public void init() {
        mockCategoryDao = mock(CategoryDaoImpl.class);
        categoryService = new CategoryServiceImpl(mockCategoryDao);
    }

    @Test
    public void testAddCategory() {
        CategoryDto dtoRequest = new CategoryDto();
            categoryService.addCategory(dtoRequest);
            fail();


    }
    @Test
    public void testAddCategory2() {
        CategoryDto dtoRequest = new CategoryDto();

        String name = "validName";
        dtoRequest.setName(name);
        when(mockCategoryDao.checkNameCategory(any())).thenReturn(false);
        CategoryDto expected = new CategoryDto(){{setName(name);}};
        CategoryDto actual = categoryService.addCategory(dtoRequest);

        verify(mockCategoryDao, times(1)).checkNameCategory(any());
        verify(mockCategoryDao, times(1)).add(any());
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testAddCategory3()  {
        CategoryDto dtoRequest = new CategoryDto();
        String name = "validName";
        dtoRequest.setName(name);
        when(mockCategoryDao.checkNameCategory(any())).thenReturn(true);
            categoryService.addCategory(dtoRequest);
            fail();


    }


    @Test
    public void testGetCategoryById2(){
        Category category = new Category();
        category.setName("category1");
        category.setIdCategory(12);
        when(mockCategoryDao.getById(anyInt())).thenReturn(category);
        CategoryDto expected = new CategoryDto(){
            {
                setName(category.getName());
                setId(category.getIdCategory());
            }
        };
        CategoryDto actual = categoryService.getCategoryById(-100);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testGetCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category("category1",null);
        categories.add(category);
        categories.add(new Category("category2",category));
        categories.add(new Category("category3",null));

        List<CategoryDto> expected = new ArrayList<>();
        for (Category tmp : categories) {
            CategoryDto result = new CategoryDto();
            result.setName(tmp.getName());
            result.setId(tmp.getIdCategory());
            Category parentCategory = tmp.getParentCategory();
            if (parentCategory != null) {
                result.setParentName(parentCategory.getName());
                result.setParentId(parentCategory.getIdCategory());
            }
            expected.add(result);
        }

        when(mockCategoryDao.getAll()).thenReturn(categories);

        List<CategoryDto> actual = categoryService.getCategories();
        Assert.assertEquals(expected, actual);
    }
}
