package com.yidumen.dao.framework.hibernate.cache.ace;

import com.alibaba.appengine.api.cache.CacheServiceFactory;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.GeneralDataRegion;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现GeneralDataRegion以便于<code>QueryResultsRegion</code>和<code>TimesampsRegion</code>的子类继承
 *
 * @author 蔡迪旻
 */
@SuppressWarnings("unchecked")
public class AceGeneralDataRegion extends AceRegion implements GeneralDataRegion {

    private final static Logger LOG = LoggerFactory.getLogger(AceGeneralDataRegion.class);

    public AceGeneralDataRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

    @Override
    public Object get(Object key) throws CacheException {
        final Map<String, Object> cacheMap = (Map<String, Object>) CacheServiceFactory.getCacheService().get(regionName);
        LOG.debug("获取区域 {} 的缓存 {}", regionName, key);
        return cacheMap.get(key.toString());
    }

    @Override
    public void put(final Object key, final Object value) throws CacheException {
        LOG.debug("在区域 {} 中写入缓存 {}", regionName, key);
        HashMap<String, Object> cacheMap = (HashMap<String, Object>) CacheServiceFactory.getCacheService().get(regionName);
        cacheMap.put(key.toString(), value);
        CacheServiceFactory.getCacheService().put(regionName, cacheMap);
    }

    @Override
    public void evict(Object key) throws CacheException {
        LOG.debug("删除区域 {} 缓存 {}", regionName, key);
        ((Map) CacheServiceFactory.getCacheService().get(regionName)).remove(key);
    }

    @Override
    public void evictAll() throws CacheException {
        LOG.debug("清空区域 {}", regionName);
        ((Map) CacheServiceFactory.getCacheService().get(regionName)).clear();
    }

}
