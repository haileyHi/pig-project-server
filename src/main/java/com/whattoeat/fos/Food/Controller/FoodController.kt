package com.whattoeat.fos.Food.Controller

import com.whattoeat.fos.Food.Domain.Entity.FoodSpecs
import com.whattoeat.fos.Food.Service.FoodService
import com.whattoeat.fos.Util.Response
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

import javax.servlet.http.HttpServletRequest

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
class FoodController {

    @Autowired
    private lateinit var foodService: FoodService

    @ApiOperation(value = "카테고리 기준 메뉴 검색")
    @ApiImplicitParams(
        ApiImplicitParam(name = "category", value = "검색할 카테고리 이름"),
        ApiImplicitParam(name = "order", value = "정렬 조건 이름(review, rank)"),
        ApiImplicitParam(name = "page", value = "조회할 페이지 번호", dataType = "int", paramType = "query", defaultValue = "1"),
        ApiImplicitParam(name = "pageSize", value = "페이지당 보여주는 데이터 개수", dataType = "int", paramType = "query", defaultValue = "35")
    )
    @GetMapping("/search")
    fun getMenuByCategory(
        httpRequest: HttpServletRequest,
        @RequestParam(required = false) searchRequest: Map<String, Any>
    ) : ResponseEntity<Response>{
        val searchKeys = mutableMapOf<FoodSpecs.SearchKey, Any>()
        // map 사용한다면 여기에 넣고 하나씩 꺼내서 searchKeys에 저장하기.
        var page = 1
        var pageSize = 35
        var order = "id"
        for (searchKey in searchRequest.keys) {
            when (searchKey) {
                "page" -> {
                    page = Integer.parseInt(searchRequest.get("page").toString())
                }
                "pageSize" -> {
                    pageSize = Integer.parseInt(searchRequest.get("pageSize").toString())
                }
                "order" -> {
                    order = if (searchRequest.get("order").toString() == "rank") {
                        // 인기 순
                        "id"
                    } else if (searchRequest.get("order").toString() == "abc"){
                        "name"
                    } else {
                        "id"
                    }
                }
                else -> {
                    try {
                        searchKeys.put(FoodSpecs.SearchKey.valueOf(searchKey.uppercase(Locale.getDefault())), searchRequest.get(key = searchKey)!!)
                    } catch (e : IllegalArgumentException) {
                        // 유효하지 않은 키 값.
                        e.printStackTrace()
                    }
                }
            }
        }
        return if(searchKeys.isEmpty()) {
            foodService.findAll(order, page, pageSize)
        } else {
            foodService.findFoodsWithFilter(searchKeys, order, page, pageSize)
        }
    }

    @GetMapping
    fun getMenuByKeyword(@RequestParam keyword: String): ResponseEntity<Response> {
        return foodService.getFoodListByKeyword(keyword = keyword)
    }

    @GetMapping("/rank")
    fun getTop5Ranks(): ResponseEntity<Response>{
        return foodService.getTop5Ranks()
    }
}