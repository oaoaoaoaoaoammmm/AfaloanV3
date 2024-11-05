package org.example.afafile.controllers

import org.assertj.core.api.Assertions.assertThat
import org.example.afafile.BaseIntegrationTest
import org.example.afafile.controllers.dtos.UploadFileResponse
import org.example.afafile.utils.toObject
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FileControllerTest : BaseIntegrationTest() {

    @Test
    fun `upload should return CREATED`() {
        uploadDocument()
    }

    @Test
    fun `download should execute OK`() {
        val uploadFileResponse = uploadDocument()
        val mvcResult = mockMvc.perform(
            get("/files")
                .param("path", uploadFileResponse.path)
        ).andExpect(status().isOk).andReturn()

        assertThat(mvcResult.response.contentType).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE)
    }

    @Test
    fun `delete should return NO_CONTENT`() {
        val uploadFileResponse = uploadDocument()
        mockMvc.perform(
            delete("/files")
                .param("path", uploadFileResponse.path)
        ).andExpect(status().isNoContent)
    }

    @Test
    fun `findPreviews should return OK`() {
        val uploadFileResponse = uploadDocument()
        mockMvc.perform(
            get("/files/previews")
                .param("path", uploadFileResponse.path)
        ).andExpectAll(
            status().isOk,
            jsonPath("$").isNotEmpty
        )
    }

    @Test
    fun `findDocumentUrl should return OK`() {
        val uploadFileResponse = uploadDocument()
        mockMvc.perform(
            get("/files/urls")
                .param("path", uploadFileResponse.path)
        ).andExpectAll(
            status().isOk,
            jsonPath("$").isNotEmpty
        )
    }

    private fun uploadDocument(): UploadFileResponse {
        val file = MockMultipartFile(
            "file",
            "document.png",
            MediaType.IMAGE_PNG_VALUE,
            javaClass.getResourceAsStream("/document.png")!!
        )
        return mockMvc.perform(
            multipart("/files").file(file)
        ).andExpect(status().isCreated).andReturn().response.contentAsString.toObject()
    }
}