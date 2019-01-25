package com.pactera.config.header;
import com.navinfo.wecloud.common.rest.response.CommonResult;
import com.navinfo.wecloud.saas.api.facade.TenantFacade;
import com.navinfo.wecloud.saas.api.response.TenantInfo;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author zhaodong
 * @Date 16:05 2019/1/21
 */
@Component
@Slf4j
public class TenantFacadeV1 {

    @Resource
    private TenantFacade tenantFacade;

    /**
     * 根据租户id获取租户的信息
     * @Author zhaodong
     * @Date 16:17 2019/1/21
     * @Param
     * @return
     **/
    public TenantInfo tenantInfo(int tenantId){
        log.info("根据租户id获取租户的信息开始>>>>>>>>>>>>>>>>>> tenantId = " + tenantId + new Date());
        CommonResult<TenantInfo> tenant = null;
        tenant = tenantFacade.getTenant(tenantId);
        log.info("根据租户id获取租户的信息进行>>>>>>>>>>>>>>>>>>  " + tenant.getData() + new Date());
        /* if (tenant.getData()==null){
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_NOT_TENANTINFO.status(),ErrorStatus.SERVICE_ERROR_NOT_TENANTINFO.message());
        }else{*/
        return tenant.getData();
       // }
    }

    /**
     * 获取租户的名字
     * @Author zhaodong
     * @Date 16:57 2019/1/21
     * @Param
     * @return
     **/
    public String tenantInfoName(int tenantId){
        TenantInfo tenantInfo = this.tenantInfo(tenantId);
        if (tenantInfo==null){
            log.info("根据tanlentId反查租户为空>>>>>>>>因为此时为系统管理员tanlentId临时存的-1，查询不到>>>>>>>> "+new Date());
            return "系统用户";
        }
        if (!StringUtils.isBlank(tenantInfo.getName())){
            log.info("根据租户id获取租户的名字开始>>>>>>>>>>>>>>>>>>  "+ tenantInfo.getName() +new Date());
            return tenantInfo.getName();
        }else{
            log.info("根据tanlentId反查租户存在>>>>>>>>但是getName()为空或空字符串>>>>>>>> "+new Date());
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_NOT_TENANTINFONAME.status(),ErrorStatus.SERVICE_ERROR_NOT_TENANTINFONAME.message());
        }
    }

    /**
     * 获取租户的开发者apiKey
     * @Author zhaodong
     * @Date 16:58 2019/1/21
     * @Param
     * @return
     **/
    public String tenantInfoApiKey(int tenantId){
        log.info("根据租户id获取租户的apiKey开始>>>>>>>>>>>>>>>>>>  "+new Date());
        TenantInfo tenantInfo = this.tenantInfo(tenantId);
        if (!StringUtils.isBlank(tenantInfo.getApiKey())){
            return tenantInfo.getApiKey();
        }else{
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_NOT_TENANTINFOAPIKEY.status(),ErrorStatus.SERVICE_ERROR_NOT_TENANTINFOAPIKEY.message());
        }
    }
    /**
     * 获取租户的英文名字
     * @Author zhaodong
     * @Date 16:58 2019/1/21
     * @Param
     * @return
     **/
    public String tenantInfoNameEn(int tenantId){
        log.info("根据租户id获取租户的英文名字开始>>>>>>>>>>>>>>>>>>  "+new Date());
        TenantInfo tenantInfo = this.tenantInfo(tenantId);
        if (!StringUtils.isBlank(tenantInfo.getNameEn())){
            return tenantInfo.getNameEn();
        }else{
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_NOT_TENANTINFOENNAME.status(),ErrorStatus.SERVICE_ERROR_NOT_TENANTINFOENNAME.message());
        }
    }
}
