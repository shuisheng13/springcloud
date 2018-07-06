package com.pactera.listening;

import com.netflix.appinfo.InstanceInfo;  
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;  
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;  
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;  
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;  
import org.springframework.context.event.EventListener;  
import org.springframework.stereotype.Component;  

@Component
public class EurekaStateChangeListener {

    @EventListener //服务下线事件
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {  
        //服务断线事件  
        String appName = eurekaInstanceCanceledEvent.getAppName();  
        String serverId = eurekaInstanceCanceledEvent.getServerId();  
        System.out.println("服务断线事件:"+appName+":"+serverId);  
    } 
    
    @EventListener //服务注册事件
    public void listen(EurekaInstanceRegisteredEvent event) {  
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println("服务注册事件:"+instanceInfo.getAppName()+":"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort());  
    } 
    
//    @EventListener //服务续约事件
//    public void listen(EurekaInstanceRenewedEvent event) {  
//        String appName = event.getAppName();  
//        String serverId = event.getServerId(); 
//        System.out.println("服务续约事件:"+appName+":"+serverId);
//    } 
    
    @EventListener //注册中心启动事件
    public void listen(EurekaRegistryAvailableEvent event) {  
    	System.out.println("注册中心启动事件:"+event);
    }  
    
    @EventListener //Server启动事件
    public void listen(EurekaServerStartedEvent event) {  
    	System.out.println("Server启动事件:"+event);
    }  
}
