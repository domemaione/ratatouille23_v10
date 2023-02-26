package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.CategoryDto
import com.v10.ratatouille23.dto.request.PriorityRequestDto
import com.v10.ratatouille23.model.Allergens
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.Dish
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
){

    fun add(categoryDto: CategoryDto): Category {
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        val toSave = Category(id = null, name = categoryDto.name, priority = categoryDto.priority)
        val saved = this.categoryRepository.save(toSave)
        return saved
    }

    fun addPriority(id: Long, priorityRequestDto: PriorityRequestDto): Category {
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        val toSave = this.categoryRepository.getReferenceById(id)
        toSave.priority = priorityRequestDto.priority
        val saved = this.categoryRepository.save(toSave)
        return saved
    }


    fun get(id: Long): Category {
        val found = this.categoryRepository.findById(id)
        if(found.isEmpty)
            throw IllegalStateException("Category not found")

        return found.get()
    }

    fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }


    fun delete(id: Long): Category{
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        val foundCategory = this.categoryRepository.findById(id)
        if(!foundCategory.isEmpty)
            this.categoryRepository.deleteById(id)

        return foundCategory.get()
    }

}