package com.yhh.springcloud.scloudproduct.service;



import com.yhh.api.vo.Product;

import java.util.List;

public interface IProductService {
    Product get(long id);
    boolean add(Product product);
    List<Product> list();
}