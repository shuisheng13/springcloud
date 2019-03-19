package com.pactera;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.navinfo.wecloud.common.filter.SaasCommonHeaderFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;


@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@Import(FdfsClientConfig.class)
@MapperScan(basePackages = "com.pactera.business.dao")
@Configuration
@EnableConfigurationProperties
@EnableFeignClients({"com.navinfo.wecloud.saas.api.facade"})
public class SystemStartBean  {
    public static void main(String[] args) {
        SpringApplication.run(SystemStartBean.class, args);
    }

    @Value("${wecloud.saas.header.filter.urlPatterns:/*}")
    public String urlPattens;

    @Bean
    public FilterRegistrationBean headerFilter() {
        FilterRegistrationBean hystrixFilter = new FilterRegistrationBean();
        hystrixFilter.setFilter(new SaasCommonHeaderFilter());
        hystrixFilter.addUrlPatterns(urlPattens);
        hystrixFilter.setOrder(2);
        return hystrixFilter;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件最大
        factory.setMaxFileSize("100MB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }


}
