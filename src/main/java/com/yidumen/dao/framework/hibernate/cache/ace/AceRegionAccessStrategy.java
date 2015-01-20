package com.yidumen.dao.framework.hibernate.cache.ace;

import com.alibaba.appengine.api.cache.CacheServiceFactory;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.access.RegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻
 */
@SuppressWarnings("unchecked")
public class AceRegionAccessStrategy implements RegionAccessStrategy {

    private final static Logger LOG = LoggerFactory.getLogger(AceRegionAccessStrategy.class);
    protected final Region region;
    private final Settings settings;

    public AceRegionAccessStrategy(Region region, Settings settings) {
        this.region = region;
        this.settings = settings;
    }

    @Override
    public Object get(Object key, long txTimestamp) throws CacheException {
        LOG.debug("读取区域 {} 中的缓存 {}", region.getName(), key);
        final Map<String, Object> cacheMap = (Map<String, Object>) CacheServiceFactory.getCacheService().get(region.getName());
        final Object result = cacheMap.get(key.toString());
        LOG.debug(null == result ? "null" : result.getClass().getName());
        return result;
    }

    @Override
    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
        return putFromLoad(key, value, txTimestamp, version, settings.isMinimalPutsEnabled());
    }

    @Override
    public boolean putFromLoad(final Object key, final Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        if (minimalPutOverride && region.contains(key)) {
            LOG.debug("区域 {} 中已存在缓存 {}，写入终止", region.getName(), key);
            return false;
        } else {
            LOG.debug("在区域 {} 中写入缓存 {}", region.getName(), key);
            HashMap<String, Object> cacheMap = (HashMap<String, Object>) (Map) CacheServiceFactory.getCacheService().get(region.getName());
            cacheMap.put(key.toString(), value);
            return CacheServiceFactory.getCacheService().put(region.getName(), cacheMap);
        }
    }

    @Override
    public SoftLock lockItem(Object key, Object version) throws CacheException {
        LOG.debug("lockItem");
        return null;
    }

    @Override
    public SoftLock lockRegion() throws CacheException {
        LOG.debug("lockRegion");
        return null;
    }

    @Override
    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        evict(key);
    }

    @Override
    public void unlockRegion(SoftLock lock) throws CacheException {
        evictAll();
    }

    @Override
    public void remove(Object key) throws CacheException {
        evict(key);
    }

    @Override
    public void removeAll() throws CacheException {
        evictAll();
    }

    @Override
    public void evict(Object key) throws CacheException {
        LOG.debug("删除区域 {} 中的缓存 {}", region.getName(), key);
        ((Map) CacheServiceFactory.getCacheService().get(region.getName())).remove(key);
    }

    @Override
    public void evictAll() throws CacheException {
        LOG.debug("清空区域 {}", region.getName());
        ((Map) CacheServiceFactory.getCacheService().get(region.getName())).clear();
    }

    public boolean insert(Object key, Object value) throws CacheException {
        return false;
    }

    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean update(Object key, Object value) throws CacheException {
        return update(key, value, null, null);
    }

    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        evict(key);
        return false;
    }

    public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
        return afterUpdate(key, value, null, null, lock);
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        evict(key);
        return false;
    }
}
