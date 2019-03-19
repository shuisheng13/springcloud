package com.pactera.business.service;

import com.pactera.vo.LaunPage;
import com.pactera.vo.LaunVersionsVo;

import java.util.List;

/**
 *
 * @author xukj
 */
public interface LaunVersionService {


    /**
     * @Author xukj
     * @Description 获取所有版本列表
     * @Date 14:29 2018/12/19
     * @Param pageSize
     * @Param pageNum
     * @return java.util.List<com.pactera.domain.LaunVersion>
     **/
    LaunPage<LaunVersionsVo> query(int pageNum, int pageSize);

    /**
     * @Author xukj
     * @Description 添加描述信息
     * @Date 15:30 2018/12/19
     * @Param
     * @return
     **/
    int describe(Long id, String description);

    /**
     * 获取所有版本列表
     * @return
     */
    List<LaunVersionsVo> list(String layoutId);
}
