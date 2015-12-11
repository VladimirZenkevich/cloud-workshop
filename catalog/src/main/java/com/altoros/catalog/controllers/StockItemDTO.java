package com.altoros.catalog.controllers;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by uladzimir.ziankevich on 12/11/2015.
 */
public class StockItemDTO {

    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemDTO that = (StockItemDTO) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getTitle(), that.getTitle())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getTitle())
                .toHashCode();
    }
}
