package com.navinfo.autoai.autoaidemol.service;

import com.yhh.springcloud.api.vo.Product;

import java.util.List;

public interface IProductService {
    Product get(long id);
    boolean add(Product product);
    List<Product> list();
}