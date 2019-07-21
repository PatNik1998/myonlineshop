package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.dao.implementations.CategoryDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.CategoryDao;
import net.thumbtack.onlineshop.entities.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryDaoTest {
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Before
    public void clear() {
        categoryDao.clear();
    }

    @Test
    public void testInsertCategoryToDb() {
        Category category = new Category("category1", null);
        categoryDao.add(category);
        Assert.assertSame(1, categoryDao.getAll().size());

        category = new Category("category2", null);
        categoryDao.add(category);
        Assert.assertSame(2, categoryDao.getAll().size());

        Category categoryForDel = categoryDao.getById(category.getIdCategory());
        categoryDao.delete(categoryForDel.getIdCategory());
        Assert.assertSame(1, categoryDao.getAll().size());
    }

    @Test
    public void testDeleteCategoryFromDb() {
        Category category = new Category("category1", null);
        categoryDao.add(category);
        categoryDao.delete(category.getIdCategory());
        Category categoryFromDb = categoryDao.getById(category.getIdCategory());
        Assert.assertEquals(null, categoryFromDb);
    }

    @Test
    public void testEditCategory() {
        String newNameCategory = "new category";

        Category category = new Category("category1", null);
        Category parentCategory = new Category("parent", null);
        categoryDao.add(category);
        categoryDao.add(parentCategory);

        Category categoryFromDb = categoryDao.getById(category.getIdCategory());
        categoryFromDb.setName(newNameCategory);
        categoryFromDb.setParentCategory(parentCategory);
        categoryDao.update(categoryFromDb);

        Assert.assertNotEquals(category, categoryFromDb);
        Assert.assertEquals(newNameCategory, categoryFromDb.getName());
        Assert.assertEquals(parentCategory, categoryFromDb.getParentCategory());
    }

    @Test
    public void testAddParentCategoryToCategory() {
        Category parentCategory = new Category("parentCategory", null);
        Category childCategory = new Category("childCategory", parentCategory);
        categoryDao.add(parentCategory);
        categoryDao.add(childCategory);

        Category childCategoryFromDb = categoryDao.getById(childCategory.getIdCategory());
        Assert.assertEquals(parentCategory, childCategoryFromDb.getParentCategory());

    }


}
