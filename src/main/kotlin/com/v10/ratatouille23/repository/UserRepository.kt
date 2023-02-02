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
    //Query che setta a null a tutti gli utenti il restaurant_id in user quando viene cancellato
    //il ristorante
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.restaurantId = NULL WHERE u.restaurantId = :restaurantId")
    fun setRestaurantIdToNullForUsersByRestaurantId(@Param("restaurantId") restaurantId: Long) : Int

    //query che cancella tutti gli utenti che non sono ADMIN se il ristorante viene
    //cancellato
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.restaurantId IS NULL AND u.role != 'ADMIN'")
    fun deleteUsersWithoutRestaurantIdAndNotAdmin() : Int


    fun getByEmail(email: String): Optional<User>
    fun findByRoleAndEnabled(role: UserRoles, enabled: Boolean): List<User>
    fun findById(userId: Long?): User?
    fun findByRestaurantId(restaurantId: Long): User
    fun findByEmail(username: String?): User

}
