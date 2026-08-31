package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.TranslationRequestDto;
import com.languageapp.backend.dto.response.TranslationResponseDto;
import com.languageapp.backend.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/translations")
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping
    public TranslationResponseDto translate(@Valid @RequestBody TranslationRequestDto request) {
        return translationService.translate(request);
    }

}
