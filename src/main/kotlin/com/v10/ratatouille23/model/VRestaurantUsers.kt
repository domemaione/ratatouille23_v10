package com.v10.ratatouille23.model

import javax.persistence.*

@Entity
@Table(name = "v_restaurant_users")
class VRestaurantUsers (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    var name: String?,

    @Column(name = "surname")
    private val surname: String?,

    @Column(name = "email")
    private val email: String?,

    @Column(name = "password")
    private val password: String?,

    @Column(name = "role")
    private val role: String?,

    @Column(name = "restaurant_id")
    private val restaurantId: Long?,

    @Column(name = "enabled")
    private val enabled: Byte?,

    @Column(name = "restaurantName")
    private val restaurantName: String? = null
)