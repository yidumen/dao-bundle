package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public class AceTimestampsRegion extends AceGeneralDataRegion implements TimestampsRegion {

    public AceTimestampsRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

    @Override
    public int getTimeout() {
        return 2000;
    }

}
