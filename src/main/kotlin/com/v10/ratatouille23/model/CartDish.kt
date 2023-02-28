package com.v10.ratatouille23.model

import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cart_dish")
class CartDish(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "cart_id")
    var cartId: Long,

    @Column(name = "dish_id")
    var dishId: Long,

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: LocalDateTime?,

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updateAt: LocalDateTime?
)