package com.pactera.utlis;

import com.github.promeg.pinyinhelper.Pinyin;

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
	public static String Id(String prefix, String creator){

        char[] chars = creator.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<2; i++) {
            String value = Pinyin.isChinese(chars[i])?
                    Pinyin.toPinyin(chars[i]).substring(0,1):
                    String.valueOf(Character.toUpperCase(chars[i]));
            sb.append(value);
        }
        String random = Math.random() + "";
        String num = random.substring(random.indexOf(".") + 1, 9);
		return  prefix + sb.toString() + num;
	}

	public static String UUId() {
		return UUID.randomUUID().toString();
	}

}
