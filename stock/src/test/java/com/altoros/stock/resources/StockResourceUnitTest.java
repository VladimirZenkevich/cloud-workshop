package com.altoros.stock.resources;

import com.altoros.stock.domain.model.ItemType;
import com.altoros.stock.domain.model.StockItem;
import com.altoros.stock.domain.model.StockItemRepository;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by uladzimir.ziankevich on 12/7/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class StockResourceUnitTest {

    @Mock
    private StockItemRepository stockItemRepositoryMock;
    @InjectMocks
    private StockResource stockResource = new StockResource();

    private MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(stockResource)
            .setHandlerExceptionResolvers(new DefaultHandlerExceptionResolver())
            .build();


    @Test
    public void testPing() throws Exception {

        mockMvc.perform(get("/stock/ping"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Ping"));
    }

    @Test
    public void testStockItemAvailable() throws Exception {

        Mockito.when(stockItemRepositoryMock.findOne(1L)).thenReturn(buildSockItem());

        mockMvc.perform(get("/stock/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Phone")))
                .andExpect(jsonPath("$.description", is("Mobile Phone")))
        ;

        Mockito.verify(stockItemRepositoryMock).findOne(1L);
    }

    @Test
    public void testStockItemNotAvailable() throws Exception {

        mockMvc.perform(get("/stock/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateNewStockItem() throws Exception {

        StockItem stockItem = buildSockItem(null, "sample_title", "sample_description");

        Mockito
                .when(stockItemRepositoryMock.save(stockItem))
                .thenReturn(buildSockItem(1L, "sample_title", "sample_description"));

        mockMvc.perform(
                post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"UNKNOWN\",\"title\":\"sample_title\",\"description\":\"sample_description\"}")
        )
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("sample_title")))
                .andExpect(jsonPath("$.description", is("sample_description")));

        Mockito.verify(stockItemRepositoryMock).save(stockItem);
    }

    @Test
    public void testNewStockItemNotCreated() throws Exception {

        StockItem stockItem = buildSockItem(null, "sample_title", "sample_description");

        Mockito
                .when(stockItemRepositoryMock.save(stockItem))
                .thenThrow(new HttpMessageNotWritableException("Test Exception"));

        mockMvc.perform(
                post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"UNKNOWN\",\"title\":\"sample_title\",\"description\":\"sample_description\"}")
        )
                .andExpect(status().is(not(HttpStatus.CREATED.value())));

        Mockito.verify(stockItemRepositoryMock).save(stockItem);
    }

    @Test
    public void testUpdateStockItem() throws Exception {

        StockItem stockItem = buildSockItem(1L, "sample_title_new", "sample_description_new");

        Mockito.when(stockItemRepositoryMock.exists(stockItem.getId())).thenReturn(true);
        Mockito.when(stockItemRepositoryMock.save(stockItem)).thenReturn(stockItem);

        mockMvc.perform(
                put("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"type\":\"UNKNOWN\",\"title\":\"sample_title_new\",\"description\":\"sample_description_new\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("sample_title_new")))
                .andExpect(jsonPath("$.description", is("sample_description_new")));

        Mockito.verify(stockItemRepositoryMock).save(stockItem);
    }

    @Test
    public void testUpdateStockItemNotExists() throws Exception {

        Mockito.when(stockItemRepositoryMock.exists(1L)).thenReturn(false);

        mockMvc.perform(
                put("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"title\":\"sample_title_new\",\"description\":\"sample_description_new\"}")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoveStockItem() throws Exception {

        Mockito.when(stockItemRepositoryMock.findOne(1L)).thenReturn(buildSockItem());

        mockMvc.perform(delete("/stock/{id}", 1L))
                .andExpect(status().isOk());

    }

    @Test
    public void testRemoveStockItemNotFound() throws Exception {

        mockMvc.perform(delete("/stock/{id}", 1L))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetAllItems() throws Exception {

        Mockito.when(stockItemRepositoryMock.findAll())
                .thenReturn(
                        Lists.newArrayList(
                                buildSockItem(1L, "title1", "description1")
                                , buildSockItem(2L, "title2", "description2")
                        )
                );

        MvcResult result = mockMvc.perform(get("/stock", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assert.assertEquals(
                result.getResponse().getContentAsString(),
                "[{\"id\":1,\"type\":\"UNKNOWN\",\"title\":\"title1\",\"description\":\"description1\"},"
                        + "{\"id\":2,\"type\":\"UNKNOWN\",\"title\":\"title2\",\"description\":\"description2\"}]"
        );
    }

    private StockItem buildSockItem() {
        return buildSockItem(1L, "Phone", "Mobile Phone");
    }

    private StockItem buildSockItem(Long id, String title, String description) {
        return new StockItem(id, ItemType.UNKNOWN, title, description);
    }

}