package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.entities.Administrator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AdministratorDaoTest {
    AdministratorDao administratorDao = new AdministratorDaoImpl();

    @Before
    public void clear() {
        administratorDao.clear();
    }

    @Test
    public void testGetAllAdministrators() {
        List<Administrator> expectedAdmin = new ArrayList<Administrator>();
        Assert.assertSame(0, administratorDao.getAll().size());
        Administrator administrator = new Administrator("ivan","ivanov",null,"ivanov","q1w2e3r4t5y6","position1");
        administratorDao.add(administrator);
        Assert.assertSame(1, administratorDao.getAll().size());
        expectedAdmin.add(administrator);

        administrator = new Administrator("ivan2","ivanov2",null,"ivanov2","q1w2e3r4t5y6", "position2");
        administratorDao.add(administrator);
        expectedAdmin.add(administrator);
        Assert.assertSame(2, administratorDao.getAll().size());

        Assert.assertTrue(administratorDao.getAll().containsAll(expectedAdmin));
    }

    @Test
    public void testInsertAdministratorToDb() {
        AdministratorDaoImpl administratorDao = new AdministratorDaoImpl();
        Assert.assertSame(0, administratorDao.getAll().size());
        Administrator administrator = new Administrator("ivan","ivanov",null,"ivanov","q1w2e3r4t5y6","position1");
        administratorDao.add(administrator);
//        Administrator adminFromDb = (Administrator) administratorDao.getByToken(administrator.getToken());
        Administrator adminFromDb = administratorDao.getById(administrator.getId());
        Assert.assertEquals(adminFromDb, administrator);

        administrator = new Administrator("ivan2","ivanov2",null,"ivanov2","q1w2e3r4t5y6", "position2");
        administratorDao.add(administrator);
        adminFromDb = administratorDao.getById(administrator.getId());
        Assert.assertEquals(adminFromDb, administrator);
    }

    @Test
    public void testDeleteAdminFromDb() {
        Administrator administrator = new Administrator("ivan","ivanov",null,"ivanov","q1w2e3r4t5y6","position1");
        administratorDao.add(administrator);
        administratorDao.delete(administrator.getId());
        Administrator adminFromDb = administratorDao.getById(administrator.getId());
        Assert.assertEquals(null, adminFromDb);

        administrator = new Administrator("ivan2","ivanov2",null,"ivanov2","q1w2e3r4t5y6", "position2");
        administratorDao.add(administrator);
        administratorDao.delete(administrator.getId());
        adminFromDb = administratorDao.getById(administrator.getId());
        Assert.assertEquals(null, adminFromDb);
    }

    @Test
    public void testGetAdministratorById() {
        Administrator administrator = new Administrator("ivan","ivanov",null,"ivanov","q1w2e3r4t5y6","position1");
        administratorDao.add(administrator);
        Administrator adminFromDb = administratorDao.getById(administrator.getId());
        Assert.assertEquals(administrator, adminFromDb);
    }

    @Test
    public void testEditAdmin() {
        String firstName = "Vasya";
        String lastName = "Pupkin";
        String position = "super-admin";

        Administrator administrator = new Administrator("ivan","ivanov",null,"ivanov","q1w2e3r4t5y6","position1");
        administratorDao.add(administrator);

        Administrator adminFromDb = administratorDao.getById(administrator.getId());
        adminFromDb.setFirstName(firstName);
        adminFromDb.setLastName(lastName);
        adminFromDb.setPosition(position);
        administratorDao.update(administrator);

        Assert.assertNotEquals(administrator, adminFromDb);
        Assert.assertEquals(firstName, adminFromDb.getFirstName());
        Assert.assertEquals(lastName, adminFromDb.getLastName());
        Assert.assertEquals(position, adminFromDb.getPosition());

    }


}
