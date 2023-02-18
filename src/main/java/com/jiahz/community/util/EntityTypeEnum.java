package com.jiahz.community.util;

/**
 * EnumName: ActivationEnum
 *
 * @Author: jiahz
 * @Date: 2023/1/24 21:58
 * @Description:
 */
public enum EntityTypeEnum {
    ENTITY_TYPE_POST(1),
    ENTITY_TYPE_COMMENT(2);

    private int entityType;

    EntityTypeEnum(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityType() {
        return entityType;
    }

}
