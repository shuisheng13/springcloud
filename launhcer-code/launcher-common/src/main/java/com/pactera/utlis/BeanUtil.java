package com.pactera.utlis;

import org.springframework.beans.BeanUtils;

public class BeanUtil {
	
	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {

		}
	}
}
