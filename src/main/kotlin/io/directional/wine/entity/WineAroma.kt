package io.directional.wine.entity

import jakarta.persistence.*

@Entity
class WineAroma(

    @Id
    @Column(name = "wine_aroma_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val aroma: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id")
    val wine: Wine,

)