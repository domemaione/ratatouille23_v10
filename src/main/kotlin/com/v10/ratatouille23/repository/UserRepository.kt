package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {}