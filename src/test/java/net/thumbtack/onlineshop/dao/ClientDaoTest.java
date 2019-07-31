package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.entities.Item;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.Product;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;


public class ClientDaoTest {
    ClientDao clientDao = new ClientDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    @Before
    public void clear() {
        clientDao.clear();
       // productDao.clear();
    }

    @Test
    public void testInsertDbToDb() {
        Client client = new Client("Никита", "Патраков", null, "patnik@thumbtack.net", "Saratov", "100000", "patnik", "q1w2e3r4t5y6", 99999999);
        clientDao.add(client);
        Client clientFromDb = clientDao.getById(client.getId());
        MatcherAssert.assertThat(client, is(clientFromDb));

        client = new Client("Ваня", "Иванов", null, "vano@thumbtack.net", "Saratov", "1000001", "vano", "q1w2e3r4t5y6", 999999999);
        clientDao.add(client);
        clientFromDb = clientDao.getById(client.getId());
        MatcherAssert.assertThat(client, is(clientFromDb));
    }

    @Test
    public void testGetAllClients() {
        List<Client> expectedClients = new ArrayList<Client>();

        Client client = new Client("Никита", "Патраков", null, "patnik@thumbtack.net", "Saratov", "100000", "patnik", "q1w2e3r4t5y6", 99999999);
        clientDao.add(client);
        expectedClients.add(client);
        Assert.assertSame(1, clientDao.getAll().size());

        client = new Client("Ваня", "Иванов", null, "vano@thumbtack.net", "Saratov", "1000001", "vano", "q1w2e3r4t5y6", 999999999);

        clientDao.add(client);
        expectedClients.add(client);
        List<Client> clients = clientDao.getAll();
        Assert.assertSame(2, clients.size());
        Assert.assertTrue(clients.containsAll(expectedClients));
    }

    @Test
    public void testDeleteClientFromDb() {
       Client client = new Client("Ваня", "Иванов", null, "vano@thumbtack.net", "Saratov", "1000001", "vano", "q1w2e3r4t5y6", 999999999);
        clientDao.add(client);
        Client clientFromDb = clientDao.getById(client.getId());
        Assert.assertEquals(client, clientFromDb);
        clientDao.delete(clientFromDb.getId());
        clientFromDb = clientDao.getById(client.getId());
        Assert.assertEquals(null, clientFromDb);
    }

    @Test
    public void testEditClient() {
       Client client = new Client("Ваня", "Иванов", null, "vano@thumbtack.net", "Saratov", "1000001", "vano", "q1w2e3r4t5y6", 999999999);
        clientDao.add(client);
        Client clientFromDb = clientDao.getById(client.getId());
        String phone = "98656782";
        String patronymic = "Petrovich";
        clientFromDb.setPhone(phone);
        clientFromDb.setPatronymic(patronymic);
        clientDao.update(clientFromDb);
        clientFromDb = clientDao.getById(client.getId());

        Assert.assertNotEquals(client, clientFromDb);
        Assert.assertEquals(patronymic, clientFromDb.getPatronymic());
        Assert.assertEquals(phone, clientFromDb.getPhone());
    }


}
