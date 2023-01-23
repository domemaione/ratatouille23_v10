package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.VRestaurantUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VRestaurantUsersRepository : JpaRepository<VRestaurantUsers, Long> {
    //fun findByRestaurantId(): List<VRestaurantUsers>
}