package com.yidumen.dao.test;

import com.yidumen.dao.framework.HibernateUtil;
import org.hamcrest.core.Is;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 蔡迪旻
 */
public class AceSecondCacheTest {
    private Session session;
    public AceSecondCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
    }

    @After
    public void tearDown() {
        session.getTransaction().commit();
    }

     @Test
     public void hello() {
         System.out.println("------第一次读取------");
         assertThat(true, Is.is(true));
     }
}
