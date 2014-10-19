package com.yidumen.dao.impl;

import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.entity.Sutra;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class SutraHibernateImpl extends AbstractHibernateImpl<Sutra> implements SutraDAO {

    public SutraHibernateImpl(SessionFactory sessionFactory) {
        super(Sutra.class);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Sutra> find(final long leftValue, final long rightValue) {
        final List<Sutra> result = this.sessionFactory.getCurrentSession().getNamedQuery("Sutra.findNodes")
                .setLong("leftValue", leftValue)
                .setLong("rightValue", rightValue)
                .list();
        return result;
    }

    @Override
    public Sutra findByLeftvalue(final long leftValue) {
        final Sutra result = (Sutra) this.sessionFactory.getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, leftValue)
                .uniqueResult();
        return result;
    }

    @Override
    public Sutra findByRightvalue(final long rightValue) {
        final Sutra result = (Sutra) this.sessionFactory.getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, rightValue)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Sutra> findParents(final Sutra sutra) {
        final List<Sutra> result = this.sessionFactory.getCurrentSession().getNamedQuery("Sutra.findParents")
                .setLong(1, sutra.getLeftValue())
                .setLong(2, sutra.getRightValue())
                .list();
        return result;
    }

    @Override
    public Sutra find(String title) {
        return (Sutra) this.sessionFactory.getCurrentSession().createCriteria(Sutra.class)
                .add(Restrictions.eq("title", title)).uniqueResult();
    }

    @Override
    protected void initializeLazy(Sutra entity) {
        Hibernate.initialize(entity.getTags());
        Hibernate.initialize(entity.getContent());
    }

}
