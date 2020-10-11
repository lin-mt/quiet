package com.gitee.quite.system.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuiteUser is a Querydsl query type for QuiteUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuiteUser extends EntityPathBase<QuiteUser> {

    private static final long serialVersionUID = 497763700L;

    public static final QQuiteUser quiteUser = new QQuiteUser("quiteUser");

    public final com.gitee.quite.system.base.QBaseEntity _super = new com.gitee.quite.system.base.QBaseEntity(this);

    public final EnumPath<com.gitee.quite.system.enums.Whether> accountExpired = createEnum("accountExpired", com.gitee.quite.system.enums.Whether.class);

    public final EnumPath<com.gitee.quite.system.enums.Whether> accountLocked = createEnum("accountLocked", com.gitee.quite.system.enums.Whether.class);

    public final StringPath avatar = createString("avatar");

    //inherited
    public final NumberPath<Long> creator = _super.creator;

    public final EnumPath<com.gitee.quite.system.enums.Whether> credentialsExpired = createEnum("credentialsExpired", com.gitee.quite.system.enums.Whether.class);

    public final StringPath emailAddress = createString("emailAddress");

    public final EnumPath<com.gitee.quite.system.enums.Whether> enabled = createEnum("enabled", com.gitee.quite.system.enums.Whether.class);

    public final EnumPath<com.gitee.quite.system.enums.Gender> gender = createEnum("gender", com.gitee.quite.system.enums.Gender.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> gmtUpdate = _super.gmtUpdate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath secretCode = createString("secretCode");

    //inherited
    public final NumberPath<Long> updater = _super.updater;

    public final StringPath username = createString("username");

    public QQuiteUser(String variable) {
        super(QuiteUser.class, forVariable(variable));
    }

    public QQuiteUser(Path<? extends QuiteUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiteUser(PathMetadata metadata) {
        super(QuiteUser.class, metadata);
    }

}

