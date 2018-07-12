package com.pactera.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @date:2018年6月21日
 * @author lzp
 * @description 校验widget压缩包图片的合法性
 *
 */
@SuppressWarnings("all")
public class CheckWidgetImg {
	
	/**
	 * 校验json中的图片是否在img中存在
	 * @return
	 */
	public static boolean isIncludeimg(List<String> jsonList,List<String> imgList) {
		boolean check = false;
		if(jsonList != null && imgList != null) {
			for(String img : jsonList) {
				if(imgList.contains(img)) {
					check = true;
				}else {
					check = false;
					break;
				}
				
				
			}
		}

		return check;
	}
	
	/**
	 * 校验值是否是图片
	 * @return
	 */
	public static boolean isImg(String value) {
		boolean isImg = false;
		if(value.equals("")) {
			isImg = false;
		}
		if(value.toLowerCase().endsWith(".png") || value.toLowerCase().endsWith(".jpg")) {
			isImg = true;
		}
		
		return isImg;
	}

	/**
	 * 递归单个值
	 * @param m
	 * @return
	 */
	public static Object viewJsonTree(Object m,List ImgList){
		if(m == null){ System.out.println("over...");return false;}
		try {
			Map mp = null;
			List ls = null;
			if(m instanceof Map || m instanceof LinkedHashMap){
				mp = (LinkedHashMap)m;
				for(Iterator ite = mp.entrySet().iterator(); ite.hasNext();){  
					Map.Entry e = (Map.Entry) ite.next();  
					if(e.getValue() instanceof String){
						System.out.println("[String]"+e.getKey()+" | " + e.getValue());
						if(isImg(e.getValue().toString())){
							ImgList.add(e.getValue().toString());
						}
					}else if(e.getValue() instanceof LinkedHashMap){
						System.out.println("{Map}"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((LinkedHashMap)e.getValue(),ImgList);
					}else if(e.getValue() instanceof ArrayList){
						System.out.println("[Array]"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((ArrayList)e.getValue(),ImgList);
					}
				}  	
			}
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						viewJsonTree((LinkedHashMap)ls.get(i),ImgList);
					}else if(ls.get(i) instanceof ArrayList){
						viewJsonTree((ArrayList)ls.get(i),ImgList);
					}	
				}
			}	
			System.out.println();
		} catch (Exception e) {
			System.out.println("###[Error] viewJsonTree() "+e.getMessage());
		}
		return null;
	}	
	
}
