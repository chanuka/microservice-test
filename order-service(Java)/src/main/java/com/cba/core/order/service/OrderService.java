package com.cba.core.order.service;

import com.cba.core.order.dto.OrderRequest;
import com.cba.core.order.dto.InventoryResponse;
import com.cba.core.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

}
