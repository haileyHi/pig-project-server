package com.whattoeat.fos.Food.DTO

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

public class FoodDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class FoodListResponse(
        var id: Int,
        var categoryName: String,
        var menuName: String,
        var image: String?
    )
}