package com.yidumen.dao.framework.hibernate.cache.ace;

import com.alibaba.appengine.api.cache.CacheService;
import com.alibaba.appengine.api.cache.CacheServiceFactory;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.Region;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻
 */
@SuppressWarnings("unchecked")
public class AceRegion implements Region {

    private final static Logger LOG = LoggerFactory.getLogger(AceRegion.class);
    protected final String regionName;
    private final CacheDataDescription metadata;
    protected Settings settings;

    public AceRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        this.regionName = regionName;
        this.metadata = metadata;
        this.settings = settings;
        //创建缓存之前应该防止集群环境下出现域名相同的情况
        final CacheService cacheService = CacheServiceFactory.getCacheService();
        if (null == cacheService.get(regionName)) {
            cacheService.put(regionName, new HashMap<String, Object>());
        }
    }

    public CacheDataDescription getCacheDataDescription() {
        return metadata;
    }

    @Override
    public String getName() {
        return regionName;
    }

    @Override
    public void destroy() throws CacheException {
        CacheServiceFactory.getCacheService().delete(regionName);
    }

    @Override
    public boolean contains(Object key) {
        final Map<String, Object> cacheMap = (Map<String, Object>) CacheServiceFactory.getCacheService().get(regionName);
        final boolean result = cacheMap.containsKey(key.toString());
        LOG.debug(result ? "缓存已包含 {}" : "缓存未包含 {}", key);
        return result;
    }

    @Override
    public long getSizeInMemory() {
        return -1;
    }

    @Override
    public long getElementCountInMemory() {
        return ((Map<String, Object>) CacheServiceFactory.getCacheService().get(regionName)).size();
    }

    @Override
    public long getElementCountOnDisk() {
        return -1;
    }

    @Override
    public Map toMap() {
        return (Map<String, Object>) CacheServiceFactory.getCacheService().get(regionName);
    }

    @Override
    public long nextTimestamp() {
        return CacheServiceFactory.getCacheService().increment("cacheTimestamp", 1, 0L);
    }

    @Override
    public int getTimeout() {
        return 1000;
    }

    public boolean isTransactionAware() {
        return false;
    }
}
