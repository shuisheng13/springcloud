package com.yhh.springcloud.scloudproduct.mapper;
import com.yhh.springcloud.api.vo.Product;
import java.util.List;
public interface ProductMapper {
    boolean create(Product product);
    public Product findById(Long id);
    public List<Product> findAll();
}