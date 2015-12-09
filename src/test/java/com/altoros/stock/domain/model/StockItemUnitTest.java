package com.altoros.stock.domain.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by uladzimir.ziankevich on 12/9/2015.
 */
public class StockItemUnitTest {

    @Test
    public void testNotEquals() throws Exception {

        StockItem item1 = new StockItem(1L, ItemType.CD, "title", "description");
        StockItem item2 = new StockItem(2L, ItemType.CD, "title", "description");

        Assert.assertNotEquals(item1, item2);

        item2 = new StockItem(1L, ItemType.UNKNOWN, "title", "description");
        Assert.assertNotEquals(item1, item2);

        item2 = new StockItem(1L, ItemType.CD, "title_2", "description");
        Assert.assertNotEquals(item1, item2);

        item2 = new StockItem(1L, ItemType.CD, "title", "description_2");
        Assert.assertNotEquals(item1, item2);
    }

    @Test
    public void testEquals() throws Exception {

        StockItem item1 = new StockItem(1L, ItemType.CD, "title", "description");
        StockItem item2 = item1;

        Assert.assertEquals(item1, item2);

        Assert.assertNotEquals(item1, null);


        item1 = new StockItem(1L, ItemType.CD, "title", "description");
        item2 = new StockItem(1L, ItemType.CD, "title", "description");

        Assert.assertEquals(item1, item2);
    }
}