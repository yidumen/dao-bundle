package com.yidumen.dao.framework.hibernate.cache.ace;

import com.alibaba.appengine.api.cache.CacheServiceFactory;
import java.util.HashMap;
import java.util.Properties;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻
 */
public final class AceRegionFactory implements RegionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(AceRegionFactory.class);
    private Settings settings;

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
        LOG.debug("缓存工作开始");
    }

    @Override
    public void stop() {
        LOG.debug("缓存工作结束");
    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    @Override
    public AccessType getDefaultAccessType() {
        return AccessType.READ_ONLY;
    }

    @Override
    public long nextTimestamp() {
        return CacheServiceFactory.getCacheService().increment("cacheTimestamp", 1, 0L);
    }

    @Override
    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        LOG.debug("创建实体缓存：{}", regionName);
        return new AceEntityRegion(regionName, metadata, settings);
    }

    @Override
    public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        LOG.debug("创建索引缓存：{}", regionName);
        return new AceNaturalIdRegion(regionName, metadata, settings);
    }

    @Override
    public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        LOG.debug("创建集合缓存：{}", regionName);
        return new AceCollectionRegion(regionName, metadata, settings);
    }

    @Override
    public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
        LOG.debug("创建查询缓存：{}", regionName);
        return new AceQueryResultsRegion(regionName, null, settings);
    }

    @Override
    public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
        LOG.debug("创建时间戳缓存：{}", regionName);
        return new AceTimestampsRegion(regionName, null, settings);
    }

}
