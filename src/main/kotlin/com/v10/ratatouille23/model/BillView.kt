package com.v10.ratatouille23.model

import org.hibernate.annotations.Immutable
import org.hibernate.annotations.Subselect
import javax.persistence.*

@Entity
@Immutable
class BillView(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    var id: Long?,

    @Column(name = "cart_id")
    var cartId: Long,

    @Column(name = "total")
    var total: Long
)