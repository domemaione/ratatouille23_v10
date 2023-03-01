package com.v10.ratatouille23.model

import org.hibernate.annotations.Immutable
import org.hibernate.annotations.Subselect
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "bill_view")
class BillView(
    @Id
    @Column(name = "id")
    var id: Long,

    @Column(name = "cart_id")
    var cartId: Long,

    @Column(name = "total", precision = 10, scale = 2)
    var total: BigDecimal
)