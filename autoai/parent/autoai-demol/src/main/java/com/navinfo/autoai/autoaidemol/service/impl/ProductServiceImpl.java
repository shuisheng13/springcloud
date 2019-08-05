package com.yhh.springcloud.scloudproduct.service.impl;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.scloudproduct.mapper.ProductMapper;
import com.yhh.springcloud.scloudproduct.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public Product get(long id) {
        return productMapper.findById(id);
    }

    @Override
    public boolean add(Product product) {
        return productMapper.create(product);
    }

    @Override
    public List<Product> list() {
        return productMapper.findAll();
    }
}