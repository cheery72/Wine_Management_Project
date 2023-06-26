package io.directional.wine.entity

import io.directional.wine.dto.CreateImporterRequest
import jakarta.persistence.*

@Entity
class Importer(

    @Id
    @Column(name = "importer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    var deleted: Boolean = false,

    @OneToMany(mappedBy = "importer")
    val wineGrape: List<Wine> = emptyList(),

    ) : BaseTime() {
        companion object {
            fun toEntity(createImporterRequest: CreateImporterRequest): Importer{
                return Importer(
                    name = createImporterRequest.importerName
                )
            }
        }

        fun setImporterInfo(createImporterRequest: CreateImporterRequest){
            this.name = createImporterRequest.importerName
        }
    }