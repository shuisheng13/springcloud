package com.pactera.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName LaunPage
 * @Description
 * @Author xukj
 * @Date 2019/1/14 15:48
 * @Version
 */
@Data
@Accessors(chain = true)
public class LaunPage<T> {

    /**
     * 当前页数
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 当前页数量
     */
    private int size;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 数据集合
     */
    private List<T> list;

}
