package com.v10.ratatouille23.model

import javax.persistence.*

@Entity
@Table(name = "allergens")
class Allergens(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name", unique = true)
    var name: String
)