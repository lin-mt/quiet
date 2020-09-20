package com.gitee.quite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuiteRole is a Querydsl query type for QuiteRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuiteRole extends EntityPathBase<QuiteRole> {

    private static final long serialVersionUID = 1660658402L;

    public static final QQuiteRole quiteRole = new QQuiteRole("quiteRole");

    public final com.gitee.quite.base.QBaseEntity _super = new com.gitee.quite.base.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> creator = _super.creator;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtUpdate = _super.gmtUpdate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath remarks = createString("remarks");

    public final StringPath roleCnName = createString("roleCnName");

    public final StringPath roleName = createString("roleName");

    //inherited
    public final NumberPath<Long> updater = _super.updater;

    public QQuiteRole(String variable) {
        super(QuiteRole.class, forVariable(variable));
    }

    public QQuiteRole(Path<? extends QuiteRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiteRole(PathMetadata metadata) {
        super(QuiteRole.class, metadata);
    }

}

