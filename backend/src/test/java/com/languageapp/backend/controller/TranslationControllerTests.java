package com.languageapp.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.languageapp.backend.config.SecurityConfig;
import com.languageapp.backend.dto.request.TranslationRequestDto;
import com.languageapp.backend.dto.response.TranslationResponseDto;
import com.languageapp.backend.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(TranslationController.class)
@Import(SecurityConfig.class)
class TranslationControllerTests {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private TranslationService translationService;

  @Test
  void translate_WithValidRequest_Returns200() throws Exception {
    TranslationResponseDto expected = new TranslationResponseDto("a book");

    when(translationService.translate(any(TranslationRequestDto.class))).thenReturn(expected);

    mockMvc
        .perform(
            post("/api/translations")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "sourceLanguage": "FRENCH",
                      "text": "livre",
                      "context": "Je lis un livre."
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.translation").value("a book"));
  }

  @Test
  void translate_WithoutAuthentication_Returns403() throws Exception {
    mockMvc
        .perform(
            post("/api/translations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "sourceLanguage": "FRENCH",
                      "text": "livre",
                      "context": "Je lis un livre."
                    }
                    """))
        .andExpect(status().isForbidden());
  }

  @Test
  void translate_WithBlankText_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/api/translations")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "sourceLanguage": "FRENCH",
                      "text": "",
                      "context": "Je lis un livre."
                    }
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void translate_WithMissingSourceLanguage_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/api/translations")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "text": "livre",
                      "context": "Je lis un livre."
                    }
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void translate_WhenTranslationServiceThrows_Returns502() throws Exception {
    when(translationService.translate(any(TranslationRequestDto.class)))
        .thenThrow(
            new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Translation service unavailable"));

    mockMvc
        .perform(
            post("/api/translations")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "sourceLanguage": "FRENCH",
                      "text": "livre",
                      "context": "Je lis un livre."
                    }
                    """))
        .andExpect(status().isBadGateway());
  }
}
