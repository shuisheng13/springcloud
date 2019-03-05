package com.pactera.business.service;

/**
 *
 * @author xukj
 */
public interface LaunVersionService {

    /**
     * @Author xukj
     * @Description 给车机端提供的上传接口
     * @Date 16:56 2018/12/19
     * @Param version 版本号
     * @Param versionName 版本名称
     * @Param apiKey
     * @return
     **/
    int add(double version, String versionName, String apiKey, String layoutName);

}
