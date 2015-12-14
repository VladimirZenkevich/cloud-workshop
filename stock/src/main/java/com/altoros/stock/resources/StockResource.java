package com.altoros.stock.resources;

import com.altoros.stock.domain.model.StockItem;
import com.altoros.stock.domain.model.StockItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Rest endpoint to manage Stock
 * <p>
 * Created by uladzimir.ziankevich on 12/7/2015.
 */
@RestController
@RequestMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockResource.class);

    @Autowired
    private StockItemRepository stockItemRepository;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "Ping";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public StockItem stockItem(@PathVariable("id") Long id, HttpServletResponse response) {
        LOGGER.info("!!!!!! Starting stock item(id={}) search", id);

        StockItem stockItem = stockItemRepository.findOne(id);

        if (stockItem == null) {
            LOGGER.info("!!!!!! Stock item(id={}) has not been found", id);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        LOGGER.info("!!!!!! Finishing stock item(id={}) search", id);
        return stockItem;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public StockItem storeInStock(@RequestBody StockItem stockItem, HttpServletResponse response) {
        LOGGER.info("!!!!!! Starting stock item({}) creation", stockItem);
        StockItem storedItem = stockItemRepository.save(stockItem);

        response.setStatus(HttpStatus.CREATED.value());

        LOGGER.info("!!!!!! Finishing stock item({}) creation", stockItem);
        return storedItem;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public StockItem update(@RequestBody StockItem stockItem, HttpServletResponse response) {
        LOGGER.info("!!!!!! Starting stock item({}) update", stockItem);

        if (!stockItemRepository.exists(stockItem.getId())) {
            LOGGER.info("!!!!!! Stock item({}) for update has not been found", stockItem);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return stockItem;
        }

        LOGGER.info("!!!!!! Starting stock item({}) update", stockItem);
        return stockItemRepository.save(stockItem);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeFromStock(@PathVariable("id") Long id, HttpServletResponse response) {
        LOGGER.info("!!!!!! Starting stock item(id={}) deletion", id);

        StockItem stockItemToRemove = stockItemRepository.findOne(id);

        if (stockItemToRemove == null) {
            LOGGER.info("!!!!!! Stock item(id={}) for deletion has not been found", id);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        LOGGER.info("!!!!!! Finishing stock item(id={}) deletion", id);
        stockItemRepository.delete(stockItemToRemove);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<StockItem> items() {
        LOGGER.info("!!!!!! Starting stock items search");

        Iterable<StockItem> items = stockItemRepository.findAll();
        LOGGER.info("!!!!!! Finishing stock items search");

        return items;
    }

}

