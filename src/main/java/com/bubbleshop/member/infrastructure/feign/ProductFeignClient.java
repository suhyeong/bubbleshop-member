package com.bubbleshop.member.infrastructure.feign;

import com.bubbleshop.member.infrastructure.feign.config.ProductClientConfig;
import com.bubbleshop.member.infrastructure.dto.GetProductDetailRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

import static com.bubbleshop.member.infrastructure.feign.ProductUrl.PRODUCTS;
import static com.bubbleshop.member.infrastructure.feign.ProductUrl.PRODUCT_DEFAULT_URL;

// TODO 1. FeignClient 호출 실패시 FallbackFactory 필요한지? 2. Header 세팅 (Member 도메인에서 호출했다는 정보 필요한지?
@FeignClient(name = "product", url = "${host.product}", configuration = ProductClientConfig.class)
public interface ProductFeignClient {
    @GetMapping(value = PRODUCT_DEFAULT_URL + PRODUCTS)
    Map<String, GetProductDetailRspDTO> getProductDetail(@RequestParam Collection<String> productIds);
}
