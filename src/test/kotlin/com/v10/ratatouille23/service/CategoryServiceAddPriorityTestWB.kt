package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.PriorityRequestDto
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.CategoryRepository
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.ArrayList
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.security.core.context.SecurityContextHolder

@SpringBootTest
class CategoryServiceAddPriorityTestWB{
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
    fun addPriorityFailed_UserNotValidWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserNotValid

        assertThrows<IllegalStateException> {
            categoryService.addPriority(categoria.id!!, priorityValid)
        }

        verify(categoryRepository, times(0)).save(any(Category::class.java))
    }

    @Test
    fun addPrioritySuccess_UserIsValidWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            categoryService.addPriority(categoria.id!!, priorityValid)
        }

        verify(categoryRepository, times(1)).save(any(Category::class.java))
    }

    @Test
    fun failedWithPriorityNotValidWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            categoryService.addPriority(categoria.id!!, priorityNotValid)
        }

        verify(categoryRepository, times(0)).save(any(Category::class.java))
    }


}