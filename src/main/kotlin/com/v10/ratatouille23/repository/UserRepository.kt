package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {

    //query che cancella tutti gli utenti che non sono ADMIN se il ristorante viene
    //cancellato
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.restaurantId IS NULL AND u.role != 'ADMIN'")
    fun deleteUsers() : Int

    @Transactional
    @Modifying
    @Query("SELECT u FROM User u WHERE u.role <> 'ADMIN' AND u.restaurantId = :restaurantId")
    fun getAllNoAdmin(restaurantId: Long) : List<User>

    fun getByEmail(email: String): Optional<User>
    fun findByRoleAndEnabled(role: UserRoles, enabled: Boolean): List<User>
    fun findAllByRestaurantId(restaurantId: Long): List<User>




}
