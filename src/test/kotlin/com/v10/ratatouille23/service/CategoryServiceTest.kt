package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.request.PriorityRequestDto
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.CategoryRepository
import com.v10.ratatouille23.security.CustomUserDetails
import com.v10.ratatouille23.utils.UserRoles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

//Questo test è solo del metodo addPriority in CategoryService

//@ExtendWith(MockitoExtension::class)
//@ContextConfiguration(classes = [CategoryService::class])
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(TestConfig::class)
class CategoryServiceTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setup() {
        categoryService = CategoryService(categoryRepository)
    }

    @Test
    fun testAddPriority() {
        // Preparazione dei dati di test
        val categoryId = 1L
        val priority = 2L
        val priorityRequestDto = PriorityRequestDto(priority)


        val category = Category(id = categoryId, name = "Category A", priority = 0)
        val savedCategory = Category(id = categoryId, name = "", priority = priority)

        // Configurazione del mock per il repository di categoria
        `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(category)
        `when`(categoryRepository.save(category)).thenReturn(savedCategory)
      //  `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(null) Questo è il caso in cui la categoria non è presente nel db

        // Simulazione dell'utente autenticato
       // val encodedPassword = BCryptPasswordEncoder().encode("provaprova") // Codifica della password
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
        val result = categoryService.addPriority(categoryId, priorityRequestDto)

        // Verifica dell'output
        assertEquals(savedCategory, result)

        // Verifica delle interazioni con il repository
        verify(categoryRepository).getReferenceById(categoryId)
        verify(categoryRepository).save(category)
    }


    @Test
    fun testAddPriorityCategoryIsNull() {
        // Preparazione dei dati di test
        val categoryId = 1L
        val priority = 2L
        val priorityRequestDto = PriorityRequestDto(priority)


        val category = Category(id = categoryId, name = "Category A", priority = 0)
        val savedCategory = Category(id = categoryId, name = "", priority = priority)

        // Configurazione del mock per il repository di categoria
       // `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(category)
        `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(null)
        `when`(categoryRepository.save(category)).thenReturn(savedCategory)
        //   Questo è il caso in cui la categoria non è presente nel db

        // Simulazione dell'utente autenticato
        // val encodedPassword = BCryptPasswordEncoder().encode("provaprova") // Codifica della password
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
        val result = categoryService.addPriority(categoryId, priorityRequestDto)

        // Verifica dell'output
        assertEquals(savedCategory, result)

        // Verifica delle interazioni con il repository
        verify(categoryRepository).getReferenceById(categoryId)
        verify(categoryRepository).save(category)
    }


    //Test di aggiunta di priorità con una priorità negativa:
    @Test
    fun testAddPriorityWithNegativePriority() {
        // Preparazione dei dati di test
        val categoryId = 1L
        val negativePriority = -1L
        val priorityRequestDto = PriorityRequestDto(negativePriority)

        val category = Category(id = categoryId, name = "Category A", priority = 0)

        // Configurazione del mock per il repository di categoria
        `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(category)

        // Esecuzione del metodo da testare
        assertThrows<IllegalArgumentException> {
            categoryService.addPriority(categoryId, priorityRequestDto)
        }

        // Verifica delle interazioni con il repository
        verify(categoryRepository).getReferenceById(categoryId)
        verifyNoMoreInteractions(categoryRepository)
    }


    //Test di aggiunta di priorità con un utente non autenticato
    @Test
    fun testAddPriorityWithUnauthenticatedUser() {
        // Preparazione dei dati di test
        val categoryId = 1L
        val priority = 2L
        val priorityRequestDto = PriorityRequestDto(priority)

        // Simulazione di un utente non autenticato
        SecurityContextHolder.clearContext()


        // Esecuzione del metodo da testare
        assertThrows<IllegalStateException> {
            categoryService.addPriority(categoryId, priorityRequestDto)
        }


    }



}





















/*
@SpringBootTest
@Import(TestConfig::class)
@ExtendWith(MockitoExtension::class)
class CategoryServiceTest {

    @MockBean
    private lateinit var categoryRepository: CategoryRepository

    private lateinit var categoryService: CategoryService

    val authenticatedUserHelper: AuthenticatedUserHelper? = mock(AuthenticatedUserHelper::class.java)

    @BeforeEach
    fun setup() {
        categoryService = CategoryService(categoryRepository)

    }

    @Test
    fun testAddPriority_UpdatesCategoryPriority() {
        // Dati di input del test
        val categoryId = 217L
        val priority = 5L
        val priorityRequestDto = PriorityRequestDto(priority)

        // Creazione dell'istanza di categoria esistente
        val existingCategory = Category(id = categoryId, name = "Antipasto", priority = 4)

        `when`(authenticatedUserHelper?.get()).thenReturn(User(id = 123, name = "tester", surname = "betatester", email = "test@gmail.com", password = "provaprova", role = UserRoles.ADMIN, restaurantId = 108L, enabled = true, firstAccess = true))
        // Configurazione del mock del repository per restituire la categoria esistente
        `when`(categoryRepository.getReferenceById(categoryId)).thenReturn(existingCategory)

        // Chiamata al metodo da testare
        val result = categoryService.addPriority(categoryId, priorityRequestDto)

        // Verifica che il metodo save sia stato chiamato sul repository
        verify(categoryRepository, times(1)).save(existingCategory)

        // Verifica che la priorità sia stata aggiornata correttamente
        assertEquals(priority, result.priority)
    }
}
*/