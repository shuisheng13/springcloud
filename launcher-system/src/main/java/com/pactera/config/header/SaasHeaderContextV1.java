package com.pactera.config.header;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang.StringUtils;
import com.navinfo.wecloud.common.filter.SaasHeaderContext;

/**
 *  saas head透传context
 *  @Author zhaodong
 * @Date 2019/1/3 9:11
 */
public class SaasHeaderContextV1 {

    public static Integer getTenantId() {
        return SaasHeaderContext.getTenantId();
    }

    public static Integer getUserId() {
        return SaasHeaderContext.getUserId();
    }

    public static Integer getOrgId() {
        return SaasHeaderContext.getOrgId();
    }

    public static Integer getUserType() {
        return StringUtils.isBlank(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.USER_TYPE)) ? null
                : Integer.valueOf(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.USER_TYPE));
    }

    public static String getOrgCode() {
        return SaasHeaderContext.getOrgCode();
    }

    public static String getToken() {
       return SaasHeaderContext.getToken();
    }

    public static String getUserName() {
        return SaasHeaderContext.getUserName();
    }

    public static String getTenantName() {
        System.out.println(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME));
        if(!StringUtils.isBlank(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME)) ){
            try {
                return URLDecoder.decode(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }
}
