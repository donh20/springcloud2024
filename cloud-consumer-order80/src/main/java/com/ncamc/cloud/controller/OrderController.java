package com.ncamc.cloud.controller;

import com.ncamc.cloud.entity.PayDTO;
import com.ncamc.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @GetMapping("/consumer/pay/get/info")
    private String getInfoByConsul(){
        return restTemplate.getForObject(Payment_URL+"/pay/get/info",String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }
}
