package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.Dish
import com.v10.ratatouille23.model.DishAllergens
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.repository.CategoryRepository
import com.v10.ratatouille23.repository.DishAllergensRepository
import com.v10.ratatouille23.repository.DishRepository
import com.v10.ratatouille23.repository.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DishService(
    private val menuRepository: MenuRepository,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val dishAllergensRepository: DishAllergensRepository
){

    @Transactional
    fun add(dishRequestDto: DishRequestDto): Dish {
        val foundRestaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val foundMenu = this.menuRepository.findByRestaurantId(foundRestaurantId)
        var categoryId: Long? = null

        if(dishRequestDto.categoryName != null) {
            val foundCategory = this.categoryRepository.findByName(dishRequestDto.categoryName)
            categoryId = if (foundCategory.isPresent) {
                foundCategory.get().id
            } else {
                val newCategory = Category(id = null, name = dishRequestDto.categoryName)
                this.categoryRepository.save(newCategory)
                newCategory.id
            }
        }

        val toSave = Dish(
            id = null,
            name = dishRequestDto.name,
            description = dishRequestDto.description,
            cost = dishRequestDto.cost,
            menuId = foundMenu.id!!,
            categoryId = categoryId

        )
        val saved = this.dishRepository.save(toSave)
        //inserisce successivamente nella tabella DishAllergens, tutti gli allergeni associati a quella portata
        if(dishRequestDto.allergens != null) {
          val toSave = dishRequestDto.allergens.map {
              DishAllergens(id = null, dishId = saved.id!!, it)
          }
            this.dishAllergensRepository.saveAll(toSave)
        /*
            for (allergenId in dishRequestDto.allergens) {
                val dishAllergens = DishAllergens(
                    id = null,
                    dishId = saved.id!!,
                    allergenId = allergenId
                )
                this.dishAllergensRepository.save(dishAllergens)

            }*/
        }
        return saved
    }


    fun get(id: Long): Dish {
        val found = this.dishRepository.findById(id)
        if(found.isEmpty)
            throw IllegalStateException("Dish not found")

        return found.get()
    }



}





/* @Transactional
    fun add(dishRequestDto: DishRequestDto): Dish {
        val foundRestaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val foundMenu = this.menuRepository.findByRestaurantId(foundRestaurantId)
        val foundCategory = this.categoryRepository.findByName(dishRequestDto.categoryName)

        val categoryId = if (foundCategory.isPresent) {
            foundCategory.get().id
        } else {
            val newCategory = Category(id = null, name = dishRequestDto.categoryName)
            this.categoryRepository.save(newCategory)
            newCategory.id
        }


        val toSave = Dish(
            id = null,
            name = dishRequestDto.name,
            description = dishRequestDto.description,
            cost = dishRequestDto.cost,
            menuId = foundMenu.id!!,
            categoryId = categoryId!!

        )
        val saved = this.dishRepository.save(toSave)
        //inserisce successivamente nella tabella DishAllergens, tutti gli allergeni associati a quella portata
        for (allergenId in dishRequestDto.allergens) {
            val dishAllergens = DishAllergens(
                id = null,
                dishId = saved.id!!,
                allergenId = allergenId
            )
            this.dishAllergensRepository.save(dishAllergens)

        }
        return saved
    }*/