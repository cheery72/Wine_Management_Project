package io.directional.wine.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.entity.*
import io.directional.wine.payload.dto.*

class RegionRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qRegion: QRegion = QRegion.region,
    private val qWinery: QWinery = QWinery.winery,
    private val qGrapeShare: QGrapeShare = QGrapeShare.grapeShare,
    private val qGrape: QGrape = QGrape.grape,
    private val qWine: QWine = QWine.wine,

    ) : RegionRepositoryCustom {
    override fun findByRegionTopList(regionId: Long): List<RegionParentDto> {

        val regions = mutableListOf<RegionParentDto>()
        val region = findParentRegion(regionId)

        if (region != null) {
            addParentRegions(region, regions)
            regions.reverse()
        }

        return regions
    }

    private fun addParentRegions(region: RegionParentDto, regions: MutableList<RegionParentDto>) {
        regions.add(region)

        val parentId = region.parentId

        if (parentId != null) {
            val parentRegion = findParentRegion(parentId)
            if (parentRegion != null) {
                addParentRegions(parentRegion, regions)
            }
        }
    }

    private fun findParentRegion(regionId: Long): RegionParentDto? {
        val qParentRegion: QRegion = QRegion("parentRegion")

        return jpaQueryFactory
            .select(
                QRegionParentDto(
                    qRegion.id,
                    qRegion.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.parent.id
                )
            )
            .from(qRegion)
            .leftJoin(qRegion.parent, qParentRegion)
            .where(qRegion.id.eq(regionId).and(qRegion.deleted.isFalse))
            .fetchFirst()
    }

    override fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto? {

        return jpaQueryFactory
            .select(
                QRegionDetailsDto(
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                    qGrape.nameEnglish,
                    qGrape.nameKorean,
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qWine.nameEnglish,
                    qWine.nameKorean,
                    qRegion.id
                )
            ).from(qRegion)
            .join(qRegion.grapeShare, qGrapeShare)
            .join(qRegion.winery, qWinery)
            .join(qWinery.wine, qWine)
            .where(
                qRegion.deleted.isFalse.and(
                    qGrape.deleted.isFalse
                        .and(qWinery.deleted.isFalse.and(qWine.deleted.isFalse))
                )
                    .and(qRegion.nameEnglish.eq(regionName).or(qRegion.nameKorean.eq(regionName)))
                    .and(qRegion.parent.nameEnglish.eq(parentRegion).or(qRegion.parent.nameKorean.eq(parentRegion)))
            )
            .orderBy(qRegion.nameEnglish.asc())
            .fetchFirst()
    }

    override fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesDto> {

        return jpaQueryFactory
            .select(
                QRegionNamesDto(
                    qRegion.nameEnglish,
                    qRegion.nameKorean
                )
            ).from(qRegion)
            .where(
                qRegion.deleted.isFalse
                    .and(
                        qRegion.nameEnglish.eq(regionName)
                            .or(qRegion.nameKorean.eq(regionName))
                            .and(
                                qRegion.parent.nameEnglish.eq(parentRegion)
                                    .or(qRegion.parent.nameKorean.eq(parentRegion))
                            )
                    )
            )
            .orderBy(qRegion.nameEnglish.asc())
            .fetch()
    }
}