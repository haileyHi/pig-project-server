package com.whattoeat.fos.Food.DTO

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

class FoodDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class FoodListResponse(
        var id: Int,
        var categoryName: String,
        var menuName: String,
        var image: String?
    )

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
    interface FoodRank {
        val rank: Int
        val menuId: Int
        val menuName: String
        val count: Int
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class FoodRankResponse(
        var rank: Int,
        var menuId: Int,
        var menuName: String,
        var rankDiff: Int?,
        var orderCount: Int
    )
}