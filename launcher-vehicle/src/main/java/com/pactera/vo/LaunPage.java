package com.pactera.vo;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import java.util.List;

/**
 * @Author xukj
 * @Description
 */
@Data
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
     * 是否为最后一页
     */
    private boolean isLastPage;
    /**
     * 数据集合
     */
    private List<T> list;


    public LaunPage(PageInfo<T> pageInfo) {
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.size = pageInfo.getSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.list = pageInfo.getList();
    }

    public LaunPage(PageInfo pageInfo, List<T> list) {
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.size = pageInfo.getSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.isLastPage = pageInfo.isIsLastPage();
        this.list = list;
    }
}
