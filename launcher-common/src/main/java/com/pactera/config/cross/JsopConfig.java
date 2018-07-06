package com.pactera.config.cross;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsopConfig extends AbstractJsonpResponseBodyAdvice {
	public JsopConfig() {
		 super("callback","jsonp");
	}
}
