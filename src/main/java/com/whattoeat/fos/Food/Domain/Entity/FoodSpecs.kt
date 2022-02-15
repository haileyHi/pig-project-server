package com.whattoeat.fos.Food.Domain.Entity

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.*

class FoodSpecs {
    fun searchWith(searchKeyword: Map<SearchKey, Any>): Specification<Food> {
        return Specification { root: Root<Food>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->
            val predicate =
                FoodSpecs().getPredicateList(searchKeyword, root, builder)
            builder.and(*predicate.toTypedArray())
        }
    }

    fun getPredicateList(
        searchKeyword: Map<SearchKey, Any>,
        root: Root<Food>,
        builder: CriteriaBuilder
    ): List<Predicate> {
        val predicate: ArrayList<Predicate> = ArrayList<Predicate>()
        for (key: SearchKey in searchKeyword.keys) {
            when (key) {
                SearchKey.KEYWORD -> {
                    predicate.add(builder.like(root.get(key.getValue()), "%" + searchKeyword.get(key) + "%"))
                }
                SearchKey.CATEGORY -> {
                    predicate.add(
                        builder.equal(
                            root.get<String>(key.getValue()).get<Int>("id"),
                            searchKeyword.get(key).toString()
                        )
                    )
                }
            }
        }
        return predicate
    }

    enum class SearchKey(private var value: String) {
        KEYWORD("name"),
        CATEGORY("category");

        fun getValue(): String = value

    }
}