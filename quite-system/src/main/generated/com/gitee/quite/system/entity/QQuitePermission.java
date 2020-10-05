package com.gitee.quite.system.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuitePermission is a Querydsl query type for QuitePermission
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuitePermission extends EntityPathBase<QuitePermission> {

    private static final long serialVersionUID = 238058680L;

    public static final QQuitePermission quitePermission = new QQuitePermission("quitePermission");

    public final com.gitee.quite.system.base.QBaseEntity _super = new com.gitee.quite.system.base.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> creator = _super.creator;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtUpdate = _super.gmtUpdate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath postAuthorizeValue = createString("postAuthorizeValue");

    public final StringPath postFilterValue = createString("postFilterValue");

    public final StringPath preAuthorizeValue = createString("preAuthorizeValue");

    public final StringPath preFilterFilterTarget = createString("preFilterFilterTarget");

    public final StringPath preFilterValue = createString("preFilterValue");

    //inherited
    public final NumberPath<Long> updater = _super.updater;

    public final StringPath urlPattern = createString("urlPattern");

    public QQuitePermission(String variable) {
        super(QuitePermission.class, forVariable(variable));
    }

    public QQuitePermission(Path<? extends QuitePermission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuitePermission(PathMetadata metadata) {
        super(QuitePermission.class, metadata);
    }

}

