package com.ncamc.cloud.controller;

import com.ncamc.cloud.entities.Pay;
import com.ncamc.cloud.entities.PayDTO;
import com.ncamc.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "支付微服务模块", description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping("/pay/add")
    @Operation(summary = "新增",description = "新增支付流水方法,json串做参数")
    public String addPay(@RequestBody Pay pay){
        log.info(pay.toString());
        int i = payService.add(pay);
        return "成功插入记录，返回值" +i;
    }

    @PostMapping("/pay/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public int deletePay(@PathVariable("id") Integer id){
        return payService.delete(id);
    }

    /**
     * DTO不能直接传到后端，需要转换成entity
     * @param payDTO
     * @return
     */
    @PutMapping("/pay/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public String updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int i = payService.update(pay);
        return "成功更新记录，返回值" + i;
    }

    @GetMapping("/pay/get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public Pay getById(@PathVariable("id") Integer id) {
        return payService.getById(id);
    }

    @GetMapping("/pay/getAll")
    @Operation(summary = "查所有流水",description = "查询全部流水方法")
    public List<Pay> getAll() {
        return payService.getAll();
    }

}
