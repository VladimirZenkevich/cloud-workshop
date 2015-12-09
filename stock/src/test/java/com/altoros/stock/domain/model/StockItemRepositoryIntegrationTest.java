package com.altoros.stock.domain.model;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by uladzimir.ziankevich on 12/8/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DomainTestConfig.class})
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration
public class StockItemRepositoryIntegrationTest {

    @Autowired
    private StockItemRepository stockItemRepository;


    @Test
    public void testStockItemOfIdNotExists() throws Exception {

        StockItem stockItem = stockItemRepository.findOne(1L);

        Assert.assertNull(stockItem);
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "StockItemRepositoryIntegrationTest-testStockItemOfIdExists.xml")
    public void testStockItemOfIdExists() throws Exception {

        StockItem stockItem = stockItemRepository.findOne(1L);

        Assert.assertNotNull(stockItem);
        Assert.assertEquals(stockItem.getId(), new Long(1L));
        Assert.assertEquals(stockItem.getTitle(), "title_1");
        Assert.assertEquals(stockItem.getDescription(), "description_1");
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "StockItemRepositoryIntegrationTest-testStockItemOfIdExists.xml")
    public void testStockItemByTitle() throws Exception {

        List<StockItem> items = stockItemRepository.findByTitle("title");

        Assert.assertEquals(0, items.size());

        items = stockItemRepository.findByTitle("title_1");

        Assert.assertEquals(1, items.size());
        Assert.assertEquals(items.get(0).getId(), new Long(1L));
        Assert.assertEquals(items.get(0).getTitle(), "title_1");
        Assert.assertEquals(items.get(0).getDescription(), "description_1");
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "StockItemRepositoryIntegrationTest-testByType.xml")
    public void testStockItemsByType() {

        List<StockItem> cds = stockItemRepository.findByType(ItemType.CD);

        Assert.assertEquals(2, cds.size());
    }

}