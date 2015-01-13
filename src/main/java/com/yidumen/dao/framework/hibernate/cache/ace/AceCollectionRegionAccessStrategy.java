package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public class AceCollectionRegionAccessStrategy extends AceRegionAccessStrategy implements CollectionRegionAccessStrategy {

    public AceCollectionRegionAccessStrategy(Region region, Settings settings) {
        super(region, settings);
    }

    @Override
    public CollectionRegion getRegion() {
        return (CollectionRegion) this.region;
    }

}
