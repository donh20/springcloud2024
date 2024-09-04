package com.ncamc.cloud.controller;

import com.ncamc.cloud.api.PayFeignApi;
import com.ncamc.cloud.entity.PayDTO;
import com.ncamc.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        System.out.println("第一步：模拟本地addOrder新增订单成功(省略sql操作)，第二步：再开启addPay支付微服务远程调用");
        ResultData<String> resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = payFeignApi.getById(id);
        return resultData;
    }
    /**
     * openfeign天然支持负载均衡演示
     *
     * @return
     */
    @GetMapping("/feign/pay/mylb")
    public String mylb(){
        return payFeignApi.mylb();
    }
}
