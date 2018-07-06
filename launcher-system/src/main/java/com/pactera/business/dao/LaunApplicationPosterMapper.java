package com.pactera.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pactera.domain.LaunApplicationPoster;
import com.pactera.vo.LaunApplicationPosterVo;

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

	/**
	 * 根据应用id查询海报(为null时查询全部)
	 * @author LL
	 * @date 2018年5月24日 下午2:12:06
	 * @param  applicationId应用id
	 * @return List<LaunApplicationPoster>
	 */
	List<LaunApplicationPosterVo> selectList(@Param("id")Long applicationId);
}