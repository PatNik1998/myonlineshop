package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.ProductDTOWithIdCategories;
import net.thumbtack.onlineshop.dto.ProductDto;

public interface ProductService {
    ProductDto getProduct(int productId);
    //List<ProductDTOWithNameCategories> getCategories(String sessionId, int[] categoriesId, String order);
    ProductDTOWithIdCategories addProduct(ProductDTOWithIdCategories productDTO);
   // ProductDto editProduct(String sessionId, ProductDTOWithIdCategories productDTO);
    String deleteProduct(int productId);
}
