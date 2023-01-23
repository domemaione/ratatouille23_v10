package com.v10.ratatouille23.model

import com.v10.ratatouille23.utils.DishType
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

    @Enumerated(EnumType.STRING)
    var type: DishType,

    @Column(name = "menu_id")
    var menuId: Long
)