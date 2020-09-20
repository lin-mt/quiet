package com.gitee.quite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuiteRolePermission is a Querydsl query type for QuiteRolePermission
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuiteRolePermission extends EntityPathBase<QuiteRolePermission> {

    private static final long serialVersionUID = -1472818607L;

    public static final QQuiteRolePermission quiteRolePermission = new QQuiteRolePermission("quiteRolePermission");

    public final com.gitee.quite.base.QBaseEntity _super = new com.gitee.quite.base.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> creator = _super.creator;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtUpdate = _super.gmtUpdate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> permissionId = createNumber("permissionId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final NumberPath<Long> updater = _super.updater;

    public QQuiteRolePermission(String variable) {
        super(QuiteRolePermission.class, forVariable(variable));
    }

    public QQuiteRolePermission(Path<? extends QuiteRolePermission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiteRolePermission(PathMetadata metadata) {
        super(QuiteRolePermission.class, metadata);
    }

}

