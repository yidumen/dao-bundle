package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public final class AceNaturalIdRegion extends AceRegion implements NaturalIdRegion {

    public AceNaturalIdRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

    @Override
    public NaturalIdRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return new AceNaturalIdRegionAccessStrategy(this, settings);
    }
    
}
