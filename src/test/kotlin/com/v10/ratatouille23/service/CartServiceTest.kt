package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.CartRequestDto
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.model.Cart
import com.v10.ratatouille23.model.CartDish
import com.v10.ratatouille23.model.TableRestaurant
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.*
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.CartStatus
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.ArrayList
import kotlin.collections.toLongArray
import kotlin.collections.toTypedArray

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(TestConfig::class)
class CartServiceTest{
    private lateinit var cartService: CartService

    @Mock
    private lateinit var tableRestaurantRepository: TableRestaurantRepository

    @Mock
    private lateinit var cartDishRepository: CartDishRepository

    @Mock
    private lateinit var cartRepository: CartRepository

    @Mock
    private lateinit var billViewRepository: BillViewRepository

    @BeforeEach
    fun setup() {
        cartService = CartService(
            tableRestaurantRepository = mock(TableRestaurantRepository::class.java),
            cartDishRepository = mock(CartDishRepository::class.java),
            cartRepository = mock(CartRepository::class.java),
            billViewRepository = mock(BillViewRepository::class.java)
        )
    }

    @Test
    fun testAddCartNewCart() {
        // Preparazione dei dati di test
        val tableId = 37L
        val dishes: Array<Long>? = arrayOf(27L, 30L)
        val cartRequestDto = CartRequestDto(dishes = dishes)

        //Simulazione utente autenticato
        val user = User(
            id = 107L,
            name = "tester",
            surname = "tester",
            email = "",
            password = "",
            role = UserRoles.ADMIN,
            restaurantId = 108L,
            enabled = true,
            firstAccess = true
        )

        val userDetails = CustomUserDetails.build(user)
        val authentication: Authentication = TestingAuthenticationToken(userDetails, null, ArrayList(userDetails.authorities))
        SecurityContextHolder.getContext().authentication = authentication

        val tableRestaurant = TableRestaurant(id = tableId, seats = 8L, restaurantId = 28L)
        val cart = Cart(
            id = 1L,
            tableId = tableId,
            status = CartStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updateAt = null
        )
        `when`(tableRestaurantRepository.getReferenceById(tableId)).thenReturn(tableRestaurant)
       // `when`(tableRestaurantRepository.getReferenceById(anyLong())).thenReturn(tableRestaurant)

        `when`(cartRepository.findByTableIdAndStatus(tableId, CartStatus.OPEN)).thenReturn(null)
        `when`(cartRepository.save(any())).thenReturn(cart)
        //`when`(cartDishRepository.saveAll(any())).thenReturn(emptyList())

        // Esecuzione del metodo da testare
        val result = cartService.add(tableId, cartRequestDto)

        // Verifica dell'output
        assertEquals(cart.id, result.id)
        assertEquals(cart.tableId, result.tableId)
        assertEquals(cart.status, result.status)
        assertEquals(cart.createdAt, result.createdAt)
        assertNull(result.updateAt)

        // Verifica delle interazioni con i repository
        verify(tableRestaurantRepository).getReferenceById(tableId)
        verify(cartRepository).findByTableIdAndStatus(tableId, CartStatus.OPEN)
        verify(cartRepository).save(any())
        //verify(cartDishRepository).saveAll(any())
    }



    @Test
    fun testAdd_ExistingCartWithoutDishes_Success() {
        // Preparazione dei dati di test
        val tableId = 1L
        val cartRequestDto = CartRequestDto(dishes = null)

        //Simulazione utente autenticato
        val user = User(
            id = 107L,
            name = "tester",
            surname = "tester",
            email = "",
            password = "",
            role = UserRoles.ADMIN,
            restaurantId = 108L,
            enabled = true,
            firstAccess = true
        )

        val userDetails = CustomUserDetails.build(user)
        val authentication: Authentication = TestingAuthenticationToken(userDetails, null, ArrayList(userDetails.authorities))
        SecurityContextHolder.getContext().authentication = authentication


        val tableRestaurant = TableRestaurant(id = tableId, seats = 8L, restaurantId = 28L, )
        `when`(tableRestaurantRepository.getReferenceById(tableId)).thenReturn(tableRestaurant)

        val existingCart = Cart(id = 1L, tableId = tableId, status = CartStatus.OPEN, null, null)
        `when`(cartRepository.findByTableIdAndStatus(tableId, CartStatus.OPEN)).thenReturn(existingCart)

        // Esecuzione del metodo da testare
        val result = cartService.add(tableId, cartRequestDto)

        // Verifica dell'output
        assertNotNull(result)
        assertEquals(existingCart.id, result.id)
        assertEquals(tableId, result.tableId)
        assertEquals(CartStatus.OPEN, result.status)
        assertNotNull(result.createdAt)
        assertNotNull(result.updateAt)

        // Verifica delle interazioni con i repository
        verify(tableRestaurantRepository).getReferenceById(tableId)
        verify(cartRepository).findByTableIdAndStatus(tableId, CartStatus.OPEN)
        verify(cartRepository).save(any())
    }


    @Test
    fun testAddCartExistingCartWithDishes() {
        // Preparazione dei dati di test
        val tableId = 37L
        val dishes: Array<Long>? = arrayOf(27L, 30L)
        val cartRequestDto = CartRequestDto(dishes = dishes)

        //Simulazione utente autenticato
        val user = User(
            id = 107L,
            name = "tester",
            surname = "tester",
            email = "",
            password = "",
            role = UserRoles.ADMIN,
            restaurantId = 108L,
            enabled = true,
            firstAccess = true
        )

        val userDetails = CustomUserDetails.build(user)
        val authentication: Authentication = TestingAuthenticationToken(userDetails, null, ArrayList(userDetails.authorities))
        SecurityContextHolder.getContext().authentication = authentication

        val tableRestaurant = TableRestaurant(id = tableId, seats = 8L, restaurantId = 28L)
        val existingCart = Cart(
            id = 1L,
            tableId = tableId,
            status = CartStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updateAt = null
        )
        `when`(tableRestaurantRepository.getReferenceById(tableId)).thenReturn(tableRestaurant)
        `when`(cartRepository.findByTableIdAndStatus(tableId, CartStatus.OPEN)).thenReturn(existingCart)



        // Esecuzione del metodo da testare
        val result = cartService.add(tableId, cartRequestDto)

        // Verifica dell'output
        assertEquals(existingCart.id, result.id)
        assertEquals(existingCart.tableId, result.tableId)
        assertEquals(existingCart.status, result.status)
        assertEquals(existingCart.createdAt, result.createdAt)
        assertNotNull(result.updateAt)

        // Verifica delle interazioni con i repository
        verify(tableRestaurantRepository).getReferenceById(tableId)
        verify(cartRepository).findByTableIdAndStatus(tableId, CartStatus.OPEN)
        verify(cartRepository).save(any())

    }




}