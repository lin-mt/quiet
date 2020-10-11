package com.gitee.quite.system.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuiteUserRole is a Querydsl query type for QuiteUserRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuiteUserRole extends EntityPathBase<QuiteUserRole> {

    private static final long serialVersionUID = 587882506L;

    public static final QQuiteUserRole quiteUserRole = new QQuiteUserRole("quiteUserRole");

    public final com.gitee.quite.common.service.base.QBaseEntity _super = new com.gitee.quite.common.service.base.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> creator = _super.creator;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtUpdate = _super.gmtUpdate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final NumberPath<Long> updater = _super.updater;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QQuiteUserRole(String variable) {
        super(QuiteUserRole.class, forVariable(variable));
    }

    public QQuiteUserRole(Path<? extends QuiteUserRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiteUserRole(PathMetadata metadata) {
        super(QuiteUserRole.class, metadata);
    }

}

