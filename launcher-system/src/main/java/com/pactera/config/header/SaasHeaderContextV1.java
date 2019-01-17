package com.pactera.config.header;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang.StringUtils;
import com.navinfo.wecloud.common.filter.SaasHeaderContext;

/**
 *  saas head透传context
 *  @Author zhaodong
 *  @Date 2019/1/3 9:11
 */
public class SaasHeaderContextV1 {

    public static String getTenantId() {
        Integer tenantId = SaasHeaderContext.getTenantId();
        if (tenantId!=null) {
            return tenantId + "";
        }else {
            throw new NullPointerException("获取租户id失败，租户id为空");
        }
    }

    public static int getTenantIdInt() {
        Integer tenantId = SaasHeaderContext.getTenantId();
        if (tenantId != null) {
            return tenantId;
        } else {
            throw new NullPointerException("获取租户id失败，租户id为空");
        }
    }

    public static Integer getUserId() {
        return SaasHeaderContext.getUserId();
    }

    public static Integer getOrgId() {
        return SaasHeaderContext.getOrgId();
    }

    public static Integer getUserType() {
        return StringUtils.isBlank(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.USER_TYPE)) ? null : Integer.valueOf(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.USER_TYPE));
    }

    public static String getOrgCode() {
        return SaasHeaderContext.getOrgCode();
    }

    public static String getToken() {
       return SaasHeaderContext.getToken();
    }

    public static String getUserName() {
        if(!StringUtils.isBlank(SaasHeaderContext.getUserName()) ){
            return SaasHeaderContext.getUserName();
        }else{
            throw new NullPointerException("获取用户名字失败，用户名字为空");
        }
    }

    public static String getTenantName() {
        // System.out.println(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME));
        if(!StringUtils.isBlank(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME)) ){
            try {
                return URLDecoder.decode(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            throw new NullPointerException("获取租户名字失败，租户名字为空");
        }
        return null;
    }
}
