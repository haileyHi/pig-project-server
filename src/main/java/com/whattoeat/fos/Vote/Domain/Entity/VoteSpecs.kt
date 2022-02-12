package com.whattoeat.fos.Vote.Domain.Entity

import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class VoteSpecs {
    /*public fun searchWith(searchKeyword: Map<SearchKey, Objects> ) : Specification<Vote> {
        return { root: Root<Vote>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->
            val predicate: List<Predicate> = getPredicateWithKeyword(searchKeyword, root, builder)
            builder.and(predicate.toArray(arrayOfNulls<Predicate>(0)))
        } as Specification<Vote>
        *//*= ((root, query, builder) -> {
            List
        }) as Specification<Vote>*//*
    }

    fun getPredicateWithKeyword(searchKeyword : Map<SearchKey, Objects>, root: Root<Vote>, builder: CriteriaBuilder) : List<Predicate>{
        val predicate : List<Predicate> = ArrayList<Predicate>()
        for (key: SearchKey in searchKeyword.keys){
            when(key){

            }
        }
    }
    enum class SearchKey {

    }*/
}