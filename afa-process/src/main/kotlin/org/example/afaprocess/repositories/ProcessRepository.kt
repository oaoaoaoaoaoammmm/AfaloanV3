package org.example.afaprocess.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import java.util.*

interface ProcessRepository: JpaRepository<Process, UUID> {

    fun findProcessById(id: UUID): Process?

    fun findAllByStatus(status: ProcessStatus): List<Process>
}
