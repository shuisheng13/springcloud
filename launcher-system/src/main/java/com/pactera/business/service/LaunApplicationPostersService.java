package com.pactera.business.service;

import java.util.List;

import com.pactera.domain.LaunApplicationPoster;
import com.pactera.vo.LaunApplicationPosterVo;

/**
 * @description:应用海报详情
 * @author:LL
 * @since:2018年5月10日 上午10:53:41
 */
public interface LaunApplicationPostersService {

	/**
	 * 根据ids查询海报列表
	 * 
	 * @author LL
	 * @date 2018年5月10日 上午10:58:32
	 * @param ids(以,分隔)
	 * @return List<LaunApplicationPoster>
	 */
	List<LaunApplicationPoster> selectByIds(String ids);

	/**
	 * 根据应用id查询海报(null为全部海报)
	 * 
	 * @author LL
	 * @date 2018年5月24日 下午2:18:44
	 * @param applicationId应用id
	 * @return List<LaunApplicationPoster>
	 */
	List<LaunApplicationPosterVo> select(Long applicationId);
}
