package com.v10.ratatouille23.model

import javax.persistence.*

@Entity
@Table(name = "order")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "table_id")
    var tableId: Long
)