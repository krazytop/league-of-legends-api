# language: fr
Fonctionnalité: Gestion des invocateurs

  Contexte:
    Etant donné qu'on insère dans la collection "Summoner" le document suivant :
      """json
      {
        "puuid": "stored-puuid-01",
        "name": "Krazytop",
        "tag": "EUW",
        "region": "EUW1",
        "level": 450,
        "icon": 4567,
        "updateDate": "2026-11-17T12:00:00Z",
        "spentTime": 123456,
        "playedSeasons": [13, 14, 15]
      }
      """

  # --- GET /summoner/{puuid} ---

  Scénario: Récupérer un invocateur existant en base par son puuid
    Quand j'appelle GET "/summoner/stored-puuid-01"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "stored-puuid-01",
        "name": "Krazytop",
        "tag": "EUW",
        "region": "EUW1",
        "level": 450,
        "icon": 4567,
        "updateDate": "2026-11-17T12:00:00Z",
        "spentTime": 123456,
        "playedSeasonsOrSets": [13, 14, 15]
      }
      """

  Scénario: Récupérer un invocateur absent de la base mais présent chez Riot par son puuid
    Etant donné que l'API Riot renvoie le compte suivant pour le puuid "riot-puuid-02" :
      """json
      {
        "puuid": "riot-puuid-02",
        "gameName": "Doublelift",
        "tagLine": "NA1"
      }
      """
    Et que l'API Riot renvoie la region suivante pour le compte associé au puuid "riot-puuid-02" :
      """json
      {
        "puuid": "riot-puuid-02",
        "game": "lol",
        "region": "na1"
      }
      """
    Et que l'API Riot renvoie l'invocateur suivant pour le puuid "riot-puuid-02" sur la région "NA1" :
      """json
      {
        "puuid": "riot-puuid-02",
        "profileIconId": 1234,
        "revisionDate": 1731844800000,
        "summonerLevel": 600
      }
      """
    Quand j'appelle GET "/summoner/riot-puuid-02"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "riot-puuid-02",
        "name": "Doublelift",
        "tag": "NA1",
        "region": "NA1",
        "level": 600,
        "icon": 1234,
        "updateDate": null,
        "spentTime": 0,
        "playedSeasonsOrSets": []
      }
      """

  Scénario: Récupérer un invocateur inexistant par son puuid retourne 404
    Etant donné que l'API Riot ne renvoie aucun compte pour le puuid "unknown-puuid"
    Quand j'appelle GET "/summoner/unknown-puuid"
    Alors le statut HTTP est 404

  # --- GET /summoner/{tag}/{name} ---

  Scénario: Récupérer un invocateur existant en base par son tag et son nom
    Quand j'appelle GET "/summoner/EUW/Krazytop"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "stored-puuid-01",
        "name": "Krazytop",
        "tag": "EUW",
        "region": "EUW1",
        "level": 450,
        "icon": 4567,
        "updateDate": "2026-11-17T12:00:00Z",
        "spentTime": 123456,
        "playedSeasonsOrSets": [13, 14, 15]
      }
      """

  Scénario: Récupérer un invocateur absent de la base mais présent chez Riot par son tag et son nom
    Etant donné que l'API Riot renvoie le compte suivant pour le nom "Doublelift" et le tag "NA1" :
      """json
      {
        "puuid": "riot-puuid-02",
        "gameName": "Doublelift",
        "tagLine": "NA1"
      }
      """
    Et que l'API Riot renvoie la region suivante pour le compte associé au puuid "riot-puuid-02" :
      """json
      {
        "puuid": "riot-puuid-02",
        "game": "lol",
        "region": "na1"
      }
      """
    Et que l'API Riot renvoie l'invocateur suivant pour le puuid "riot-puuid-02" sur la région "NA1" :
      """json
      {
        "puuid": "riot-puuid-02",
        "profileIconId": 1234,
        "revisionDate": 1731844800000,
        "summonerLevel": 600
      }
      """
    Quand j'appelle GET "/summoner/NA1/Doublelift"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "riot-puuid-02",
        "name": "Doublelift",
        "tag": "NA1",
        "region": "NA1",
        "level": 600,
        "icon": 1234,
        "updateDate": null,
        "spentTime": 0,
        "playedSeasonsOrSets": []
      }
      """

  Scénario: Récupérer un invocateur inexistant par son tag et son nom retourne 404
    Etant donné que l'API Riot ne renvoie aucun compte pour le nom "Unknown" et le tag "EUW"
    Quand j'appelle GET "/summoner/EUW/Unknown"
    Alors le statut HTTP est 404

  # --- POST /summoner/{puuid} ---

  Scénario: Importer un nouvel invocateur en base via POST
    Etant donné que l'API Riot renvoie le compte suivant pour le puuid "riot-puuid-02" :
      """json
      {
        "puuid": "riot-puuid-02",
        "gameName": "Doublelift",
        "tagLine": "NA1"
      }
      """
    Et que l'API Riot renvoie la region suivante pour le compte associé au puuid "riot-puuid-02" :
      """json
      {
        "puuid": "riot-puuid-02",
        "game": "lol",
        "region": "na1"
      }
      """
    Et que l'API Riot renvoie l'invocateur suivant pour le puuid "riot-puuid-02" sur la région "NA1" :
      """json
      {
        "puuid": "riot-puuid-02",
        "profileIconId": 1234,
        "revisionDate": 1731844800000,
        "summonerLevel": 600
      }
      """
    Quand j'appelle POST "/summoner/riot-puuid-02"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "riot-puuid-02",
        "name": "Doublelift",
        "tag": "NA1",
        "region": "NA1",
        "level": 600,
        "icon": 1234,
        "updateDate": "@INSTANT_ACTUEL@",
        "spentTime": 0,
        "playedSeasonsOrSets": []
      }
      """
    Et la collection "Summoner" contient uniquement les documents suivants :
      """json
      [
        {
          "puuid": "stored-puuid-01",
          "name": "Krazytop",
          "tag": "EUW",
          "region": "EUW1",
          "level": 450,
          "icon": 4567,
          "updateDate": "2026-11-17T12:00:00Z",
          "spentTime": 123456,
          "playedSeasons": [13, 14, 15]
        },
        {
          "puuid": "riot-puuid-02",
          "name": "Doublelift",
          "tag": "NA1",
          "region": "NA1",
          "level": 600,
          "icon": 1234,
          "updateDate": "@INSTANT_ACTUEL@",
          "spentTime": 0,
          "playedSeasons": []
        }
      ]
      """

  Scénario: Mettre à jour un invocateur existant en conservant ses statistiques cumulées
    Etant donné que l'API Riot renvoie le compte suivant pour le puuid "stored-puuid-01" :
      """json
      {
        "puuid": "stored-puuid-01",
        "gameName": "NewKrazytop",
        "tagLine": "FR1"
      }
      """
    Et que l'API Riot renvoie la region suivante pour le compte associé au puuid "stored-puuid-01" :
      """json
      {
        "puuid": "stored-puuid-01",
        "game": "lol",
        "region": "euw1"
      }
      """
    Et que l'API Riot renvoie l'invocateur suivant pour le puuid "stored-puuid-01" sur la région "EUW1" :
      """json
      {
        "puuid": "stored-puuid-01",
        "profileIconId": 9999,
        "revisionDate": 1731844800000,
        "summonerLevel": 455
      }
      """
    Quand j'appelle POST "/summoner/stored-puuid-01"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "stored-puuid-01",
        "name": "NewKrazytop",
        "tag": "FR1",
        "region": "EUW1",
        "level": 455,
        "icon": 9999,
        "updateDate": "@INSTANT_ACTUEL@",
        "spentTime": 123456,
        "playedSeasonsOrSets": [13, 14, 15]
      }
      """
    Et la collection "Summoner" contient uniquement le document suivant :
      """json
      {
        "puuid": "stored-puuid-01",
        "name": "NewKrazytop",
        "tag": "FR1",
        "region": "EUW1",
        "level": 455,
        "icon": 9999,
        "updateDate": "@INSTANT_ACTUEL@",
        "spentTime": 123456,
        "playedSeasons": [13, 14, 15]
      }
      """

  Scénario: Mettre à jour un invocateur inexistant retourne 404
    Etant donné que l'API Riot ne renvoie aucun compte pour le puuid "unknown-puuid"
    Quand j'appelle POST "/summoner/unknown-puuid"
    Alors le statut HTTP est 404