package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.reference.*;
import com.krazytop.leagueoflegends.domain.service.PatchService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.PatchRestMapper;
import com.krazytop.leagueoflegends.model.generated.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatchControllerTest {

    @Mock
    private PatchService service;

    @Spy
    private PatchRestMapper mapper = Mappers.getMapper(PatchRestMapper.class);

    @InjectMocks
    private PatchController controller;

    @Test
    void getPatch_shouldReturnPatchDTO() {
        // Arrange
        String patchId = "16.22";
        String language = "fr_FR";
        Patch patch = createPatch(patchId, language);
        PatchDTO expectedDTO = createExpectedPatchDTO(patchId, language);

        when(service.getPatch(patchId, language)).thenReturn(patch);

        // Act
        ResponseEntity<PatchDTO> response = controller.getPatch(patchId, language);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedDTO);
        verify(service).getPatch(patchId, language);
    }

    private Patch createPatch(String patchId, String language) {
        ChampionReference champion = new ChampionReference()
                .setTitle("l'Épée des Darkin")
                .setStats(Map.of("hp", 650.0f, "armor", 38.0f))
                .setTags(List.of("Fighter", "Tank"));
        champion.setId("266")
                .setName("Aatrox")
                .setImage("Aatrox.png")
                .setDescription("Épée des Darkin");

        ItemReference item = new ItemReference()
                .setPlainText("Dégâts d'attaque et coups critiques")
                .setBaseGold(625)
                .setTotalGold(3400)
                .setTags(List.of("Damage", "CriticalStrike"))
                .setStats(Map.of("FlatPhysicalDamageMod", 70.0f))
                .setToItems(List.of("1028", "1027", "1008"))
                .setFromItems(List.of("1038", "1037", "1018"));
        item.setId("3031")
                .setName("Lame d'infini")
                .setImage("3031.png")
                .setDescription("Augmente les dégâts critiques");

        SummonerSpellReference spell = new SummonerSpellReference()
                .setTooltip("Téléporte le champion vers le curseur.")
                .setCooldownBurn(300.0f);
        spell.setId("SummonerFlash")
                .setName("Saut éclair")
                .setImage("SummonerFlash.png")
                .setDescription("Téléporte votre champion sur une courte distance");

        AugmentReference augment = new AugmentReference()
                .setDataValues(Map.of("AttackSpeed", List.of(0.1f, 0.2f)));
        augment.setId("Augment_1")
                .setName("Symphonie de la guerre")
                .setImage("augment1.png")
                .setDescription("Confère Tempo mortel et Conquérant");

        RunePerkReference perk = new RunePerkReference()
                .setLongDescription("Description détaillée de Attaque soutenue");
        perk.setId("8005")
                .setName("Attaque soutenue")
                .setImage("PressTheAttack.png")
                .setDescription("Toucher 3 fois un champion inflige des dégâts bonus");

        RuneReference rune = new RuneReference()
                .setPerks(List.of(List.of(perk)));
        rune.setId("8000")
                .setName("Précision")
                .setImage("7201_Precision.png")
                .setDescription("Attaques améliorées et dégâts soutenus");

        QueueReference queue = new QueueReference()
                .setId("420")
                .setName("Classée Solo/Duo")
                .setDescription("Faille de l'invocateur 5v5");

        return new Patch()
                .setId("16.22_fr_FR")
                .setPatchId(patchId)
                .setLanguage(language)
                .setSeason(16)
                .setChampions(List.of(champion))
                .setItems(List.of(item))
                .setSummonerSpells(List.of(spell))
                .setAugments(List.of(augment))
                .setRunes(List.of(rune))
                .setQueues(List.of(queue));
    }

    private PatchDTO createExpectedPatchDTO(String patchId, String language) {
        ChampionReferenceDTO champion = new ChampionReferenceDTO()
                .setId("266")
                .setName("Aatrox")
                .setImage("Aatrox.png")
                .setDescription("Épée des Darkin")
                .setTitle("l'Épée des Darkin")
                .setStats(Map.of("hp", 650.0f, "armor", 38.0f))
                .setTags(List.of("Fighter", "Tank"));

        ItemReferenceDTO item = new ItemReferenceDTO()
                .setId("3031")
                .setName("Lame d'infini")
                .setImage("3031.png")
                .setDescription("Augmente les dégâts critiques")
                .setPlainText("Dégâts d'attaque et coups critiques")
                .setBaseGold(625)
                .setTotalGold(3400)
                .setTags(List.of("Damage", "CriticalStrike"))
                .setStats(Map.of("FlatPhysicalDamageMod", 70.0f))
                .setToItems(List.of("1028", "1027", "1008"))
                .setFromItems(List.of("1038", "1037", "1018"));

        SummonerSpellReferenceDTO spell = new SummonerSpellReferenceDTO()
                .setId("SummonerFlash")
                .setName("Saut éclair")
                .setImage("SummonerFlash.png")
                .setDescription("Téléporte votre champion sur une courte distance")
                .setTooltip("Téléporte le champion vers le curseur.")
                .setCooldownBurn(300.0f);

        AugmentReferenceDTO augment = new AugmentReferenceDTO()
                .setId("Augment_1")
                .setName("Symphonie de la guerre")
                .setImage("augment1.png")
                .setDescription("Confère Tempo mortel et Conquérant")
                .setDataValues(Map.of("AttackSpeed", List.of(0.1f, 0.2f)));

        RunePerkReferenceDTO perk = new RunePerkReferenceDTO()
                .setId("8005")
                .setName("Attaque soutenue")
                .setImage("PressTheAttack.png")
                .setDescription("Toucher 3 fois un champion inflige des dégâts bonus")
                .setLongDescription("Description détaillée de Attaque soutenue");

        RuneReferenceDTO rune = new RuneReferenceDTO()
                .setId("8000")
                .setName("Précision")
                .setImage("7201_Precision.png")
                .setDescription("Attaques améliorées et dégâts soutenus")
                .setPerks(List.of(List.of(perk)));

        QueueReferenceDTO queue = new QueueReferenceDTO()
                .setId("420")
                .setName("Classée Solo/Duo")
                .setDescription("Faille de l'invocateur 5v5");

        return new PatchDTO()
                .setId("16.22_fr_FR")
                .setPatchId(patchId)
                .setLanguage(language)
                .setSeason(16)
                .setChampions(List.of(champion))
                .setItems(List.of(item))
                .setSummonerSpells(List.of(spell))
                .setAugments(List.of(augment))
                .setRunes(List.of(rune))
                .setQueues(List.of(queue));
    }
}