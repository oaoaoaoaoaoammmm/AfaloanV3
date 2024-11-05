package org.example.afaprocess.mappers

import org.assertj.core.api.Assertions.assertThat
import org.example.afaprocess.utils.createCreateProcessRequest
import org.example.afaprocess.utils.createProcess
import org.example.afaprocess.utils.createProcessDto
import org.junit.jupiter.api.Test

class ProcessMapperTest {

    private val processMapper = ProcessMapper()

    @Test
    fun `convertToDto should execute successfully`() {
        val process = createProcess()

        val result = processMapper.convertToDto(process)

        assertThat(process.debt).isEqualTo(result.debt)
        assertThat(process.comment).isEqualTo(result.comment)
        assertThat(process.status).isEqualTo(result.status)
    }

    @Test
    fun `convertToView should execute successfully`() {
        val process = createProcess()

        val result = processMapper.convertToView(process)

        assertThat(process.debt).isEqualTo(result.debt)
        assertThat(process.comment).isEqualTo(result.comment)
        assertThat(process.status).isEqualTo(result.status)
    }

    @Test
    fun `convert(request CreateProcessRequest) should execute successfully`() {
        val request = createCreateProcessRequest()

        val result = processMapper.convert(request)

        assertThat(request.debt).isEqualTo(result.debt)
        assertThat(request.comment).isEqualTo(result.comment)
    }

    @Test
    fun `convert(dto ProcessDto) should execute successfully`() {
        val dto = createProcessDto()

        val result = processMapper.convert(dto)

        assertThat(dto.debt).isEqualTo(result.debt)
        assertThat(dto.comment).isEqualTo(result.comment)
        assertThat(dto.status).isEqualTo(result.status)
    }
}