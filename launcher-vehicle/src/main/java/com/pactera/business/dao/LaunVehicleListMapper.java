package com.pactera.business.dao;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.dto.ThemClassDTO;
import com.pactera.dto.ThemListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhaodong
 * @Date 10:55 2019/1/7
 **/
public interface LaunVehicleListMapper {

    /**
     * 查询用户所属租户的主题分类(上架的主题分类)
     * @Author zhaodong
     * @Date 10:56 2019/1/7
     * @Param
     * @return
     **/
    List<ThemClassDTO> themeclasslist2(@Param("tenanId") String tenanId);

    /**
     * 查询分类下的主题列表(上架的主题)
     * @Author zhaodong
     * @Date 10:56 2019/1/7
     * @Param
     * @return
     **/
    List<ThemListDTO> themTopAndByClassId(LaunThemeAdministration launThemeVo);
}
