package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.reference.*;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference.*;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.PatchMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.PatchMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatchMongoAdapterTest {

    @Mock
    private PatchMongoRepository repository;

    @Spy
    private PatchMongoMapper mapper = Mappers.getMapper(PatchMongoMapper.class);

    @InjectMocks
    private PatchMongoAdapter adapter;

    @Test
    void findPatch_whenFound() {
        // Arrange
        String patchId = "16.22";
        String language = "fr_FR";
        PatchDocument document = createPatchDocument(patchId, language);
        Patch expectedPatch = createExpectedPatch(patchId, language);

        when(repository.findFirstByPatchIdAndLanguage(patchId, language))
                .thenReturn(Optional.of(document));

        // Act
        Optional<Patch> result = adapter.findPatch(patchId, language);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedPatch);
        verify(repository).findFirstByPatchIdAndLanguage(patchId, language);
    }

    @Test
    void findPatch_whenNotFound() {
        // Arrange
        String patchId = "99.99";
        String language = "fr_FR";

        when(repository.findFirstByPatchIdAndLanguage(patchId, language))
                .thenReturn(Optional.empty());

        // Act
        Optional<Patch> result = adapter.findPatch(patchId, language);

        // Assert
        assertThat(result).isEmpty();
        verify(repository).findFirstByPatchIdAndLanguage(patchId, language);
        verifyNoInteractions(mapper);
    }

    private PatchDocument createPatchDocument(String patchId, String language) {
        ChampionReferenceSubdocument champion = new ChampionReferenceSubdocument()
                .setTitle("l'Épée des Darkin")
                .setStats(Map.of("hp", 650.0f, "armor", 38.0f))
                .setTags(List.of("Fighter", "Tank"));
        champion.setId("266")
                .setName("Aatrox")
                .setImage("Aatrox.png")
                .setDescription("Épée des Darkin");

        ItemReferenceSubdocument item = new ItemReferenceSubdocument()
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

        SummonerSpellReferenceSubdocument spell = new SummonerSpellReferenceSubdocument()
                .setTooltip("Téléporte le champion vers le curseur.")
                .setCooldownBurn(300.0f);
        spell.setId("SummonerFlash")
                .setName("Saut éclair")
                .setImage("SummonerFlash.png")
                .setDescription("Téléporte votre champion sur une courte distance");

        AugmentReferenceSubdocument augment = new AugmentReferenceSubdocument()
                .setDataValues(Map.of("AttackSpeed", List.of(0.1f, 0.2f)));
        augment.setId("Augment_1")
                .setName("Symphonie de la guerre")
                .setImage("augment1.png")
                .setDescription("Confère Tempo mortel et Conquérant");

        RunePerkReferenceSubdocument perk = new RunePerkReferenceSubdocument()
                .setLongDescription("Description détaillée de Attaque soutenue");
        perk.setId("8005")
                .setName("Attaque soutenue")
                .setImage("PressTheAttack.png")
                .setDescription("Toucher 3 fois un champion inflige des dégâts bonus");

        RuneReferenceSubdocument rune = new RuneReferenceSubdocument()
                .setPerks(List.of(List.of(perk)));
        rune.setId("8000")
                .setName("Précision")
                .setImage("7201_Precision.png")
                .setDescription("Attaques améliorées et dégâts soutenus");

        QueueReferenceSubdocument queue = new QueueReferenceSubdocument()
                .setId("420")
                .setName("Classée Solo/Duo")
                .setDescription("Faille de l'invocateur 5v5");

        return new PatchDocument()
                .setId("mongo-id-123")
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

    private Patch createExpectedPatch(String patchId, String language) {
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
                .setId("mongo-id-123")
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