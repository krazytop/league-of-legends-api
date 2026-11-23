package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.domain.service.MatchService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MatchRestMapper;
import com.krazytop.leagueoflegends.model.generated.MatchPageDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private MatchService service;

    @Mock
    private MatchRestMapper mapper;

    @InjectMocks
    private MatchController controller;

    @Test
    void searchMatches_shouldReturnOkWithMappedPage() {
        // Arrange
        MatchSearchRequestDTO requestDTO = new MatchSearchRequestDTO();
        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<>();
        PageResult<Match> pageResult = new PageResult<>();
        MatchPageDTO expectedPageDTO = new MatchPageDTO();

        when(mapper.toModel(requestDTO)).thenReturn(pageRequest);
        when(service.searchMatches(pageRequest)).thenReturn(pageResult);
        when(mapper.toDTO(pageResult)).thenReturn(expectedPageDTO);

        // Act
        ResponseEntity<MatchPageDTO> response = controller.searchMatches(requestDTO);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(expectedPageDTO);
        verify(mapper).toModel(requestDTO);
        verify(service).searchMatches(pageRequest);
        verify(mapper).toDTO(pageResult);
    }

    @Test
    void updateMatches_shouldDelegateToServiceAndReturnOk() {
        // Arrange
        String puuid = "puuid-123";

        // Act
        ResponseEntity<Void> response = controller.updateMatches(puuid);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(service).updateMatches(puuid, false);
    }
}