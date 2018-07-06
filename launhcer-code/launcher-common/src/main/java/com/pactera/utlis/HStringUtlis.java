package com.pactera.utlis;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class HStringUtlis extends StringUtils {

	public static boolean isBlank(CharSequence cs) {
		boolean blank = StringUtils.isBlank(cs);
		if (!blank) {
			if (cs.equals("null")) {
				blank = true;
			}
		}
		return blank;
	}

	// 填充主键位数
	public static Long autoCompeleteVal(Long id) {
		String valueOf = String.valueOf(id);
		while (valueOf.length() < 10)
			valueOf = valueOf + "0";
		return new Long(valueOf);
	}

	public static String ChannelId() {
		String channelId = "";
		Random random = new Random();
		// 参数length，表示生成几位随机数
		for (int i = 0; i < 6; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				channelId += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				channelId += String.valueOf(random.nextInt(10));
			}
		}
		return channelId.toUpperCase();
	}
	
	public static void main(String[] args) {
		String channelId = ChannelId();
		System.out.println(channelId);
	}
}
