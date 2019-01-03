package com.pactera.config.header;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
/**
 *  saas head透传context
 *  @Author zhaodong
 * @Date 2019/1/3 9:11
 */
public class SaasHeaderContext {
    private static final HystrixRequestVariableDefault<Map<String, String>> INFO_THREAD_LOCAL = new HystrixRequestVariableDefault<Map<String, String>>();

    public static Map<String, String> getHeaders() {
        return INFO_THREAD_LOCAL.get();
    }

    public static void setHeaders(Map<String, String> map) {
        INFO_THREAD_LOCAL.set(map);
    }

    public static void release() {
        INFO_THREAD_LOCAL.remove();
    }

    public static Integer getTenantId() {
        return StringUtils.isBlank(getHeaders().get(GatewayHeaderKey.TENANT_ID)) ? null
                : Integer.valueOf(getHeaders().get(GatewayHeaderKey.TENANT_ID));
    }

    public static Integer getUserId() {
        return StringUtils.isBlank(getHeaders().get(GatewayHeaderKey.USER_ID)) ? null
                : Integer.valueOf(getHeaders().get(GatewayHeaderKey.USER_ID));
    }

    public static Integer getOrgId() {
        return StringUtils.isBlank(getHeaders().get(GatewayHeaderKey.ORG_ID)) ? null
                : Integer.valueOf(getHeaders().get(GatewayHeaderKey.ORG_ID));
    }

    public static Integer getUserType() {
        return StringUtils.isBlank(getHeaders().get(GatewayHeaderKey.USER_TYPE)) ? null
                : Integer.valueOf(getHeaders().get(GatewayHeaderKey.USER_TYPE));
    }

    public static String getOrgCode() {
        return getHeaders().get(GatewayHeaderKey.ORG_CODE);
    }

    public static String getToken() {
        return getHeaders().get(GatewayHeaderKey.TOKEN);
    }

    public static String getUserName() {
        return getHeaders().get(GatewayHeaderKey.USER_NAME);
    }
}