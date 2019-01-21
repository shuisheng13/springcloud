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
        log.info("根据租户id获取租户的信息开始>>>>>>>>>>>>>>>>>>  "+new Date());
        CommonResult<TenantInfo> tenant = null;
        try {
            tenant = tenantFacade.getTenant(tenantId);
        } catch (Exception e) {
            throw new DataStoreException(ErrorStatus.SERVICE_ERROR_CLIENTEXCEPTION.status(),ErrorStatus.SERVICE_ERROR_CLIENTEXCEPTION.message());
        }
        if (tenant.getData()==null){
            throw new NullPointerException("连接saas成功,但是查询租户id为空、根据租户id查询租户信息不存在");
        }else{
            return tenant.getData();
        }
    }

    /**
     * 获取租户的名字
     * @Author zhaodong
     * @Date 16:57 2019/1/21
     * @Param
     * @return
     **/
    public String tenantInfoName(int tenantId){
        log.info("根据租户id获取租户的名字开始>>>>>>>>>>>>>>>>>>  "+new Date());
        TenantInfo tenantInfo = this.tenantInfo(tenantId);
        if (!StringUtils.isBlank(tenantInfo.getName())){
            return tenantInfo.getName();
        }else{
            throw new NullPointerException("租户名字为空");
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
            throw new NullPointerException("租户apiKey为空");
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
            throw new NullPointerException("租户英文名字为空");
        }
    }
}
