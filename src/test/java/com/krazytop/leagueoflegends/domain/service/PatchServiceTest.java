package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.reference.Patch;
import com.krazytop.leagueoflegends.domain.port.out.patch.PatchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatchServiceTest {

    @Mock
    private PatchRepositoryPort repositoryPort;

    @InjectMocks
    private PatchService service;

    @Test
    void getPatch_whenFound() {
        String patchId = "16.22";
        String language = "fr_FR";
        Patch expectedPatch = new Patch()
                .setId("16.22_fr_FR")
                .setPatchId(patchId)
                .setLanguage(language);

        when(repositoryPort.findPatch(patchId, language))
                .thenReturn(Optional.of(expectedPatch));

        Patch result = service.getPatch(patchId, language);

        assertThat(result).isSameAs(expectedPatch);
        verify(repositoryPort).findPatch(patchId, language);
    }

    @Test
    void getPatch_whenNotFound_shouldThrowResourceNotFoundException() {
        String patchId = "99.99";
        String language = "fr_FR";

        when(repositoryPort.findPatch(patchId, language))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getPatch(patchId, language))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Le patch 99.99 en langue fr_FR n'existe pas ou n'est pas encore disponible");

        verify(repositoryPort).findPatch(patchId, language);
    }
}