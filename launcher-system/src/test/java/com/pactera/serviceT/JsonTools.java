package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
@SuppressWarnings("all")
public class JsonTools {
	private static String jsonStr = "{\"api\":\"2.1\",\"message\":[\"��Ʒ\",\"tokken\"],\"request\":{\"ptype\":\"JK\",\"tokken\":\"A#daDSFkiwi239sdls#dsd\"},\"response\":{\"status\":{\"statusCode\":\"500\",\"statusMessage\":[\"��Ʒ���ʹ���\",\"tokkenʧЧ\"]},\"page\":{\"pageSize\":\"100\",\"pageIndex\":\"1\"},\"data\":{\"ptitle\":\"all product lists\",\"sDate\":\"2014-12-01\",\"eDate\":\"2016-12-01\",\"productList\":[{\"pid\":\"RA001\",\"pname\":\"��Ʒ1\"},{\"pid\":\"RA002\",\"pname\":\"��Ʒ2\"}]}},\"args\":[{\"tit\":\"RA001\",\"val\":\"��Ʒ1\"},{\"tit\":\"RA002\",\"val\":\"��Ʒ2\"}]}";
	private static ObjectMapper mapper = new ObjectMapper();
	
	
	@Test
	public void test4() {
		String json = "{\\\"backgroud\\\":\\\"2a26f1e625d801d5.png\\\",\\\"category\\\":\\\"音乐\\\",\\\"height\\\":\\\"5\\\",\\\"id\\\":180514714823103,\\\"name\\\":\\\"music\\\",\\\"setting\\\":{\\\"ImageSet\\\":[{\\\"default\\\":{\\\"nine-path\\\":\\\"c3e07ca596c88992.9.png\\\"},\\\"description\\\":\\\"用来设置音乐播放控件背景\\\",\\\"key\\\":\\\"background\\\",\\\"label\\\":\\\"背景\\\"},{\\\"default\\\":{\\\"alignType\\\":\\\"LT\\\",\\\"image\\\":\\\"90f931027e95c8b6.png\\\",\\\"scaleType\\\":\\\"01\\\"},\\\"description\\\":\\\"设置默认的专辑图片\\\",\\\"key\\\":\\\"albumPic\\\",\\\"label\\\":\\\"专辑图片\\\"},{\\\"default\\\":{\\\"image\\\":\\\"bff0688b3f9433d9.png\\\"},\\\"description\\\":\\\"播放按钮图标\\\",\\\"key\\\":\\\"playBtn\\\",\\\"label\\\":\\\"播放按钮\\\"},{\\\"default\\\":{\\\"image\\\":\\\"1511b4f6020ec61d.png\\\"},\\\"description\\\":\\\"暂停按钮图标\\\",\\\"key\\\":\\\"playBtn\\\",\\\"label\\\":\\\"暂停按钮\\\"}],\\\"TextSet\\\":[{\\\"default\\\":{\\\"color\\\":\\\"#FFFFFF\\\",\\\"def_text\\\":\\\"\\\",\\\"font_style\\\":[\\\"italic\\\",\\\"bold\\\",\\\"underline\\\",\\\"deleteline\\\"],\\\"line_spacing\\\":\\\"12\\\",\\\"size\\\":\\\"23\\\"},\\\"description\\\":\\\"设置歌曲名文本显示属性\\\",\\\"key\\\":\\\"musicName\\\",\\\"label\\\":\\\"歌曲名\\\"},{\\\"default\\\":{\\\"background\\\":{\\\"color\\\":\\\"#00000000\\\"},\\\"color\\\":\\\"#FFFFFF\\\",\\\"def_text\\\":\\\"\\\",\\\"font_style\\\":[\\\"italic\\\",\\\"bold\\\",\\\"underline\\\",\\\"deleteline\\\"],\\\"line_spacing\\\":\\\"12\\\",\\\"size\\\":\\\"23\\\"},\\\"description\\\":\\\"设置专辑名文本显示属性\\\",\\\"key\\\":\\\"albumName\\\",\\\"label\\\":\\\"专辑名\\\"}],\\\"multiSelect\\\":[{\\\"default\\\":[\\\"name\\\",\\\"src\\\"],\\\"description\\\":\\\"选择要显示的功能项（多选）\\\",\\\"key\\\":\\\"contentSettings\\\",\\\"label\\\":\\\"功能显示\\\",\\\"values\\\":[{\\\"label\\\":\\\"歌曲名称\\\",\\\"value\\\":\\\"name\\\"},{\\\"label\\\":\\\"歌词\\\",\\\"value\\\":\\\"lyric\\\"},{\\\"label\\\":\\\"专辑名\\\",\\\"value\\\":\\\"album\\\"}]}],\\\"singleSelect\\\":[{\\\"default\\\":\\\"rotate\\\",\\\"description\\\":\\\"用来表示翻页的动画\\\",\\\"key\\\":\\\"pageAnim\\\",\\\"label\\\":\\\"翻页动画\\\",\\\"values\\\":[{\\\"lable\\\":\\\"旋转\\\",\\\"value\\\":\\\"rotate\\\"},{\\\"lable\\\":\\\"放大\\\",\\\"value\\\":\\\"enlarge\\\"}]}]},\\\"version\\\":\\\"v1.0\\\",\\\"width\\\":\\\"3\\\",\\\"x\\\":0,\\\"y\\\":0}";
		try {
			//Map map = mapper.readValue(json, Map.class);
			System.out.println(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	public static String getObjectByJson(Object obj){
		String str = "";
		try {
			str = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			System.out.println("###[Error] getObjectToJson() "+e.getMessage());
		}
		return str;
	}
	public static Object viewJsonTree(Object m){
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
					}else if(e.getValue() instanceof LinkedHashMap){
						System.out.println("{Map}"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((LinkedHashMap)e.getValue());
					}else if(e.getValue() instanceof ArrayList){
						System.out.println("[Array]"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((ArrayList)e.getValue());
					}
				}  	
			}
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						viewJsonTree((LinkedHashMap)ls.get(i));
					}else if(ls.get(i) instanceof ArrayList){
						viewJsonTree((ArrayList)ls.get(i));
					}	
				}
			}	
			System.out.println();
		} catch (Exception e) {
			System.out.println("###[Error] viewJsonTree() "+e.getMessage());
		}
		return null;
	}	
	
	
	private int i = 0;
	public Object getObjectByJson(String jsonStr,String argsPath,TypeEnum argsType){
		if(argsPath == null || argsPath.equals("") || argsType == null){ 
			System.out.println("over...");return null;
		}
		
		Object obj = null;
		try {
			Map maps = mapper.readValue(jsonStr, Map.class);
			if(argsPath.indexOf(".") >= 0){
				obj = getObject(maps,argsPath,argsType);
			}else{ 
				if(argsType == TypeEnum.string){
					obj = maps.get(argsPath).toString();
				}else if(argsType == TypeEnum.map){
					obj = (Map)maps.get(argsPath);
				}else if(argsType == TypeEnum.arrayList){
					obj = (List)maps.get(argsPath);
				}else if(argsType == TypeEnum.arrayMap){
					obj = (List<Map>)maps.get(argsPath);
				}
			}
		} catch (Exception e) {
			System.out.println("###[Error] getObjectByJson() "+e.getMessage());
		}
		return obj;
	}
	private Object getObject(Object m,String key,TypeEnum type){
		if(m == null){ System.out.println("over...");return null;}
		Object o = null; 
		
		Map mp = null;
		List ls = null;
		try {
			if(m instanceof Map || m instanceof LinkedHashMap){
				mp = (LinkedHashMap)m;
				for(Iterator ite = mp.entrySet().iterator(); ite.hasNext();){  
					Map.Entry e = (Map.Entry) ite.next();  
					
					if(i<key.split("\\.").length && e.getKey().equals(key.split("\\.")[i])){
						System.out.println("["+key.split("\\.").length+"]["+key+"]["+(i+1)+"][OK]["+key.split("\\.")[i]+"]"); //Val [" + e.toString()+"]
						i++;
						if(e.getValue() instanceof String){
							if(i== key.split("\\.").length){
								o = e.getValue();
								return o;
							}
						}else if(e.getValue() instanceof LinkedHashMap){
							if(i== key.split("\\.").length){
								if(type == TypeEnum.map){
									o = (LinkedHashMap)e.getValue();
									return o;
								}
							}else{
								o = getObject((LinkedHashMap)e.getValue(),key,type);
							}
							return o;
						}else if(e.getValue() instanceof ArrayList){
							if(i== key.split("\\.").length){
								if(type == TypeEnum.arrayList){
									o = (ArrayList)e.getValue();
									return o;
								}
								if(type == TypeEnum.arrayMap){
									o = (ArrayList<Map>)e.getValue();
									return o;
								}
							}else{
								o = getObject((ArrayList)e.getValue(),key,type);
							}
							return o;
						}
					}else{
						System.out.println("["+key.split("\\.").length+"]["+key+"]["+(i+1)+"][NO]["+e.getKey()+"]");
					}
				}  	
			}
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						if(i== key.split("\\.").length){
							if(type == TypeEnum.map){
								o = (LinkedHashMap)ls.get(i);
								return o;
							}
						}else{
							o = getObject((LinkedHashMap)ls.get(i),key,type);
						}
						return o;
					}else if(ls.get(i) instanceof ArrayList){
						if(i== key.split("\\.").length){
							if(type == TypeEnum.arrayList){
								o = (ArrayList)ls.get(i);
								return o;
							}
							if(type == TypeEnum.arrayMap){
								o = (ArrayList<Map>)ls.get(i);
								return o;
							}
						}else{
							o = getObject((ArrayList)ls.get(i),key,type);
						}
						return o;
					}	
				}
			}	
			System.out.println();
		} catch (Exception e) {
			System.out.println("###[Error] getObject() "+e.getMessage());
		}
		
		return o;
	}
	
	
	/*
	 */
	public enum TypeEnum{
        string,
        map,
        arrayList,
        arrayMap;
    }
}
