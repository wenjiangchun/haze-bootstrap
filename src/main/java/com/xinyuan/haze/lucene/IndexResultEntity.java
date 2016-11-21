package com.xinyuan.haze.lucene;

import com.xinyuan.haze.core.jpa.entity.BaseEntity;

/**
 * Created by Sofar on 2016/10/20.
 */
public class IndexResultEntity {

    private String id;

    private  Class<? extends BaseEntity> baseEntity;

    private String name;

    private String filePath;

    private String fileName;

}
