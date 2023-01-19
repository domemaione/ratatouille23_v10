package com.v10.ratatouille23.model

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "restaurant")
class Restaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "menu_id")
    var menuId: Long?,

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "update_at")
    var updateAt: Timestamp?,

    @Column(name = "created_at")
    var createdAt: Timestamp?
)