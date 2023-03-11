package com.example.socialnetworkgui.domain;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {
    private ID id;
    private static final long serializableUID = 99999999L;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
