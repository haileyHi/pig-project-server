package com.whattoeat.fos.Food.Controller

import com.whattoeat.fos.Food.Service.FoodService
import com.whattoeat.fos.Util.Response
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
class FoodController {

    @Autowired
    private lateinit var foodService: FoodService

    @GetMapping("/category/{category}")
    fun getMenuByCategory(httpRequest: HttpServletRequest,
                          @PathVariable category: String,
                          @RequestParam orderBy: String,
                          @RequestParam page: Int,
                          @RequestParam pageSize: Int
    ) : ResponseEntity<Response>{
        return foodService.getFoodListByCategory(category = category, sortBy = orderBy, page = page, pageSize = pageSize)
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