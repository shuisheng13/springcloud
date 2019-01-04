package com.pactera.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdUtlis {
	
	public static Long Id() {
		UUID randomUUID = UUID.randomUUID();
		String replace = randomUUID.toString().replace("-", "");
		Long long2 = DateId();
		Long long1 = new Long(replace.hashCode());
		return long1 + long2;
	}

	public static Long DateId() {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss SSS");
		String str = format.format(new Date());
		str = str.trim().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		return new Long(str);
	}

	// 生成主题分类id
	public static String Id(String prefix ,String creator){
		String substring = creator.substring(0,2);// 前两个字母大写
		String creatorTwo = substring.toUpperCase();
		return  prefix + creatorTwo + (int)(Math.random() * 10000000);
	}

	public static String UUId() {
		return UUID.randomUUID().toString();
	}

}
