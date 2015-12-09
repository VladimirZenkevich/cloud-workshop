package com.altoros.stock.domain.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by uladzimir.ziankevich on 12/7/2015.
 */
@Entity
@Table(name = "stock_item")
public class StockItem {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;


    public StockItem(Long id, ItemType type, String title, String description) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
    }

    public StockItem() {
    }

    public Long getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItem stockItem = (StockItem) o;

        return new EqualsBuilder()
                .append(this.getId(), stockItem.getId())
                .append(this.getType(), stockItem.getType())
                .append(this.getTitle(), stockItem.getTitle())
                .append(this.getDescription(), stockItem.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getTitle())
                .append(getDescription())
                .toHashCode();
    }
}
