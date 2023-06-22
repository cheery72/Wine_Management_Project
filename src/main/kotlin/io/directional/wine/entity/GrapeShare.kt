package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class GrapeShare(

    @Id
    @Column(name = "grape_share_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val share: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grape_id")
    val grape: Grape,

)
