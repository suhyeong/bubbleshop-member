package com.bubbleshop.member.domain.service;

import com.bubbleshop.member.domain.model.view.ProductView;
import com.bubbleshop.member.domain.repository.ProductRepository;
import com.bubbleshop.member.infrastructure.dto.GetProductDetailRspDTO;
import com.bubbleshop.member.infrastructure.feign.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductService implements ProductRepository {
    private final ProductFeignClient productFeignClient;

    @Override
    public Map<String, ProductView> getProductList(Collection<String> productIds) {
        Map<String, GetProductDetailRspDTO> result = productFeignClient.getProductDetail(productIds);

        Map<String, ProductView> value = new HashMap<>();
        if(Objects.isNull(result) || result.isEmpty())
            return value;

        for(Map.Entry<String, GetProductDetailRspDTO> data : result.entrySet()) {
            value.put(data.getKey(), new ProductView(data.getValue()));
        }

        return value;
    }
}
