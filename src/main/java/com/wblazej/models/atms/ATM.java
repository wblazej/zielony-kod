package com.wblazej.models.atms;

import com.alibaba.fastjson2.annotation.JSONField;

public class ATM {
    public int atmId;
    private final Priorities priority;
    public int region;

    ATM(int atmId, String requestType, int region) {
        this.atmId = atmId;
        this.region = region;
        this.priority = Priorities.valueOf(requestType);
    }

    @JSONField(serialize = false)
    public int getPriority() {
        return this.priority.ordinal();
    }

    public int getRegion() {
        return this.region;
    }
}
