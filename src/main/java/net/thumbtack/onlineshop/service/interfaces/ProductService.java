package net.thumbtack.onlineshop.service.interfaces;

import net.thumbtack.onlineshop.dto.request.ProductDTOWithIdCategories;
import net.thumbtack.onlineshop.dto.request.ProductDTOWithNameCategories;
import net.thumbtack.onlineshop.dto.request.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProduct(int productId);
    //List<ProductDTOWithNameCategories> getProducts(String sessionId, int[] categoriesId, String order);
    ProductDto addProduct(ProductDto productDTO);
   // ProductDto editProduct(String sessionId, ProductDTOWithIdCategories productDTO);
    String deleteProduct(int productId);
}
