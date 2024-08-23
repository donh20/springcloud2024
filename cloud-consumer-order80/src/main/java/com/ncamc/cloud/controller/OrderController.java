package com.ncamc.cloud.controller;

import com.ncamc.cloud.entity.PayDTO;
import com.ncamc.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    /**
     * url,requestMap,reseponseEntity
     */
//    public static final String Payment_URL = "http://localhost:8001";//硬编码写死
    public static final String Payment_URL = "http://cloud-payment-service";//服务注册中心上的微服务名称

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(Payment_URL+"/pay/add", payDTO, ResultData.class);
    }

    @DeleteMapping("/consumer/pay/del/{id}")
    public ResultData deleteOrder(@PathVariable("id") Integer id){
        restTemplate.delete(Payment_URL + "/pay/del/{id}",id);
        return ResultData.success(id);
    }

    @PutMapping("/consumer/pay/update")
    public ResultData updateOrder(@RequestBody PayDTO payDTO){
        restTemplate.put(Payment_URL+"/pay/update", payDTO);
        return ResultData.success(payDTO.getId());
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(Payment_URL + "/pay/get/" + id, ResultData.class, id);
    }

}
