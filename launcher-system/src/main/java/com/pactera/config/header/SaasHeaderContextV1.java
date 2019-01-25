package com.pactera.config.header;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import com.navinfo.wecloud.common.filter.SaasHeaderContext;

/**
 *  saas head透传context
 *  @Author zhaodong
 *  @Date 2019/1/3 9:11
 */
@Slf4j
public class SaasHeaderContextV1 {

    public static String getTenantId() {
        log.info("userType为>>>>>>>>>>>>>>>>>  "+SaasHeaderContextV1.getUserType());
        Integer tenantId = SaasHeaderContext.getTenantId();
        if (tenantId!=null) {
            return tenantId + "";
        }else {
            int exception = SaasHeaderContextV1.exception();
            return exception + "";
        }
    }

    public static int getTenantIdInt() {
        log.info("userType为>>>>>>>>>>>>>>>>>  "+SaasHeaderContextV1.getUserType());
        Integer tenantId = SaasHeaderContext.getTenantId();
        if (tenantId != null) {
            return tenantId;
        } else {
            int exception = SaasHeaderContextV1.exception();
            return exception;
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
        log.info("userType为>>>>>>>>>>>>>>>>>  "+SaasHeaderContextV1.getUserType());
        if(!StringUtils.isBlank(SaasHeaderContext.getUserName())){
            return SaasHeaderContext.getUserName();
        }
        return null;
    }

    public static String getTenantName() {
        log.info("userType为>>>>>>>>>>>>>>>>>  "+SaasHeaderContextV1.getUserType());
        if(!StringUtils.isBlank(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME)) ){
            try {
                return URLDecoder.decode(SaasHeaderContext.getHeaders().get(GatewayHeaderKey.TENANT_NAME), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            int exception = SaasHeaderContextV1.exception();
            if (exception==-1){
                return "XTYH";
            }
        }
        return null;
    }

    private static int exception(){
        if (SaasHeaderContextV1.getUserType()==0){
            log.error("该系统管理员为最高管理员，不是普通租户,userType= >>>>>>>>"+SaasHeaderContextV1.getUserType()+" >>>>>>>>"+new Date());
            return -1;
        }else{
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_NOT_TENANT.status(),ErrorStatus.SERVICE_ERROR_NOT_TENANT.message());
        }
    }
}
