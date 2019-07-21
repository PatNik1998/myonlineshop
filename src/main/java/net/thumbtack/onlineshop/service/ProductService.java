package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.ProductDto;

public interface ProductService {
    ProductDto getProduct(int productId);
    //List<ProductDTOWithNameCategories> getProducts(String sessionId, int[] categoriesId, String order);
    ProductDto addProduct(ProductDto productDTO);
   // ProductDto editProduct(String sessionId, ProductDTOWithIdCategories productDTO);
    String deleteProduct(int productId);
}
