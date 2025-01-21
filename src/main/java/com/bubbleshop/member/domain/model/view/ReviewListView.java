package com.bubbleshop.member.domain.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewListView {
    private long count;
    private List<ReviewView> reviewList;
}
