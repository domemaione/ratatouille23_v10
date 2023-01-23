package com.v10.ratatouille23.model

import com.v10.ratatouille23.utils.UserRoles
import javax.persistence.*

@Entity
@Table(name = "user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "surname")
    var surname: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: UserRoles,

    @Column(name = "restaurant_id")
    var restaurantId: Long?,

    @Column(name = "enabled")
    var enabled: Boolean
)