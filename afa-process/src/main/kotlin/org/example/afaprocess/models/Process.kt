package org.example.afaprocess.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.afaprocess.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "processes")
data class Process(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "debt")
    val debt: BigDecimal,

    @Column(name = "comment")
    val comment: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: ProcessStatus,

    @Column(name = "bid_id")
    val bidId: UUID? = null
)
