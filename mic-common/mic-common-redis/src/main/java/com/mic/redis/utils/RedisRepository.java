package com.mic.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Description:  
 * @author: pf
 * @create: 2021/1/13 15:57
 */       
@Slf4j
public class RedisRepository {

    private RedisTemplate<String, Object> redisTemplate;

    private RedisTemplate<String, String> stringRedisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate,  RedisTemplate<String, String> stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public String getStr(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void setStr(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public  Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 批量获取普通对象
     *
     * @param keys 键
     * @return 对象
     */
    public  List<Object> mGet(final String... keys) {
        if(keys == null || keys.length <=0 ){
            return null;
        }
        ArrayList keyList = new ArrayList();
        for(String key : keys) {
            keyList.add(key);
        }
        return mGet(keyList);
    }


    /**
     * 批量获取普通对象
     *
     * @param keys 键
     * @return 对象
     */
    public  List<Object> mGet(final Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 存入普通对象
     *
     * @param key Redis键
     * @param value 值
     */
    public  void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     * 存入普通对象
     *
     * @param key 键
     * @param value 值
     * @param timeout 有效期，单位秒
     */
    public  void setExpire(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存入普通对象
     *
     * @param key 键
     * @param value 值
     * @param timeout 有效期，单位秒
     */
    public  void setStrExpire(final String key, final String value, final long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }


    /**
     * 判断keys其中任意键中是否已存在
     *
     * @param keys 键
     * @return 对象
     */
    public  Boolean existAny(final String... keys) {
        if(keys == null || keys.length ==0){
            return Boolean.FALSE;
        }
        for(String key : keys){
            if(redisTemplate.opsForValue().get(key) != null){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断keys其中任意键中是否已存在
     *
     * @param keys 键
     * @return 对象
     */
    public  Boolean existStrAny(final String... keys) {
        if(keys == null || keys.length ==0){
            return Boolean.FALSE;
        }
        for(String key : keys){
            if(stringRedisTemplate.opsForValue().get(key) != null){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public  boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public  boolean expire(final String key, final long timeout, final TimeUnit unit) {

        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public  boolean del(final String key) {

        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键
     *  @return 成功删除的个数
     */
    public  long del(final String... keys) {
        if(keys == null || keys.length <=0 ){
            return 0;
        }
        ArrayList keyList = new ArrayList();
        for(String key : keys){
            keyList.add(key);
        }
        return del(keyList);
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public  long del(final Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    public  long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public  long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

//    ----------------------------- Hash 操作 ------------------------

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public  Object hGet(final String key, final String hKey) {
        return redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public  List<Object> hMultiGet(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }


    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public  void hPut(final String key, final String hKey, final Object value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key Redis键
     * @param values Hash键值对
     */
    public  void hPutAll(final String key, final Map<String, Object> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

//    ----------------------------- Set 操作 ------------------------

    /**
     * 获取Set中是否存在key,value的数据
     *
     * @param key Redis键
     * @return 是否存在
     */
    public  Set<Object> sGet(final String key) {
        Set<Object> objectSet = redisTemplate.opsForSet().members(key);
        return objectSet;
    }

    /**
     * 获取Set中是否存在key,value的数据
     *
     * @param key Redis键
     * @return 是否存在
     */
    public  Set<String> sGetStr(final String key) {
        Set<String> objectSet = stringRedisTemplate.opsForSet().members(key);
        return objectSet;
    }

    /**
     * 获取Set中key的数据
     *
     * @param key Redis键
     * @return Set中key的数据
     */
    public  Boolean sExist(final String key,Object value) {
        Boolean exists = redisTemplate.opsForSet().isMember(key,value);
        return exists;
    }

    /**
     * 获取Set中key的数据
     *
     * @param key Redis键
     * @return Set中key的数据
     */
    public  Boolean sExistStr(final String key,String value) {
        Boolean exists = stringRedisTemplate.opsForSet().isMember(key,value);
        return exists;
    }

    /**
     * 往Set中存入数据
     *
     * @param key Redis键
     * @param values 值
     * @return 存入的个数
     */
    public  long sSet(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往Set中存入数据
     *
     * @param key Redis键
     * @param values 值
     * @return 存入的个数
     */
    public  long sSet(final String key, final Collection<Object> values) {
        Object[] objects = values.toArray();
        Long count = redisTemplate.opsForSet().add(key, objects);
        return count == null ? 0 : count;
    }
    /**
     * 往Set中存入数据
     *
     * @param key Redis键
     * @param values 值
     * @return 存入的个数
     */
    public  long sSetStr(final String key, final String... values) {
        Long count = stringRedisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往Set中存入数据
     *
     * @param key Redis键
     * @param values 值
     * @return 存入的个数
     */
    public  long sSetStr(final String key, final Collection<String> values) {
        String[] strValues = values.toArray(new String[]{});
        Long count = stringRedisTemplate.opsForSet().add(key, strValues);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key Redis键
     * @param values 值
     * @return 移除的个数
     */
    public  long sDel(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * Set取差集
     *
     * @param keys 取差集的key集合
     * @return 差集集合
     */
    public  Set<String> diff(final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().difference( keys);
    }

    /**
     * Set取差集并存储
     *
     * @param key 结果存储到的key
     * @param keys 取差集的key集合
     * @return 差集数量
     */
    public  Long diffStore(final String key, final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().differenceAndStore(keys, key);
    }


    /**
     * Set取交集
     *
     * @param keys 取交集的key集合
     * @return 交集集合
     */
    public  Set<String> intersect(final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().intersect( keys);
    }

    /**
     * Set取交集并存储
     *
     * @param key 结果存储到的key
     * @param keys 取交集的key集合
     * @return 交集数量
     */
    public  Long intersectStore(final String key, final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().intersectAndStore(keys, key);
    }

    /**
     * Set取并集
     *
     * @param keys 取差集的key集合
     * @return 并集集合
     */
    public  Set<String> union(final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().union(keys);
    }


    /**
     * Set取并集并存储
     *
     * @param key 结果存储到的key
     * @param keys 取并集的key集合
     * @return 并集集合
     */
    public  Long unionStore(final String key, final Collection<String> keys) {
        return stringRedisTemplate.opsForSet().unionAndStore(keys,key);
    }

//    ----------------------------- List 操作 ------------------------

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key Redis键
     * @param start 开始位置
     * @param end 结束位置（start=0，end=-1表示获取全部元素）
     * @return List对象
     */
    public  List<Object> lGet(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 往List中存入数据
     *
     * @param key Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public  long rPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public  long lPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public  long lPushAll(final String key, final Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * redis List 引擎
     *
     * @return the list operations
     */
    public ListOperations<String, Object> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    public Long length(String key) {
        return opsForList().size(key);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public List<Object> getList(String key, int start, int end) {
        return redisTemplate.opsForList().range(key,start,end);
    }


    public void listLrem(String key, int start, Object object) {
        redisTemplate.opsForList().remove(key, start, object);
    }
}
