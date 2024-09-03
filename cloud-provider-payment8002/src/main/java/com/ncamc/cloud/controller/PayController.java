package com.ncamc.cloud.controller;

import com.ncamc.cloud.Enum.ReturnCodeEnum;
import com.ncamc.cloud.entity.Pay;

import com.ncamc.cloud.entity.PayDTO;
import com.ncamc.cloud.resp.ResultData;
import com.ncamc.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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
    public ResultData<String> addPay(@RequestBody PayDTO payDTO){
        log.info(payDTO.toString());
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int i = payService.add(pay);
        return ResultData.success("成功插入记录，返回值:" +i);
    }

    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id){
        int i = payService.delete(id);
        return ResultData.success(i);
    }

    /**
     * DTO不能直接传到后端，需要转换成entity
     * @param payDTO
     * @return
     */
    @PutMapping("/pay/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        int i = payService.update(pay);
        return ResultData.success("成功更新记录，返回值: "+i);
    }

    @GetMapping("/pay/get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        if(id<=0) {
            throw new RuntimeException("id不能为负数");
        }
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping("/pay/getAll")
    @Operation(summary = "查所有流水",description = "查询全部流水方法")
    public ResultData<List<Pay>> getAll() {
        List<Pay> payList = payService.getAll();
        return ResultData.success(payList);
    }

    @GetMapping("/pay/error/{id}")
    public ResultData<Integer> getPayError(@PathVariable("id") Integer id){
        Integer integer = Integer.valueOf(200);
        try {
            System.out.println("pay error test");
            int age = 10/id;
        } catch (Exception e){
            e.printStackTrace();
            return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return ResultData.success(integer);
    }


    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/get/info")
    public String getInfoByConsul(@Value("${ncamc.info}") String info){
        return "info: " + info + "\t"+"port: " + port;
    }
}
