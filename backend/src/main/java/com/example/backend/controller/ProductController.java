package com.example.backend.controller;

import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.entity.ProductEntity;
import com.example.backend.data.request.FilterRequest;
import com.example.backend.data.request.ProductRequest;
import com.example.backend.data.response.base.MyResponse;
import com.example.backend.service.CategoryService;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value="/api/v1/product/product-list/{pageId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductList(@PathVariable int pageId){
        Pageable pageable = PageRequest.of(pageId - 1, 10, Sort.by("slug").descending());
        Page<ProductEntity> productEntities = productService.getProductList(pageable);
        Map<String, Object> mapReturn = new HashMap<>();
        mapReturn.put("products", productEntities.toList());
        mapReturn.put("totalPage", productEntities.getTotalPages());
        mapReturn.put("pageSize", 10);
        mapReturn.put("pageOffset", pageId);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/api/v1/product/get-product", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts(){
        List<ProductEntity> productEntities = productService.getProducts();
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/api/v1/product/get-product/{slug}", method = RequestMethod.GET)
    public ResponseEntity<?> getSingleProduct(@PathVariable String slug){
        ProductEntity productEntity = productService.getSingleProduct(slug);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/api/v1/product/product-count", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProductsCount(){
        List<ProductEntity> productEntities = productService.getProducts();
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities.size())
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/api/v1/product/product-photo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts(@PathVariable int id){
        String photo = productService.getPhoto(id);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildPhoto(photo)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/product/create-product", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ProductEntity productEntity = productService.createProduct(productRequest);
        if(productRequest == null){
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Failed")
                    .buildData(null)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);

        }
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/product/update-product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestBody ProductRequest productRequest) {
        ProductEntity productEntity = productService.updateProduct(id,productRequest);
        if(productRequest == null){
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Failed")
                    .buildData(null)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);

        }
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/product/delete-product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(null)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/product/related-product/{pId}/{cId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSimilarProduct(@PathVariable int cId,@PathVariable int pId){
        List<ProductEntity> productEntities = productService.getSimilar(cId,pId);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/api/v1/product/product-filters", method = RequestMethod.POST)
    public ResponseEntity<?> getFilter(@RequestBody FilterRequest filterRequest){
        List<ProductEntity> productEntities = productService.filter(filterRequest);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/api/v1/product/product-category/{slug}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilter(@PathVariable String slug){
        CategoryEntity category = categoryService.getCategoryBySlug(slug);
        List<ProductEntity> productEntities = productService.getProductByCategory(slug);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildCategory(category)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/product/search/{text}", method = RequestMethod.GET)
    public ResponseEntity<?> searchProducts(@PathVariable String text){
        List<ProductEntity> productEntities = productService.search(text);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(productEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
}
