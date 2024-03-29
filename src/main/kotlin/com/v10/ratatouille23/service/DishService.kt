package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.model.*
import com.v10.ratatouille23.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DishService(
    private val menuRepository: MenuRepository,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val dishAllergensRepository: DishAllergensRepository, private val restaurantRepository: RestaurantRepository
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
                val newCategory = Category(id = null, name = dishRequestDto.categoryName, priority = 0)
                this.categoryRepository.save(newCategory)
                newCategory.id
            }
        }

        val toSave = Dish(
            id = null,
            name = dishRequestDto.name,
            description = dishRequestDto.description,
            nameLan = dishRequestDto.nameLan,
            descriptionLan = dishRequestDto.descriptionLan,
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
        }
        return saved
    }


    fun get(id: Long): Dish {
        val found = this.dishRepository.findById(id)
        if(found.isEmpty)
            throw IllegalStateException("Dish not found")

        return found.get()
    }


    fun getAll(): List<Dish> {
        val restaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val menuId = this.menuRepository.findByRestaurantId(restaurantId).id
        return dishRepository.findAllByMenuId(menuId)
    }

    fun getDishesByCategory(categoryId: Long): List<Dish>{
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        return dishRepository.findAllByCategoryId(categoryId)
    }

    fun update(dishRequestDto: DishRequestDto): Dish {
        val found: Dish = this.get(dishRequestDto.id!!)
        val toSave = Dish(
            id = found.id,
            name = dishRequestDto.name,
            description = dishRequestDto.description,
            nameLan = dishRequestDto.nameLan,
            descriptionLan = dishRequestDto.descriptionLan,
            cost = dishRequestDto.cost,
            menuId = found.menuId,
            categoryId = found.categoryId

        )
        val saved = this.dishRepository.save(toSave)
        return saved
    }


    fun addCategoryToDish(dishRequestDto: DishRequestDto): Dish{
        val found: Dish = this.get(dishRequestDto.id!!)
        var categoryId: Long? = null

        if(dishRequestDto.categoryName != null) {
            val foundCategory = this.categoryRepository.findByName(dishRequestDto.categoryName)
            categoryId = if (foundCategory.isPresent) {
                foundCategory.get().id
            } else {
                val newCategory = Category(id = null, name = dishRequestDto.categoryName, priority = 0)
                this.categoryRepository.save(newCategory)
                newCategory.id
            }
        }
        val toSave = Dish(
            id = found.id,
            name = found.name,
            description = found.description,
            nameLan = found.nameLan,
            descriptionLan = found.descriptionLan,
            cost = found.cost,
            menuId = found.menuId,
            categoryId = categoryId

        )
        val saved = this.dishRepository.save(toSave)

        return saved
    }


    fun delete(id: Long): Dish{
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not found")
        val found: Dish = this.get(id)
        this.dishRepository.deleteById(found.id!!)
        return found
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




/*
            for (allergenId in dishRequestDto.allergens) {
                val dishAllergens = DishAllergens(
                    id = null,
                    dishId = saved.id!!,
                    allergenId = allergenId
                )
                this.dishAllergensRepository.save(dishAllergens)

            }*/