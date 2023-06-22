package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class WineGrape(

    @Id
    @Column(name = "wine_grape_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id")
    val wine: Wine,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grape_id")
    val grape: Grape,

)