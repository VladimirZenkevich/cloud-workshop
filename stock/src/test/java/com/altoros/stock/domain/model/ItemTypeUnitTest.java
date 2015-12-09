package com.altoros.stock.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by uladzimir.ziankevich on 12/9/2015.
 */
public class ItemTypeUnitTest {

    @Test
    public void testTypeByIncorrectName() {

        Assert.assertEquals(ItemType.UNKNOWN, ItemType.fromValue("Incorrect_name"));
    }

    @Test
    public void testTypeByCorrectName() {

        Assert.assertEquals(ItemType.ELECTRONICS, ItemType.fromValue("ELECTRONICS"));
    }

    @Test
    public void testTypeByIncorrectId() {

        Assert.assertEquals(ItemType.UNKNOWN, ItemType.fromId(-1L));
    }

    @Test
    public void testTypeByCorrectId() {

        Assert.assertEquals(ItemType.ELECTRONICS, ItemType.fromId(2L));
    }

}