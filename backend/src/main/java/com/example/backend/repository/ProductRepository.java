package com.example.backend.repository;

import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAll();

    Page<ProductEntity> findAll(Pageable pageable);
    ProductEntity findProductByName(String name);
    ProductEntity findProductById(int id);

    ProductEntity findProductBySlug(String slug);
    void deleteById(int id);

    @Query(value = "SELECT * FROM product WHERE category_entity_id = :cId and id != :pId limit 3", nativeQuery = true)
    List<ProductEntity> findSimilarProducts(@Param("cId") int cId, @Param("pId") int pId);

    @Query(value = "SELECT * FROM product WHERE category_entity_id in :list", nativeQuery = true)
    List<ProductEntity> filterCategory(@Param("list") List<Integer> list);

    @Query(value = "SELECT * FROM product WHERE price >= :low AND price <= :high", nativeQuery = true)
    List<ProductEntity> filterPrice(@Param("low") int low,@Param("high") int high);

    @Query(value = "SELECT * FROM product WHERE price >= :low AND price <= :high AND category_entity_id in :list", nativeQuery = true)
    List<ProductEntity> filterAll(@Param("low") int low,@Param("high") int high, @Param("list") List<Integer> list);

    List<ProductEntity> findProductByCategoryEntity(CategoryEntity categoryEntity);
    List<ProductEntity> findProductBySlugContaining(String text);
}
