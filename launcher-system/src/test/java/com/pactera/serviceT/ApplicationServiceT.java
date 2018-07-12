package com.pactera.serviceT;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.base.Tester;
import com.pactera.business.service.LaunApplicationService;
import com.pactera.domain.LaunApplication;
import com.pactera.domain.LaunApplicationPoster;
import com.pactera.utlis.IdUtlis;
import com.pactera.vo.LaunApplicationVo;

/**
 * @description:应用相关测试
 * @author:Scott
 * @since:2018年4月26日 下午4:10:37
 */
public class ApplicationServiceT extends Tester{

	@Autowired
	private LaunApplicationService launApplicationService;
	
	@Test
	public void save() {
		LaunApplication app =new LaunApplication();
		app.setPackageId(IdUtlis.Id().toString());
		app.setName("测试应用");
		launApplicationService.add(app, "1,2");
		
	}
	
	@Test
	public void savePoster() {
		List<LaunApplicationPoster> posterList =new ArrayList<LaunApplicationPoster>();
		LaunApplicationPoster poster = new LaunApplicationPoster();
		//poster.setPosterPath();
		poster.setHeight(2);
		poster.setWidth(2);
		poster.setType(1);
		posterList.add(poster);
		launApplicationService.savePoster(new Date(), new Date(), 2L, posterList);
		
	}
	@Test
	public void update() {
		LaunApplication app =new LaunApplication();
		app.setName("修改测试应用");
		app.setId(180525256807769L);
		System.out.println(launApplicationService.update(app, "180520289780670"));
		
	}
	
	@Test
	public void testVo() {
		LaunApplication app =new LaunApplication();
		app.setName("修改测试应用");
		app.setId(180525256807769L);
		LaunApplicationVo appVo =new LaunApplicationVo();
		BeanUtils.copyProperties(app, appVo);
		System.out.println(appVo);
		
	}
}
