package com.yidumen.dao.impl;

import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.entity.Account;
import java.io.Serializable;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccountHibernateImpl extends AbstractHibernateImpl<Account> implements AccountDAO,Serializable {

    public AccountHibernateImpl(SessionFactory sessionFactory) {
        super(Account.class);
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Account find(String emailOrPhone) {
        final Account result = (Account) this.sessionFactory.getCurrentSession().getNamedQuery("Account.findByName")
                .setString("username", emailOrPhone)
                .uniqueResult();
        initializeLazy(result);
        return result;
    }

    @Override
    protected void initializeLazy(Account entity) {
        Hibernate.initialize(entity.getAgreed());
        Hibernate.initialize(entity.getAccessInfo());
        Hibernate.initialize(entity.getReceivedMessages());
        Hibernate.initialize(entity.getSendedMessages());
        Hibernate.initialize(entity.getVerifyInfo());
    }
    
    
}
