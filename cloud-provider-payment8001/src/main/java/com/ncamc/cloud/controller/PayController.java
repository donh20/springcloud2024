package com.ncamc.cloud.controller;

import com.ncamc.cloud.entities.Pay;
import com.ncamc.cloud.entities.PayDTO;
import com.ncamc.cloud.service.PayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping("/pay/add")
    public String addPay(@RequestBody Pay pay){
        log.info(pay.toString());
        int i = payService.add(pay);
        return "成功插入记录，返回值" +i;
    }

    @PostMapping("/pay/del/{id}")
    public int deletePay(@PathVariable("id") Integer id){
        return payService.delete(id);
    }

    /**
     * DTO不能直接传到后端，需要转换成entity
     * @param payDTO
     * @return
     */
    @PutMapping("/pay/update")
    public String updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int i = payService.update(pay);
        return "成功更新记录，返回值" + i;
    }

    @GetMapping("/pay/get/{id}")
    public Pay getById(@PathVariable("id") Integer id) {
        return payService.getById(id);
    }

    @GetMapping("/pay/getAll")
    public List<Pay> getAll() {
        return payService.getAll();
    }

}
