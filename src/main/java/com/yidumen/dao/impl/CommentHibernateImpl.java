package com.yidumen.dao.impl;

import com.yidumen.dao.CommentDAO;
import com.yidumen.dao.entity.Comment;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class CommentHibernateImpl extends AbstractHibernateImpl<Comment> implements CommentDAO {

    public CommentHibernateImpl(SessionFactory sessionFactory) {
        super(Comment.class);
        this.sessionFactory = sessionFactory;
    }

}
