package com.v10.ratatouille23.model

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "dish")
class Dish (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "cost")
    val cost: Double = 0.0,

    @Column(name = "menu_id")
    var menuId: Long,

    @Column(name = "category_id")
    var categoryId: Long
)