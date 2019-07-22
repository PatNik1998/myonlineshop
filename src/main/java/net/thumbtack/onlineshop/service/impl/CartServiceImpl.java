package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.Item;
import net.thumbtack.onlineshop.entities.Product;
import net.thumbtack.onlineshop.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartServiceImpl implements CartService {

  private ProductDao productDao;
  private ClientDao clientDao;
  @Autowired
  public CartServiceImpl(ProductDaoImpl productDao, ClientDao clientDao){
      this.productDao = productDao;
      this.clientDao = clientDao;
  }
    public List<ProductDto>  addProductToCart(Client client, ProductDto productDto){
        Product product = getProduct(productDto);
        client.addItem(product,productDto.getCount());
        clientDao.update(client);
        return getCart(client);
    }

public List<ProductDto> editProductFromCart(Client client, ProductDto productDto){
      getProduct(productDto);

      Item item = client.getItemById(productDto.getId());
      client.editItem(item,productDto.getCount());
      clientDao.update(client);
      return getCart(client);
}

public String deleteProductFromCart(Client client, int productId){
      Product product = productDao.getById(productId);
      Item item = client.getItemById(productId);

      client.deleteItem(item);
      clientDao.update(client);
      return "{}";

}

public ProductDto buyProduct(Client client, ProductDto productDto){
      Product product = getProduct(productDto);
      int count = productDto.getCount();
      int orderPrice = count + productDto.getPrice();
      client.setDeposit(client.getDeposit() - orderPrice);
      clientDao.update(client);
      return productDto;
}

public Product getProduct(ProductDto productDto){
        int id = productDto.getId();
        Product product = productDao.getById(id);

        Integer count = productDto.getCount();
        if(count == null || count == 0){
            productDto.setCount(1);
            count = 1;
        }

        return product;
}

public List<ProductDto> getCart(Client client){
      List<ProductDto> result = new ArrayList<>();
      for(Item item : client.getItems()){
          ProductDto dto = new ProductDto();
          dto.setName(item.getProduct().getName());
          dto.setCount(item.getAmount());
          dto.setPrice(item.getProduct().getPrice());
          dto.setId(item.getProduct().getIdProduct());
          result.add(dto);

      }
      return result;
}
}



