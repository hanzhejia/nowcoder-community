package com.jiahz.community.util;

/**
 * EnumName: ActivationEnum
 *
 * @Author: jiahz
 * @Date: 2023/1/24 21:58
 * @Description:
 */
public enum ActivationEnum {
    ACTIVATION_SUCCESS(0),
    ACTIVATION_REPEAT(1),
    ACTIVATION_FAILURE(2);

    private int activationStatus;

    ActivationEnum(int activationStatus) {
        this.activationStatus = activationStatus;
    }

    public int getActivationStatus() {
        return activationStatus;
    }

}
