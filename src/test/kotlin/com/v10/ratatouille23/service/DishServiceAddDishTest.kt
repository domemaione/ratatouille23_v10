package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.*
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.ArrayList

//Test BB
@SpringBootTest
class DishServiceAddDishTest {
    //Nome CE: dishRequestDtoUserNoRestaurant ----- Parametro: dishRequestDto ------- Insieme dei valori: DishRequestDto(id = 1L, name = "Pizza"...
    private val dishRequestDtoUserNoRestaurant: DishRequestDto = DishRequestDto(
        id = 1L,
        name = "Pizza",
        description = "Pomodoro e mozzarella",
        nameLan = null,
        descriptionLan = null,
        cost = 6.0,
        categoryName = "Pizze",
        allergens = null
    )
    private val dishRequestDtoUserRestaurantExist: DishRequestDto = DishRequestDto(
        id = 2L,
        name = "Pasta",
        description = "Pomodoro e mozzarella",
        nameLan = null,
        descriptionLan = null,
        cost = 7.0,
        categoryName = "Primo",
        allergens = null
    )
    private val dishRequestDtoNoAllergens: DishRequestDto = DishRequestDto(
        id = 3L,
        name = "Spaghetto",
        description = "pomodorini",
        nameLan = null,
        descriptionLan = null,
        cost = 6.0,
        categoryName = "Pizze",
        allergens = null
    )
    private val dishRequestDtoNoCategoryName: DishRequestDto = DishRequestDto(
        id = 2L,
        name = "Pizza",
        description = "Pomodoro e mozzarella",
        nameLan = null,
        descriptionLan = null,
        cost = 6.0,
        categoryName = "Pizze",
        allergens = null
    )
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

    private val restaurant: Restaurant =
        Restaurant(id = 28L, name = "La Facoltà", address = "Via MSA", updateAt = null, createdAt = null)
    private val menu: Menu = Menu(id = 29L, restaurantId = 28L)
    private lateinit var authenticationUserWithRestaurant: Authentication
    private lateinit var authenticationUserWithoutRestaurant: Authentication


    private lateinit var dishService: DishService

    @Mock
    lateinit var dishRepository: DishRepository

    @Mock
    lateinit var restaurantRepository: RestaurantRepository

    @Mock
    lateinit var menuRepository: MenuRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var categoryRepository: CategoryRepository


    @BeforeEach
    fun setup() {
        restaurantRepository = Mockito.mock(RestaurantRepository::class.java)
        dishRepository = Mockito.mock(DishRepository::class.java)
        userRepository = Mockito.mock(UserRepository::class.java)
        menuRepository = Mockito.mock(MenuRepository::class.java)
        categoryRepository = Mockito.mock(CategoryRepository::class.java)

        restaurantRepository.save(restaurant)
        userRepository.save(userWithRestaurant)
        userRepository.save(userWithoutRestaurant)
        Mockito.`when`(menuRepository.findByRestaurantId(restaurant.id!!)).thenReturn(menu)

        dishService = DishService(
            categoryRepository = Mockito.mock(CategoryRepository::class.java),
            dishRepository = dishRepository,
            dishAllergensRepository = Mockito.mock(DishAllergensRepository::class.java),
            restaurantRepository = restaurantRepository,
            menuRepository = menuRepository,
        )

        val userDetailsWithRestaurant = CustomUserDetails.build(userWithRestaurant)
        val userDetailsWithoutRestaurant = CustomUserDetails.build(userWithoutRestaurant)
        authenticationUserWithRestaurant = TestingAuthenticationToken(
            userDetailsWithRestaurant,
            null,
            ArrayList(userDetailsWithRestaurant.authorities)
        )
        authenticationUserWithoutRestaurant = TestingAuthenticationToken(
            userDetailsWithoutRestaurant,
            null,
            ArrayList(userDetailsWithoutRestaurant.authorities)
        )
    }

    @Test
    fun addDishFailed_UserWithoutRestaurant() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithoutRestaurant

        assertThrows<IllegalStateException> {
            this.dishService.add(dishRequestDtoUserNoRestaurant)
        }

    }

    @Test
    fun addDishSuccess_UserWithRestaurant() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            this.dishService.add(dishRequestDtoUserRestaurantExist)
        }

    }

    @Test
    fun addDishSuccess_WithoutAllergens() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            this.dishService.add(dishRequestDtoNoAllergens)
        }

        @Test
        fun addDishSuccess_WithoutCategoryName() {
            SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

            assertThrows<IllegalStateException> {
                this.dishService.add(dishRequestDtoNoCategoryName)
            }

        }

    }
}


