package com.pactera.utlis;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhaodong
 * @Date 13:38 2019/3/7
 * @Param 
 * @return 
 **/
@Component
public class RedisUtil {

    @Resource
    ValueOperations<String, Object> valueOperations;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    ListOperations<String, Object> listOperations;

    @Resource
    HashOperations<String, String, Object> hashOperations;

    /**
     * 添加缓存
     */
    public void setValue(String key,Object value){
        valueOperations.set(key,value);
    }

    /**
     * 添加缓存（time为过期时间）
     */
    public void setValueTime(String key,Object value,long time){
        valueOperations.set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 读缓存
     */
    public Object getValue(String key){
        Object value = valueOperations.get(key);
        return value;
    }

    /**
     * 删除缓存
     */
    public boolean delete(String key) {
        RedisOperations<String, Object> operations = valueOperations.getOperations();
        operations.delete(key);
        return true;
    }


    /**
     * @Author zhaodong
     * @Date 9:00 2019/3/11
     * @Param
     * @return
     **/
    public boolean isKey(String key){
       return this.getValue(key)==null?false:true;
    }
    /**
     * 简单加锁(解锁删除即可)
     * @Author zhaodong
     * @Date 15:42 2019/3/8
     * @Param
     * @return
     **/
    public boolean setLock(String key,Object value){
        valueOperations.setIfAbsent(key, value);
        Boolean expire = redisTemplate.expire(key, 30000, TimeUnit.MILLISECONDS);
        return expire;
    }







}
