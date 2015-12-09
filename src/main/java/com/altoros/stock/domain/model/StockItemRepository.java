package com.altoros.stock.domain.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by uladzimir.ziankevich on 12/7/2015.
 */
@Repository
public interface StockItemRepository extends CrudRepository<StockItem, Long> {

    List<StockItem> findByTitle(String title);

    List<StockItem> findByType(ItemType type);
}
