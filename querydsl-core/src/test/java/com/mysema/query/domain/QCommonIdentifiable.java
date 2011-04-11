package com.mysema.query.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;


/**
 * QCommonIdentifiable is a Querydsl query type for CommonIdentifiable
 */
public class QCommonIdentifiable extends EntityPathBase<CommonIdentifiable<? extends java.io.Serializable>> {

    private static final long serialVersionUID = 1818647030;

    public static final QCommonIdentifiable commonIdentifiable = new QCommonIdentifiable("commonIdentifiable");

    public final QCommonPersistence _super = new QCommonPersistence(this);

    public final SimplePath<java.io.Serializable> id = createSimple("id", java.io.Serializable.class);

    //inherited
    public final NumberPath<Long> version = _super.version;

    @SuppressWarnings("unchecked")
    public QCommonIdentifiable(String variable) {
        super((Class)CommonIdentifiable.class, forVariable(variable));
    }

    public QCommonIdentifiable(BeanPath<? extends CommonIdentifiable<? extends java.io.Serializable>> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    @SuppressWarnings("unchecked")
    public QCommonIdentifiable(PathMetadata<?> metadata) {
        super((Class)CommonIdentifiable.class, metadata);
    }

}

