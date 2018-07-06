package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.domain.LaunApplicationPoster;

import tk.mybatis.mapper.common.Mapper;

/**
 * @description:应用海报相关接口
 * @author:LL
 * @since:2018年5月10日 上午10:34:24
 */
public interface LaunApplicationPosterMapper extends Mapper<LaunApplicationPoster> {

	/**
	 * 根据主键id查询海报
	 * 
	 * @author LL
	 * @date 2018年5月10日 上午10:37:03
	 * @param 主键ids
	 * @return List<LaunApplicationPoster>
	 */
	List<LaunApplicationPoster> selectByIds(@Param("ids") List<Long> ids);

}