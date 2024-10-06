package com.task.penta.entity;

import lombok.Getter;

@Getter
public enum ActionTypeEnum {
    create("C"),
    update("U"),
    delete("D");

    private final String actionType;

    ActionTypeEnum(String actionType) {
        this.actionType = actionType;
    }
}
