package com.pactera.config.header;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 公共头信息处理，业务依赖
 * @author zhaodong
 * 2019年1月3日
 */
public class SaasCommonHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HystrixRequestContext.initializeContext();
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Enumeration<String> keys = req.getHeaderNames();
            Map<String, String> map = SaasHeaderContext.getHeaders();
            if (map == null) {
                map = new ConcurrentHashMap<>();
                SaasHeaderContext.setHeaders(map);
            }
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                if (req.getHeader(key) != null) {
                    map.put(key, req.getHeader(key));
                }
            }
            chain.doFilter(request, response);
        } finally {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }

    @Override
    public void destroy() {
    }
}
