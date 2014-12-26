package com.yidumen.dao;

import com.yidumen.dao.entity.Tag;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface TagDAO extends BaseDAO<Tag> {

    Tag find(String tagName);

    List<Tag> findTags(int limit);

    List<Tag> findVideoTags(final int limit);

    List<Tag> find(Tag tag);
    
}
