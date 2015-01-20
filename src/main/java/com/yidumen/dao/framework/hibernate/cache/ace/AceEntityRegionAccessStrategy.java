package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public final class AceEntityRegionAccessStrategy extends AceRegionAccessStrategy implements EntityRegionAccessStrategy {

    public AceEntityRegionAccessStrategy(EntityRegion region, Settings settings) {
        super(region, settings);
    }

    @Override
    public EntityRegion getRegion() {
        return (EntityRegion) this.region;
    }

}
