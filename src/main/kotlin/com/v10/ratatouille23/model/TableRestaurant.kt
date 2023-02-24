package com.v10.ratatouille23.model

import javax.persistence.*

@Entity
@Table(name = "table_restaurant")
class TableRestaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "seats")
    var seats: Long,

    @Column(name = "restaurant_id")
    var restaurantId: Long
)