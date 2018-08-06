package com.pactera.utlis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSON;

/**
 * ajax返回前台数据
 * @author lzp
 *
 */
public class BaseOutJson {

	public static void writeJson(Object object, HttpServletResponse response){
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * <pre>outString(打印字符串) 使用response.setContentType("application/json");
	 * 创建人：SunCong
	 * 创建时间：2016年3月14日 上午10:07:28    
	 * 修改人：SunCong
	 * 修改时间：2016年3月14日 上午10:07:28    
	 * 修改备注： 
	 * @param json</pre>
	 */
	protected static void outString(String json, HttpServletResponse response) {
		PrintWriter writer = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
				writer = null;
			}
		}
	}
	
	
	protected static void ajaxOut(String jsonStr, HttpServletResponse response){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
