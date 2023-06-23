package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class WinePairing(

    @Id
    @Column(name = "wine_pairing_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val pairing: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id")
    val wine: Wine,

)