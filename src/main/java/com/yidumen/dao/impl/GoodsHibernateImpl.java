package com.yidumen.dao.impl;

import com.yidumen.dao.GoodsDAO;
import com.yidumen.dao.entity.Goods;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class GoodsHibernateImpl extends AbstractHibernateImpl<Goods> implements GoodsDAO {

    public GoodsHibernateImpl(SessionFactory sessionFactory) {
        super(Goods.class);
        this.sessionFactory = sessionFactory;
    }

}
