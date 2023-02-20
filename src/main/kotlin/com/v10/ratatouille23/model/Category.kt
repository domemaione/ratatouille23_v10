package com.v10.ratatouille23.model
import javax.persistence.*

@Entity
@Table(name = "category")
class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    var name: String
)