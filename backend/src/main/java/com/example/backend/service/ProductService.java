package com.example.backend.service;

import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.entity.ProductEntity;
import com.example.backend.data.request.CategoryRequest;
import com.example.backend.data.request.FilterRequest;
import com.example.backend.data.request.ProductRequest;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.ProductRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<ProductEntity> getProducts(){
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities;
    }

    public Page<ProductEntity> getProductList(Pageable pageable){
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        return productEntities;
    }
    public String getPhoto(int id){
        return productRepository.findProductById(id).getPhoto();
    }

    public ProductEntity getSingleProduct(String slug){
        return productRepository.findProductBySlug(slug);
    }

    public ProductEntity createProduct(ProductRequest productRequest) {
        CategoryEntity category = categoryRepository.findCategoryById(productRequest.getCategory());
        ProductEntity existed = productRepository.findProductByName(productRequest.getName());
        if(existed != null) return null;
        final Slugify slg = Slugify.builder().build();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productRequest.getName());
        productEntity.setSlug(slg.slugify(productRequest.getName()));
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setQuantity(productRequest.getQuantity());
        productEntity.setPhoto(productRequest.getPhoto());
        productEntity.setCategoryEntity(category);
        productRepository.saveAndFlush(productEntity);
        return productEntity;
    }

    public ProductEntity updateProduct(int id,ProductRequest productRequest){
        CategoryEntity category = categoryRepository.findCategoryById(productRequest.getCategory());
//        ProductEntity existed= productRepository.findProductBySlug(productRequest.getName());
//        if(existed != null) return null;
        ProductEntity productEntity = productRepository.findProductById(id);
        productEntity.setName(productRequest.getName());
        final Slugify slg = Slugify.builder().build();
        productEntity.setSlug(slg.slugify(productRequest.getName()));
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setQuantity(productRequest.getQuantity());
        productEntity.setPhoto(productRequest.getPhoto());
        productEntity.setCategoryEntity(category);
        productRepository.saveAndFlush(productEntity);
        return productEntity;
    }
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public List<ProductEntity> getSimilar(int cId, int pId){
        return productRepository.findSimilarProducts(cId,pId);
    }
    public List<ProductEntity> filter(FilterRequest filterRequest){
        if(filterRequest.getChecked().size() > 0 && filterRequest.getRadio().size() > 0) {
            return productRepository.filterAll(filterRequest.getRadio().get(0), filterRequest.getRadio().get(1), filterRequest.getChecked());
        }
        if(filterRequest.getChecked().size() > 0 && filterRequest.getRadio().size() == 0) {
            return  productRepository.filterCategory(filterRequest.getChecked());
        }
        if(filterRequest.getChecked().size() == 0 && filterRequest.getRadio().size() > 0) {
            return productRepository.filterPrice(filterRequest.getRadio().get(0), filterRequest.getRadio().get(1));
        }
        return null;
    }
    public List<ProductEntity> getProductByCategory(String slug){
        CategoryEntity category = categoryRepository.findCategoryBySlug(slug);
        List<ProductEntity> productEntities = productRepository.findProductByCategoryEntity(category);
        return productEntities;
    }
    public List<ProductEntity> search(String text){
        return  productRepository.findProductBySlugContaining(text);
    }
}
