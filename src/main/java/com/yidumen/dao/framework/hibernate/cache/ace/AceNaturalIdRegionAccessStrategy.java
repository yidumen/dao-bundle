package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public final class AceNaturalIdRegionAccessStrategy extends AceRegionAccessStrategy implements NaturalIdRegionAccessStrategy {

    public AceNaturalIdRegionAccessStrategy(NaturalIdRegion region, Settings settings) {
        super(region, settings);
    }

    @Override
    public NaturalIdRegion getRegion() {
        return (NaturalIdRegion) this.region;
    }

}
