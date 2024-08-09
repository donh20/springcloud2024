package com.example.cloud.service;

import com.example.cloud.entities.Pay;

import java.util.List;

public interface PayService {
    int add(Pay pay);
    int delete(Integer id);
    int update(Pay pay);

    Pay getById(Integer id);
    public List<Pay> getAll();
}
