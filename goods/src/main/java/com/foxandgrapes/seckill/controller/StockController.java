package com.foxandgrapes.seckill.controller;

import com.foxandgrapes.seckill.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private IStockService stockService;


}
