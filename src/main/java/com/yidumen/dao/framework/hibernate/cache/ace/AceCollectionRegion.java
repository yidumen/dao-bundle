package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public class AceCollectionRegion extends AceRegion implements CollectionRegion {

    public AceCollectionRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

    @Override
    public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return new AceCollectionRegionAccessStrategy(this, settings);
    }
    
}
