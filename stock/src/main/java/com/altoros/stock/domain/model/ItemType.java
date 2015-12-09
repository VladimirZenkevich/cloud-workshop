package com.altoros.stock.domain.model;

/**
 * Created by uladzimir.ziankevich on 12/9/2015.
 */
public enum ItemType {

    UNKNOWN(1L),
    ELECTRONICS(2L),
    CD(3L);

    Long id;

    ItemType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static ItemType fromValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }

    public static ItemType fromId(Long id) {

        for (ItemType type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }

        return UNKNOWN;
    }
}
