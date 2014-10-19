package com.yidumen.dao.impl;

import com.yidumen.dao.TagDAO;
import com.yidumen.dao.constant.TagType;
import com.yidumen.dao.entity.Tag;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class TagHibernateImpl extends AbstractHibernateImpl<Tag> implements TagDAO {

    public TagHibernateImpl(SessionFactory sessionFactory) {
        super(Tag.class);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tag> findTags(int limit) {
        final List<Tag> result = this.sessionFactory.getCurrentSession().getNamedQuery("Tag.OrderByHints")
                .setMaxResults(limit)
                .list();
        return result;
    }

    @Override
    public List<Tag> findVideoTags(final int limit) {
        final Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Tag.class)
                .add(Restrictions.eq("type", TagType.CONTENT))
                .add(Restrictions.isNotEmpty("videos"))
                .addOrder(Order.desc("hits"));
        if (limit > 0) {
            criteria.setMaxResults(limit);
        }
        final List<Tag> result = criteria.list();
        return result;
    }

    @Override
    public Tag find(String tagName) {
        final Tag result = (Tag) this.sessionFactory.getCurrentSession().getNamedQuery("Tag.findByname")
                .setString("tagname", tagName)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Tag> find(Tag tag) {
        return this.sessionFactory.getCurrentSession()
                .createCriteria(Tag.class)
                .add(Example.create(tag)
                        .excludeProperty("hits"))
                .list();
    }

    @Override
    protected void initializeLazy(Tag entity) {
        Hibernate.initialize(entity.getVideos());
        Hibernate.initialize(entity.getSutras());
    }

}
