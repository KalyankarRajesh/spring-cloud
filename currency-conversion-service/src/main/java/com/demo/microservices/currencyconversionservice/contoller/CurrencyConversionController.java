package com.demo.microservices.currencyconversionservice.contoller;


import com.demo.microservices.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.demo.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {


        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class, uriVariables);
        CurrencyConversionBean currencyConversionBean = responseEntity.getBody();
        return new CurrencyConversionBean(currencyConversionBean.getId(), from, to, currencyConversionBean.getConversionMultiple(),
                quantity, quantity.multiply(currencyConversionBean.getConversionMultiple()), currencyConversionBean.getPort());

    }
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionBean currencyConversionBean = currencyExchangeServiceProxy.retriveExchangeValue(from,to);
logger.info("{}",currencyConversionBean);
        return new CurrencyConversionBean(currencyConversionBean.getId(), from, to, currencyConversionBean.getConversionMultiple(),
                quantity, quantity.multiply(currencyConversionBean.getConversionMultiple()), currencyConversionBean.getPort());

    }
}
