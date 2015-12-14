package com.altoros.catalog.controllers;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by uladzimir.ziankevich on 12/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerUnitTest {

    @Mock
    private DiscoveryClient discoveryClientMock;
    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private HomeController homeController = new HomeController();

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders
                .standaloneSetup(homeController)
                .setViewResolvers(resolver)
                .build();
    }


    @Test
    public void testHomePage() throws Exception {

        StockItemDTO stockItemDTO = new StockItemDTO();
        stockItemDTO.setId(1L);
        stockItemDTO.setTitle("title_1");

        when(discoveryClientMock.getInstances("stock-service"))
                .thenReturn(Lists.newArrayList(buildDefaultStockServiceInstance()));
        when(restTemplateMock.getForObject("http://localhost:8080/stock", List.class))
                .thenReturn(Lists.newArrayList(buildStockResponseBody()));

        mockMvc.perform(get("/catalog/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.model().attribute("stockItems", Lists.newArrayList(stockItemDTO)))
                .andReturn();
    }

    private ServiceInstance buildDefaultStockServiceInstance() {
        return new DefaultServiceInstance("stock-service", "localhost", 8080, false);
    }

    private LinkedHashMap<String, Object> buildStockResponseBody() {
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();

        body.put("id", 1L);
        body.put("title", "title_1");
        body.put("type", "item_type");
        body.put("description", "description_1");

        return body;
    }

}