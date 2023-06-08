package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.model.*
import com.v10.ratatouille23.repository.*
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import java.util.*
import io.mockk.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension

//Questo è un test sul metodo add e addCategoryToDish di DishService WB & BB
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(TestConfig::class)
class DishServiceTest {

    private lateinit var dishService: DishService

    @Mock
    lateinit var dishRepository: DishRepository

    @Mock
    lateinit var restaurantRepository: RestaurantRepository

    @Mock
    lateinit var menuRepository: MenuRepository

    @Mock
    lateinit var categoryRepository: CategoryRepository


    @BeforeEach
    fun setup() {
        restaurantRepository = mock(RestaurantRepository::class.java)
        dishRepository = mock(DishRepository::class.java)
        val categoryName = "primo piatto"
        val foundCategory = Category(id = 1L, name = categoryName, priority = 0)

        `when`(categoryRepository.findByName(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(foundCategory))


        dishService = DishService(
            menuRepository = mock(MenuRepository::class.java),
            categoryRepository = mock(CategoryRepository::class.java),
            dishRepository = dishRepository,
            dishAllergensRepository = mock(DishAllergensRepository::class.java),
            restaurantRepository = restaurantRepository
        )


    }

    //Test dove menu non esiste e non è possibile inserire la portata
    @Test
    fun addDishTestwithMenuNull(){
        //Given
        val restaurantId = 108L
        val menuId = 109L
        val dishRequestDto = DishRequestDto(
            id = null,
            name = "Polpette milanesi",
            description = "Carne e sugo",
            nameLan = null,
            descriptionLan = null,
            cost = 7.00,
           categoryName = null,
            allergens = null
        )
        // Configurazione del mock
        val restaurant = Optional.of(Restaurant(id = restaurantId, name = "quel fottuto ristorante", address = "Via marco polo", updateAt = null, createdAt = null))
        `when`(restaurantRepository.findById(restaurantId)).thenReturn(restaurant)

        val foundMenu = Menu(id = menuId, restaurantId = restaurantId)
        `when`(menuRepository.findByRestaurantId(restaurantId)).thenReturn(foundMenu)


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


        // When
        val result = dishService.add(dishRequestDto)

    }


    //Test con ristorante non esistente e non è possibile inserire la portata
    @Test
    fun testAddDishWithInvalidRestaurant() {
        // Preparazione dei dati di test
        val dishRequestDto = DishRequestDto(
            id = 111,
            name = "Polpette milanesi",
            description = "Carne e sugo",
            nameLan = null,
            descriptionLan = null,
            cost = 7.00,
            categoryName = null,
            allergens = null
        )
        val invalidRestaurantId = 999L

        // Configurazione del mock per il repository del ristorante
        `when`(restaurantRepository.findById(invalidRestaurantId)).thenReturn(Optional.empty())

        // Simulazione dell'utente autenticato
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

        // Esecuzione del metodo da testare
        assertThrows<IllegalStateException> { dishService.add(dishRequestDto) }

        // Verifica delle interazioni con il repository
        verify(restaurantRepository).findById(invalidRestaurantId)
    }


    //Test inserimento portata con successo
    @Test
    fun testAddDishSuccessfully() {
        // Preparazione dei dati di test
        val dishRequestDto = DishRequestDto(
            id = null,
            name = "Polpette milanesi",
            description = "Carne e sugo",
            nameLan = null,
            descriptionLan = null,
            cost = 7.00,
            categoryName = null,
            allergens = null
        )
        val restaurantId = 28L
        val menuId = 29L

        // Configurazione del mock per il repository del ristorante
        val restaurant = Optional.of(Restaurant(id = restaurantId, name = "La Facoltà", address = "Via MSA", updateAt = null, createdAt = null))
        val menu = Menu(id = menuId, restaurantId = restaurantId)
        `when`(restaurantRepository.findById(restaurantId)).thenReturn(restaurant)
        `when`(menuRepository.findByRestaurantId(restaurantId)).thenReturn(menu)

        // Simulazione dell'utente autenticato
        val user = User(
            id = 107L,
            name = "tester",
            surname = "tester",
            email = "",
            password = "",
            role = UserRoles.ADMIN,
            restaurantId = restaurantId,
            enabled = true,
            firstAccess = true
        )
        val userDetails = CustomUserDetails.build(user)
        val authentication: Authentication = TestingAuthenticationToken(userDetails, null, ArrayList(userDetails.authorities))
        SecurityContextHolder.getContext().authentication = authentication

        // Esecuzione del metodo da testare
        val result = dishService.add(dishRequestDto)

        // Verifica dell'output
        assertNotNull(result.id)
        assertEquals(dishRequestDto.name, result.name)
        assertEquals(dishRequestDto.description, result.description)
        assertEquals(dishRequestDto.cost, result.cost)
        assertEquals(menuId, result.menuId)
        assertNull(result.categoryId) // Verifica che categoryId sia null quando categoryName è null

        // Verifica delle interazioni con i repository
        verify(restaurantRepository).findById(restaurantId)
        verify(menuRepository).findByRestaurantId(restaurantId)
        verify(dishRepository).save(any(Dish::class.java))
    }


    //Test aggiunta di una portata ma con categoria non valida
    @Test
    fun testAddDishWithInvalidCategory() {
        // Preparazione dei dati di test
        val dishRequestDto = DishRequestDto(
            id = null,
            name = "Pizza Margherita",
            description = "La classica",
            nameLan = null,
            descriptionLan = null,
            cost = 9.99,
            categoryName = "Invalid Category",
            allergens = null
        )

        val foundRestaurantId = 108L
        val foundMenuId = 29L

        val foundMenu = Menu(id = foundMenuId, restaurantId = foundRestaurantId)

        // Configurazione del mock per il repository del menu
        `when`(menuRepository.findByRestaurantId(foundRestaurantId)).thenReturn(foundMenu)

        // Configurazione del mock per il repository delle categorie
        `when`(categoryRepository.findByName(any())).thenReturn(Optional.empty())

        // Esecuzione del metodo da testare
        assertThrows(IllegalArgumentException::class.java) {
            dishService.add(dishRequestDto)
        }

        // Verifica delle interazioni con i repository
        verify(menuRepository).findByRestaurantId(foundRestaurantId)
        verify(categoryRepository).findByName(any())
    }





    //Test sul secondo metodo addCategoryToDish in DishService
    @Test
    fun testAddCategoryToDishCategoryisNull() {
        // Preparazione dei dati di test
        val dishId = 30L
        val existingCategory = Category(id = 26L, name = "primo piatto", priority = 0)
        val dish = DishRequestDto(id = dishId, name = "pizza con l'ananas", description = "ananas ben cotta di Pozzuoli", nameLan = null, descriptionLan = null, cost = 9.50, categoryName = "primo piatto", allergens = null)
        val existingDish = Dish(
            id = dishId,
            name = "pizza con l'ananas",
            description = "ananas ben cotta di Pozzuoli",
            nameLan = null,
            descriptionLan = null,
            cost = 9.50,
            menuId = 29L,
            categoryId = 26L
        )

        // Simulazione dell'utente autenticato
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

        // Configurazione del mock per il repository del piatto
        `when`(dishRepository.findById(dishId)).thenReturn(Optional.of(existingDish))

        // Configurazione del mock per il repository della categoria
        //`when`(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty())




        // Configurazione del mock per il metodo save del repository del piatto
        `when`(dishRepository.save(any())).thenAnswer { invocation ->
            invocation.arguments[0] // Restituisci l'oggetto salvato
        }

        // Esecuzione del metodo da testare
        val result = dishService.addCategoryToDish(dish)

        // Verifica dell'output
       // assertEquals(existingDish,result)
        // Verifica dell'output
        assertEquals(existingDish.categoryId, result.categoryId)


        // Verifica delle interazioni con i repository
        verify(dishRepository).findById(dishId)
        //verify(categoryRepository).findByName(categoryName)
        verify(categoryRepository, never()).save(any()) // Verifica che il metodo save non sia mai stato chiamato
    }


    @Test
    fun testAddCategoryToDishNewCategory() {
        // Preparazione dei dati di test
        val dishId = 30L
        val categoryName = "primo piatto"
        val dish = DishRequestDto(id = dishId, name = "pizza con l'ananas", description = "ananas ben cotta di Pozzuoli", nameLan = null, descriptionLan = null, cost = 9.50, categoryName = "primo piatto", allergens = null)
        val existingDish = Dish(
            id = dishId,
            name = "pizza con l'ananas",
            description = "ananas ben cotta di Pozzuoli",
            nameLan = null,
            descriptionLan = null,
            cost = 9.50,
            menuId = 29L,
            categoryId = null
        )

        // Simulazione dell'utente autenticato
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

        // Configurazione del mock per il repository del piatto
        `when`(dishRepository.findById(dishId)).thenReturn(Optional.of(existingDish))

        // Configurazione del mock per il repository della categoria
        `when`(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty())

        // Configurazione del mock per il metodo save del repository del piatto
        `when`(dishRepository.save(any())).thenAnswer { invocation ->
            invocation.arguments[0] // Restituisci l'oggetto salvato
        }

        // Esecuzione del metodo da testare
        val result = dishService.addCategoryToDish(dish)

        // Verifica dell'output
        assertEquals(existingDish.id, result.id)
        assertEquals(existingDish.name, result.name)
        assertEquals(existingDish.description, result.description)
        assertEquals(existingDish.cost, result.cost)
        assertEquals(existingDish.menuId, result.menuId)

        // Verifica delle interazioni con i repository
        verify(dishRepository).findById(dishId)
    }



}



























/*
//Questo test è considerato una WhiteBox

//Mock è un oggetto di simulazione per sostituire le dipendenze reali e simulano tutto il comportamento delle dipendenze
@ExtendWith(MockitoExtension::class)
//@Import(TestConfig::class) //mi è servito per escludere la configurazione di sendgrid (per l'invio di email)
class DishServiceTest {

    lateinit var dishService: DishService

    @Mock
    lateinit var dishRepository: DishRepository

    @BeforeEach
    //questo metodo viene eseguito prima di ogni test ecco perché BeforeEach, dove al suo interno viene creata l'istanza
    //DishService, utilizzando il costruttore ed iniettando le dipendenze (MenuRepository...etc)
    fun setup() {
        dishService = DishService(
            menuRepository = mock(MenuRepository::class.java),
            categoryRepository = mock(CategoryRepository::class.java),
            dishRepository = dishRepository,
            dishAllergensRepository = mock(DishAllergensRepository::class.java),
            restaurantRepository = mock(RestaurantRepository::class.java)
        )
    }

    @Test
    fun testGetExistingDish() {
        // Given
        val dishId = 199L
        val dish = Dish(id = dishId, name = "Acqua minerale naturale Sant'Anna", description = "acqua", nameLan = null, descriptionLan = null, cost = 2.00, menuId = 109L, categoryId = 167L )

        `when`(dishRepository.findById(dishId)).thenReturn(Optional.of(dish))

        // When
        val result = dishService.get(dishId)

        // Then
        assertEquals(dishId, result.id)
        assertEquals("Acqua minerale naturale Sant'Anna", result.name)
        assertEquals("coca", result.description)
        //assertEquals(2L, result.cost, 0.0)
    }

}
*/