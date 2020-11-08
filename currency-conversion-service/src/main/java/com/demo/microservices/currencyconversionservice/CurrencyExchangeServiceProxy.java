package com.demo.microservices.currencyconversionservice;


import com.demo.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//before configuring the naming server
//@FeignClient(name = "currency-exchange-service", url = "http://localhost:8000")
//after configuring naming server
//@FeignClient(name = "currency-exchange-service")
//once u configure zuul we need to redirect request through zuul api
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

   // @GetMapping("/currency-exchange/from/{from}/to/{to}")
   @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retriveExchangeValue(@PathVariable String from, @PathVariable String to);

}
