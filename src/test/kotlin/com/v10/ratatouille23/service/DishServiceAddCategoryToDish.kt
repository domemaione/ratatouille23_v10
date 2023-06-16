package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.DishRequestDto
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
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.ArrayList

@SpringBootTest
class DishServiceAddCategoryToDish{
    private val dishRequestDtoWithCategory: DishRequestDto = DishRequestDto(
        id = 1L,
        name = "Pizza",
        description = "Pomodoro e mozzarella",
        nameLan = null,
        descriptionLan = null,
        cost = 6.0,
        categoryName = "Pizze",
        allergens = null
    )

    private val dishRequestDtoWithIdNull: DishRequestDto = DishRequestDto(
        id = null,
        name = "Pizza",
        description = "Pomodoro e mozzarella",
        nameLan = null,
        descriptionLan = null,
        cost = 6.0,
        categoryName = "Pizze",
        allergens = null
    )

   private val dishRequestDtoWithCategoryNull: DishRequestDto = DishRequestDto(
       id = 2L,
       name = "Pizza",
       description = "Pomodoro e mozzarella",
       nameLan = null,
       descriptionLan = null,
       cost = 6.0,
       categoryName = null,
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

    private val restaurant: Restaurant = Restaurant(id = 28L, name = "La Facoltà", address = "Via MSA", updateAt = null, createdAt = null)
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
        Mockito.`when`(menuRepository.findByRestaurantId(restaurant.id!!)).thenReturn(menu)

        dishService = DishService(
            categoryRepository = Mockito.mock(CategoryRepository::class.java),
            dishRepository = dishRepository,
            dishAllergensRepository = Mockito.mock(DishAllergensRepository::class.java),
            restaurantRepository = restaurantRepository,
            menuRepository = menuRepository,
        )

        val userDetailsWithRestaurant = CustomUserDetails.build(userWithRestaurant)
        authenticationUserWithRestaurant = TestingAuthenticationToken(
            userDetailsWithRestaurant,
            null,
            ArrayList(userDetailsWithRestaurant.authorities)
        )

    }

    @Test
    fun addDishSucces_WithCtegory() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithoutRestaurant

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            this.dishService.addCategoryToDish(dishRequestDtoWithCategory)
        }

    }

    @Test
    fun addDishFailedWithDishIdNull() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            this.dishService.addCategoryToDish(dishRequestDtoWithIdNull)
        }

    }

    @Test
    fun addDishWithCategoryNameNull() {

        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            this.dishService.addCategoryToDish(dishRequestDtoWithCategoryNull)
        }

    }

}