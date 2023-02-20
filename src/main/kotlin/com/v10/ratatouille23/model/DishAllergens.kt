package com.v10.ratatouille23.model
import javax.persistence.*

@Entity
@Table(name = "dish_allergens")
class DishAllergens(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "dish_id")
    var dishId: Long,

    @Column(name = "allergen_id")
    var allergenId: Long
)