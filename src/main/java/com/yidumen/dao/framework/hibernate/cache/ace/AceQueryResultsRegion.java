package com.yidumen.dao.framework.hibernate.cache.ace;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cfg.Settings;

/**
 *
 * @author 蔡迪旻
 */
public final class AceQueryResultsRegion extends AceGeneralDataRegion implements QueryResultsRegion {

    public AceQueryResultsRegion(String regionName, CacheDataDescription metadata, Settings settings) {
        super(regionName, metadata, settings);
    }

}
