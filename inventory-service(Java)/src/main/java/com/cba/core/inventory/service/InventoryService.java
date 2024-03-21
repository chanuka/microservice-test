package com.cba.core.inventory.service;

import com.cba.core.inventory.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        Thread.sleep(10000);
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        inventoryResponses.add(InventoryResponse.builder().skuCode("001").isInStock(true).build());
        inventoryResponses.add(InventoryResponse.builder().skuCode("002").isInStock(true).build());
        inventoryResponses.add(InventoryResponse.builder().skuCode("003").isInStock(false).build());
        return inventoryResponses;
    }
}
