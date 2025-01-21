package com.bubbleshop.member.domain.model.view;

import com.bubbleshop.member.infrastructure.dto.GetProductDetailRspDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    private String productName;

    public ProductView(GetProductDetailRspDTO getProductDetailRspDTO) {
        this.productName = getProductDetailRspDTO.getProductName();
    }
}
