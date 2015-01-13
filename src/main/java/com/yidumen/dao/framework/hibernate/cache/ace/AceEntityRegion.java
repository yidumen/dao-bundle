package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public final class AceEntityRegion extends AceRegion implements EntityRegion {

    public AceEntityRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

    @Override
    public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return new AceEntityRegionAccessStrategy(this, settings);
    }

}
