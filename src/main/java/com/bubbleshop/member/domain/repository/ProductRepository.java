package com.bubbleshop.member.domain.repository;

import com.bubbleshop.member.domain.model.view.ProductView;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public interface ProductRepository {
    Map<String, ProductView> getProductList(Collection<String> productIds);
}
