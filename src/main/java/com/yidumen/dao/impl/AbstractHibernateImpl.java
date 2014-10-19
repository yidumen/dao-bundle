package com.yidumen.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 * @param <T> 实体类
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateImpl<T> {

    protected SessionFactory sessionFactory;

    private final Class<T> entityClass;

    public AbstractHibernateImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        this.sessionFactory.getCurrentSession().persist(entity);
    }

    public void edit(T entity) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void remove(T entity) {
        this.sessionFactory.getCurrentSession().delete(entity);
    }

    public T find(Long id) {
        final T result = (T) this.sessionFactory.getCurrentSession().load(entityClass, id);
        return result;
    }

    public List<T> findAll() {
        final List<T> result = this.sessionFactory.getCurrentSession().createCriteria(entityClass).list();
        return result;
    }

    public List<T> findRange(int first, int size) {
        List<T> result = this.sessionFactory.getCurrentSession().createCriteria(entityClass)
                .setFirstResult(first)
                .setMaxResults(size)
                .list();
        initializeListLazy(result);
        return result;
    }

    protected void initializeListLazy(final List<T> list) throws HibernateException {
        for (T entity : list) {
            initializeLazy(entity);
        }
    }

    protected void initializeLazy(T entity) {
        // Do nothing because implement by child.
    }
;

}
