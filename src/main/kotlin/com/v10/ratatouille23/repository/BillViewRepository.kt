package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.BillView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
interface BillViewRepository: JpaRepository <BillView, Long> {
    fun findByCartId(cartId: Long): List<BillView>
}