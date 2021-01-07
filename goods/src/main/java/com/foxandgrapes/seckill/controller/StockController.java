package com.foxandgrapes.seckill.controller;

import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StockController {

    @Autowired
    private IStockService stockService;

    @RequestMapping("/updateStock/{goodsId}/{inputStock}/{outputStock}")
    public Map<String, Object> updateStock(@PathVariable("goodsId") Long goodsId,
                                           @PathVariable("inputStock") Integer inputStock,
                                           @PathVariable("outputStock") Integer outputStock) {
        return stockService.updateStock(goodsId, inputStock, outputStock);
    }
}
