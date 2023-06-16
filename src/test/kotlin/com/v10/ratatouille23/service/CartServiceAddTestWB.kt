package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.CartRequestDto
import com.v10.ratatouille23.model.Cart
import com.v10.ratatouille23.model.CartDish
import com.v10.ratatouille23.model.TableRestaurant
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.*
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
class CartServiceAddTestWB{
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

    private val tableRestaurantValid: TableRestaurant = TableRestaurant(
        id = 11L,
        restaurantId = 18L,
        seats = 8L
    )

    private val tableRestaurantNotValid: TableRestaurant = TableRestaurant(
        id = 19L,
        restaurantId = 18L,
        seats = 8L
    )

    private val tableRestaurantWithStatusClosed: TableRestaurant = TableRestaurant(
        id = 23L,
        restaurantId = 18L,
        seats = 8L
    )

    private val cartRequestDtoTest: CartRequestDto = CartRequestDto(dishes = arrayOf(27L, 30L))

    private lateinit var authenticationUserWithRestaurant: Authentication
    private lateinit var authenticationUserWithoutRestaurant: Authentication
    private lateinit var cartService: CartService

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var tableRestaurantRepository: TableRestaurantRepository

    @Mock
    lateinit var cartDishRepository: CartDishRepository

    @Mock
    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var billViewRepository: BillViewRepository

    @BeforeEach
    fun setup() {
        userRepository = Mockito.mock(UserRepository::class.java)
        userRepository.save(userWithRestaurant)

        cartService = CartService(
            tableRestaurantRepository = Mockito.mock(TableRestaurantRepository::class.java),
            cartDishRepository = Mockito.mock(CartDishRepository::class.java),
            cartRepository = Mockito.mock(CartRepository::class.java),
            billViewRepository = Mockito.mock(BillViewRepository::class.java)
        )

        tableRestaurantRepository.save(tableRestaurantValid)


        val userDetailsWithRestaurant = CustomUserDetails.build(userWithRestaurant)
        authenticationUserWithRestaurant = TestingAuthenticationToken(
            userDetailsWithRestaurant,
            null,
            ArrayList(userDetailsWithRestaurant.authorities)
        )

        val userDetailsWithoutRestaurant = CustomUserDetails.build(userWithoutRestaurant)
        authenticationUserWithoutRestaurant = TestingAuthenticationToken(
            userDetailsWithoutRestaurant,
            null,
            ArrayList(userDetailsWithoutRestaurant.authorities)
        )

    }

    @Test
    fun addCartFailed_UserWithoutRestaurantWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithoutRestaurant

        assertThrows<IllegalStateException> {
            cartService.add(tableRestaurantValid.id!!, cartRequestDtoTest)
        }

        verify(tableRestaurantRepository, times(0)).save(any(TableRestaurant::class.java))
        verify(cartRepository, times(0)).save(any(Cart::class.java))
        verify(cartDishRepository, times(0)).save(any(CartDish::class.java))
    }

    @Test
    fun addCartFailed_TableRestaurantNotValidWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            cartService.add(tableRestaurantNotValid.id!!, cartRequestDtoTest)
        }

        verify(tableRestaurantRepository, times(0)).save(any(TableRestaurant::class.java))
        verify(cartRepository, times(0)).save(any(Cart::class.java))
        verify(cartDishRepository, times(0)).save(any(CartDish::class.java))
    }

    @Test
    fun addCartFailed_StatusClosedWB() {
        SecurityContextHolder.getContext().authentication = authenticationUserWithRestaurant

        assertThrows<IllegalStateException> {
            cartService.add(tableRestaurantWithStatusClosed.id!!, cartRequestDtoTest)
        }

        verify(tableRestaurantRepository, times(0)).save(any(TableRestaurant::class.java))
        verify(cartRepository, times(0)).save(any(Cart::class.java))
        verify(cartDishRepository, times(0)).save(any(CartDish::class.java))
    }

}