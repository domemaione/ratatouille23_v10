package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.dto.request.PriorityRequestDto
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.*
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.ArrayList

class CategoryServiceAddPriorityTest{
    private val userWithRestaurant: User = User(
        id = 10L,
        name = "Alessio",
        surname = "Spina",
        email = "ales@gmail.com",
        password = "",
        role = UserRoles.ADMIN,
        restaurantId = 28L,
        enabled = true,
        firstAccess = true
    )

    private val userWithoutRestaurant: User = User(
        id = 11L,
        name = "Giacomo",
        surname = "Poretti",
        email = "g.poretti@gmail.com",
        password = "",
        role = UserRoles.ADMIN,
        restaurantId = null,
        enabled = true,
        firstAccess = true
    )

    private val userNotValid: User = User(
        id = 13L,
        name = "Salvatore",
        surname = "Esposito",
        email = "s.esp@gmail.com",
        password = "",
        role = UserRoles.WAITER,
        restaurantId = null,
        enabled = true,
        firstAccess = true
    )

    private val priorityValid: PriorityRequestDto = PriorityRequestDto(
        priority = 3L
    )

    private val priorityNotValid: PriorityRequestDto = PriorityRequestDto(
        priority = -3
    )

    private val categoria: Category = Category(id = 1L, name = "Primo piatto", priority = 1)

    private lateinit var authenticationUserWithRestaurant: Authentication
    private lateinit var authenticationUserWithoutRestaurant: Authentication
    private lateinit var authenticationUserNotValid: Authentication
    private lateinit var categoryService: CategoryService


    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var categoryRepository: CategoryRepository


    @BeforeEach
    fun setup() {
        userRepository = Mockito.mock(UserRepository::class.java)
        categoryRepository = Mockito.mock(CategoryRepository::class.java)

        categoryRepository.save(categoria)
        userRepository.save(userWithRestaurant)
        userRepository.save(userWithoutRestaurant)

        categoryService = CategoryService(
            categoryRepository = Mockito.mock(CategoryRepository::class.java)
        )

        val userDetailsWithRestaurant = CustomUserDetails.build(userWithRestaurant)
        authenticationUserWithRestaurant = TestingAuthenticationToken(
            userDetailsWithRestaurant,
            null,
            ArrayList(userDetailsWithRestaurant.authorities)
        )

        val userDetailsWithUserNotValid = CustomUserDetails.build(userNotValid)
        authenticationUserNotValid = TestingAuthenticationToken(
            userNotValid,
            null,
            ArrayList(userDetailsWithUserNotValid.authorities)
        )

    }

    @Test
    fun addPriorityFailed_UserNotValid() {
        SecurityContextHolder.getContext().authentication = authenticationUserNotValid

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
          this.categoryService.addPriority(categoria.id!!,priorityValid)
        }

    }

    @Test
    fun addPrioritySucces_UserIsValid() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            this.categoryService.addPriority(categoria.id!!,priorityValid)
        }

    }

    @Test
    fun failedWithPriorityNotValid() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            this.categoryService.addPriority(categoria.id!!,priorityNotValid)
        }

    }




}