package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.entities.Client;

import java.util.List;

public interface CartService {
    ProductDto buyProduct(Client client, ProductDto productDto);
    String deleteProductFromCart(Client client, int productId);
    List<ProductDto> addProductToCart(Client client, ProductDto productDto);
    List<ProductDto> editProductFromCart(Client client, ProductDto productDto);
    List<ProductDto> getCart(Client client);
}
