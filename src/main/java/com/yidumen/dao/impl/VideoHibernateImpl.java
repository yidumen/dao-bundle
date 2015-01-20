package com.yidumen.dao.impl;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class VideoHibernateImpl extends AbstractHibernateImpl<Video> implements VideoDAO, Serializable {

    public VideoHibernateImpl(SessionFactory sessionFactory) {
        super(Video.class);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Video> find(final VideoStatus videoStatus) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.findByStatus")
                .setParameter(0, videoStatus)
                .list();
        return result;
    }

    @Override
    public List<Video> getNewVideos(final int limit) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.findNew")
                .setMaxResults(limit).list();
        return result;
    }

    @Override
    public List<Video> findRecommend() {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.findRecommend").list();
        return result;
    }

    @Override
    public List dateGroup() {
        final List result = this.sessionFactory.getCurrentSession().getNamedQuery("video.dateGroup").list();
        return result;
    }

    @Override
    public List<Video> find(final Date startTime, final Date endTime) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.findBetween")
                .setDate(0, new java.sql.Date(startTime.getTime()))
                .setDate(1, new java.sql.Date(endTime.getTime()))
                .list();
        return result;
    }

    @Override
    public Video find(final String file) {
        final Video result = (Video) this.sessionFactory.getCurrentSession().getNamedQuery("video.findByFile")
                .setString("file", file)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList")
                .setDate(0, shootTime)
                .setString(1, file)
                .list();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file, final int limit) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList2")
                .setDate(0, shootTime)
                .setString(1, file)
                .setMaxResults(limit)
                .list();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final int limit) {
        final List<Video> result = this.sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList3")
                .setDate(0, shootTime)
                .setMaxResults(limit)
                .list();
        return result;
    }

    @Override
    public List<Video> find(VideoQueryModel model) {
        final Criteria criteria = createCriteria(model);
        final List<Video> result = criteria.list();
        if (model.isAllEager()) {
            initializeListLazy(result);
        }
        return result;
    }

    @Override
    public Long count(VideoQueryModel model) {
        final Criteria criteria = createCriteria(model);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private Criteria createCriteria(VideoQueryModel model) throws HibernateException {
        final Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Video.class);
        if (model.getSort2() > 0) {
            criteria.add(Restrictions.between("sort", model.getSort(), model.getSort2()));
        }
        if (model.getTitle() != null && !model.getTitle().isEmpty()) {
            criteria.add(Restrictions.like("title", "%" + model.getTitle() + "%"));
        }
        if (model.getFile() != null && !model.getFile().isEmpty()) {
            criteria.add(Restrictions.between("file", model.getFile(), model.getFile2()));
        }
        if (model.getExtInfo() != null && !model.getExtInfo().isEmpty()) {
            criteria.createAlias("extInfo", "ext");
            for (VideoInfo info : model.getExtInfo()) {
                for (VideoInfo info2 : model.getExtInfo2()) {
                    if (!info.getResolution().equals(info2.getResolution())) {
                        continue;
                    }
                    criteria.add(Restrictions.eq("ext.resolution", info.getResolution()))
                            .add(Restrictions.between("ext.fileSize", info.getFileSize(), info2.getFileSize()));
                }
            }
        }
        if (model.getTags() != null && !model.getTags().isEmpty()) {
            criteria.createAlias("tags", "tag");
            for (Tag tag : model.getTags()) {
                if (tag.getTagname() != null) {
                    criteria.add(Restrictions.eq("tag.tagname", tag.getTagname()));
                }
                if (tag.getType() != null) {
                    criteria.add(Restrictions.eq("tag.type", tag.getType()));
                }
            }
        }
        if (model.getDescrpition() != null && !model.getDescrpition().isEmpty()) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getDescrpition() + "%"));
        }
        if (model.getNote() != null && !model.getNote().isEmpty()) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getNote() + "%"));
        }
        if (model.getGrade() != null && !model.getGrade().isEmpty()) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getGrade() + "%"));
        }
        if (model.getShootTime() != null) {
            criteria.add(Restrictions.between("shootTime", model.getShootTime(), model.getShootTime2()));
        }
        if (model.getStatus2() != null) {
            Criterion[] restrictionses = new Criterion[model.getStatus2().size()];
            for (int i = 0; i < restrictionses.length; i++) {
                VideoStatus status = model.getStatus2().get(i);
                restrictionses[i] = Restrictions.eq("status", status);
            }
            criteria.add(Restrictions.or(restrictionses));
        }
        if (model.isRecommendVideo()) {
            criteria.add(Restrictions.between("recommend", model.getRecommend(), model.getRecommend2()));
        }
        if (model.getPubDate() != null) {
            criteria.add(Restrictions.between("pubDate", model.getPubDate(), model.getPubDate2()));
        }
        if (model.getComment2() != null && !model.getComment2().isEmpty()) {
            criteria.createAlias("comments", "comment")
                    .add(Restrictions.like("comment.content", "%" + model.getComment2() + "%"));
        }
        if (model.getOrderProperty() != null && !model.getOrderProperty().isEmpty()) {
            if (model.isDesc()) {
                criteria.addOrder(Order.desc(model.getOrderProperty()));
            } else {
                criteria.addOrder(Order.asc(model.getOrderProperty()));
            }
        }
        if (model.getFirst() > 0) {
            criteria.setFirstResult(model.getFirst());
        }

        if (model.getLimit() > 0) {
            criteria.setMaxResults(new Long(model.getLimit()).intValue());
        }
        
        return criteria;
    }

    @Override
    protected void initializeLazy(final Video video) throws HibernateException {
        Hibernate.initialize(video.getExtInfo());
        Hibernate.initialize(video.getTags());
        Hibernate.initialize(video.getComments());
    }

}
