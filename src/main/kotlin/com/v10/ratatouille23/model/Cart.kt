package com.v10.ratatouille23.model

import com.v10.ratatouille23.utils.CartStatus
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cart")
class Cart (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "table_id")
    var tableId: Long,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: CartStatus,

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: LocalDateTime?,

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updateAt: LocalDateTime?
)