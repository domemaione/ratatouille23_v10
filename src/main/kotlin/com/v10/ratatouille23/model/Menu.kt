package com.v10.ratatouille23.model
import javax.persistence.*

@Entity
@Table(name = "menu")
class Menu (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "restaurant_id", unique = true)
    var restaurantId: Long
)
