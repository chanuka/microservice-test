package com.cba.core.order.service;

import com.cba.core.order.dto.OrderLineItemsDto;
import com.cba.core.order.dto.OrderRequest;
import com.cba.core.order.dto.InventoryResponse;
import com.cba.core.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    public String placeOrder(OrderRequest orderRequest) {

        List<String> skuCodes = new ArrayList<>();
        skuCodes.add("001");
        skuCodes.add("002");
        skuCodes.add("003");

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/inventory/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(UUID.randomUUID().toString()));


        return "Success";
    }


    @Cacheable("orders")
    public List<OrderLineItemsDto> findAll() throws Exception {

        List<OrderLineItemsDto> list = new ArrayList<>();

        list.add(OrderLineItemsDto.builder().id(1l).price(new BigDecimal("25.23")).skuCode("003").quantity(10).build());
        list.add(OrderLineItemsDto.builder().id(2l).price(new BigDecimal("36.23")).skuCode("004").quantity(5).build());

        System.out.println("list method called instead cache");

        list.add(OrderLineItemsDto.builder().id(3l).price(new BigDecimal("96.23")).skuCode("005").quantity(9).build());
        list.add(OrderLineItemsDto.builder().id(4l).price(new BigDecimal("16.23")).skuCode("006").quantity(24).build());

        return list;
    }

}
