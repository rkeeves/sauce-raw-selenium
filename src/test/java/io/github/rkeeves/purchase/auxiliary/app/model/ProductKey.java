package io.github.rkeeves.purchase.auxiliary.app.model;

import lombok.Getter;

public enum ProductKey {

    LIGHT(0),
    BOLT_SHIRT(1),
    ONESIE(2),
    TATT(3),
    BACKPACK(4),
    JACKET(5);

    @Getter
    private final int id;

    ProductKey(int id) {
        this.id = id;
    }
}
