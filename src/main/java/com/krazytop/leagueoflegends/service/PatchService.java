package com.krazytop.leagueoflegends.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krazytop.leagueoflegends.entity.Metadata;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.PatchMapper;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.nomenclature.*;
import com.krazytop.leagueoflegends.repository.PatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatchService.class);

    private static final List<String> SUPPORTED_LANGUAGES = List.of("fr_FR", "en_GB");

    private final PatchRepository patchNomenclatureRepository;
    private final MetadataService metadataService;
    private final PatchMapper patchMapper;

    @Autowired
    public PatchService(PatchRepository patchNomenclatureRepository, MetadataService metadataService, PatchMapper patchMapper) {
        this.patchNomenclatureRepository = patchNomenclatureRepository;
        this.metadataService = metadataService;
        this.patchMapper = patchMapper;
    }

    public Optional<Patch> getPatch(String patchId, String language) {
        return patchNomenclatureRepository.findFirstByPatchIdAndLanguage(patchId, language);
    }

    public PatchDTO getPatchDTO(String patchId, String language) {
        return patchMapper.toDTO(getPatch(patchId, language).orElseThrow(() -> new CustomException(ApiErrorEnum.PATCH_NOT_FOUND)));
    }

    public void updateAllPatches() throws IOException, URISyntaxException {
        updateCurrentPatchVersion();
        List<String> allPatchesVersion = getAllPatchesVersion();
        Metadata metadata = metadataService.getMetadata().orElse(new Metadata());
        for (String patchVersion : allPatchesVersion) {
            for (String language : SUPPORTED_LANGUAGES) {
                if (getPatch(removeFixVersion(patchVersion), language).isEmpty()) {
                    updatePatchData(patchVersion, language);
                }
            }
            metadata.getAllPatches().add(removeFixVersion(patchVersion));
            metadata.setCurrentSeason(patchNomenclatureRepository.findLatestPatch().getSeason());
            metadataService.saveMetadata(metadata);
        }
    }

    public void updateCurrentPatchVersion() throws IOException, URISyntaxException {
        String uri = "https://ddragon.leagueoflegends.com/realms/euw.json";
        URL url = new URI(uri).toURL();
        Metadata metadata = metadataService.getMetadata().orElse(new Metadata());
        String currentPatch = new ObjectMapper().readTree(url).get("v").asText().replaceAll("^(\\d+\\.\\d+).*", "$1");
        if (!Objects.equals(metadata.getCurrentPatch(), currentPatch)) {
            metadata.setCurrentPatch(currentPatch);
            metadataService.saveMetadata(metadata);
        }
    }

    public List<String> getAllPatchesVersion() throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String uri = "https://ddragon.leagueoflegends.com/api/versions.json";
        JsonNode data = mapper.readTree(new URI(uri).toURL());
        List<String> allPatchesVersion = mapper.convertValue(data, new TypeReference<>() {});
        return allPatchesVersion.stream()
                .filter(version -> !version.contains("lol"))
                .filter(version -> !version.startsWith("0."))
                .toList();
    }

    private void updatePatchData(String patchVersion, String language) throws IOException, URISyntaxException {
        String shortVersion = removeFixVersion(patchVersion);
        LOGGER.info("Update LOL patch {} for language {}", shortVersion, language);
        Patch patch = new Patch(shortVersion, language);
        patch.setChampions(getPatchChampions(patchVersion, language));
        patch.setSummonerSpells(getPatchSummonerSpells(patchVersion, language));
        patch.setItems(getPatchItems(patchVersion, language));
        if (isVersionAfterAnOther(shortVersion, "8.0")) patch.setRunes(getPatchRunes(patchVersion, language));
        if (isVersionAfterAnOther(shortVersion, "13.13")) patch.setAugments(getPatchAugments(shortVersion, language));
        patch.setSeason(Integer.valueOf(shortVersion.split("\\.")[0]));
        patch.setQueues(getPatchQueues(isVersionAfterAnOther(shortVersion, "13.13") ? shortVersion : "13.14", language));
        patchNomenclatureRepository.save(patch);
    }

    private List<ChampionNomenclature> getPatchChampions(String version, String language) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/%s/champion.json", version, language);
        Map<String, ChampionNomenclature> nomenclaturesMap = mapper.convertValue(mapper.readTree(new URI(url).toURL()).get("data"), new TypeReference<>() {});
        return nomenclaturesMap.values().stream().toList();
    }

    private List<SummonerSpellNomenclature> getPatchSummonerSpells(String version, String language) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/%s/summoner.json", version, language);
        Map<String, SummonerSpellNomenclature> nomenclaturesMap = mapper.convertValue(mapper.readTree(new URI(url).toURL()).get("data"), new TypeReference<>() {});
        return nomenclaturesMap.values().stream().toList();
    }

    private List<ItemNomenclature> getPatchItems(String version, String language) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/%s/item.json", version, language);
        Map<String, ItemNomenclature> nomenclaturesMap = mapper.convertValue(mapper.readTree(new URI(url).toURL()).get("data"), new TypeReference<>() {});
        nomenclaturesMap.forEach((id, nomenclature) -> nomenclature.setId(id));
        return nomenclaturesMap.values().stream().toList();
    }

    private List<AugmentNomenclature> getPatchAugments(String version, String language) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://raw.communitydragon.org/%s/cdragon/arena/%s.json", version, language.toLowerCase());
        return mapper.convertValue(mapper.readTree(new URI(url).toURL()).get("augments"), new TypeReference<>() {});
    }

    private List<RuneNomenclature> getPatchRunes(String version, String language) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/%s/runesReforged.json", version, language);
        return mapper.convertValue(mapper.readTree(new URI(url).toURL()), new TypeReference<>() {});
    }

    public List<QueueNomenclature> getPatchQueues(String patchVersion, String language) throws IOException, URISyntaxException {
        if (Objects.equals(patchVersion, "13.2") || Objects.equals(patchVersion, "13.3")) patchVersion = "13.4";
        if (Objects.equals(patchVersion, "11.7")) patchVersion = "11.8";
        ObjectMapper mapper = new ObjectMapper();
        String queueUri = String.format("https://raw.communitydragon.org/%s/plugins/rcp-be-lol-game-data/global/%s/v1/queues.json", patchVersion, language.toLowerCase());
        if (isVersionAfterAnOther(patchVersion, "14.12")) {
            return mapper.convertValue(mapper.convertValue(new ObjectMapper().readTree(new URI(queueUri).toURL()), new TypeReference<>() {}), new TypeReference<>() {});
        } else {
            Map<String, QueueNomenclature> nomenclaturesMap = mapper.convertValue(new ObjectMapper().readTree(new URI(queueUri).toURL()), new TypeReference<>() {});
            nomenclaturesMap.forEach((id, nomenclature) -> nomenclature.setId(id));
            return nomenclaturesMap.values().stream().toList();
        }
    }

    public String removeFixVersion(String version) {
        return version.substring(0, version.lastIndexOf('.'));
    }

    public boolean isVersionAfterAnOther(String version, String referentVersion) {
        String[] v1 = version.split("\\.");
        String[] v2 = referentVersion.split("\\.");

        int majorDiff = Integer.parseInt(v1[0]) - Integer.parseInt(v2[0]);
        return majorDiff != 0 ? majorDiff > 0 : Integer.parseInt(v1[1]) > Integer.parseInt(v2[1]);
    }

}