package org.example.afaprocess.mappers

import org.example.afaprocess.controllers.processes.dtos.CreateProcessRequest
import org.example.afaprocess.controllers.processes.dtos.ProcessView
import org.example.afaprocess.controllers.processes.dtos.ProcessDto
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import org.springframework.stereotype.Component

@Component
class ProcessMapper {

    fun convertToDto(process: Process): ProcessDto {
        return ProcessDto(
            debt = process.debt,
            status = process.status,
            comment = process.comment,
            bidId = process.bidId!!
        )
    }

    fun convertToView(process: Process): ProcessView {
        return ProcessView(
            id = process.id!!,
            debt = process.debt,
            status = process.status,
            comment = process.comment,
        )
    }

    fun convert(request: CreateProcessRequest): Process {
        return Process(
            debt = request.debt,
            status = ProcessStatus.CREATED,
            comment = request.comment,
            bidId = request.bidId
        )
    }

    fun convert(dto: ProcessDto): Process {
        return Process(
            debt = dto.debt,
            comment = dto.comment,
            status = dto.status,
            bidId = dto.bidId
        )
    }
}