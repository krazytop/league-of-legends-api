# language: fr
Fonctionnalité: Gestion des boards de comparaison d'invocateurs

  Scénario: Créer un nouveau board vide
    Etant donné que la collection "Board" est vide
    Quand j'appelle POST "/board"
    Alors le statut HTTP est 201
    Et la réponse JSON contient :
      """json
      {
        "name": "Nouveau Board",
        "puuids": []
      }
      """

  Scénario: Récupérer un board existant par son identifiant
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Team Ranked",
        "puuids": [
          "puuid-1234",
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Quand j'appelle GET "/board/board-123"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "id": "board-123",
        "name": "Team Ranked",
        "puuids": [
          "puuid-1234",
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """

  Scénario: Récupérer un board inexistant
    Etant donné que la collection "Board" est vide
    Quand j'appelle GET "/board/board-inconnu"
    Alors le statut HTTP est 404
    Et la réponse JSON est :
      """json
      {
        "detail": "Le board board-inconnu n'existe pas",
        "instance": "/board/board-inconnu",
        "status": 404,
        "title": "Not Found",
        "type": "about:blank"
      }
      """

  Scénario: Renommer partiellement un board existant
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Nouveau Board",
        "puuids": [
          "puuid-1234"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Quand j'appelle PATCH "/board/board-123" avec le body :
      """json
      {
        "name": "  Les Tryharders  "
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "id": "board-123",
        "name": "Les Tryharders",
        "puuids": [
          "puuid-1234"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Et la collection "Board" contient uniquement le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Les Tryharders",
        "puuids": [
          "puuid-1234"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """

  Scénario: Renommer un board inexistant
    Etant donné que la collection "Board" est vide
    Quand j'appelle PATCH "/board/board-inconnu" avec le body :
      """json
      {
        "name": "Les Tryharders"
      }
      """
    Alors le statut HTTP est 404
    Et la réponse JSON est :
      """json
      {
        "detail": "Le board board-inconnu n'existe pas",
        "instance": "/board/board-inconnu",
        "status": 404,
        "title": "Not Found",
        "type": "about:blank"
      }
      """

  Scénario: Supprimer un board existant
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Board à supprimer",
        "puuids": [],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Quand j'appelle DELETE "/board/board-123"
    Alors le statut HTTP est 200
    Et la collection "Board" est vide

  Scénario: Ajouter un invocateur déjà importé à un board
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Et qu'on insère dans la collection "Summoner" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "name": "Krazytop",
        "tag": "EUW",
        "level": 450,
        "icon": "29",
        "region": "EUW1",
        "updateDate": "2026-11-22T16:00:00Z",
        "spentTime": 123456,
        "playedSeasons": [16]
      }
      """
    Quand j'appelle POST "/board/board-123/summoner/EUW/Krazytop"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "puuid": "puuid-1234",
        "name": "Krazytop",
        "tag": "EUW",
        "level": 450,
        "icon": "29",
        "region": "EUW1",
        "updateDate": "2026-11-22T16:00:00Z",
        "spentTime": 123456,
        "playedSeasonsOrSets": [16]
      }
      """
    Et la collection "Board" contient uniquement le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-1234"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """

  Scénario: Ajouter un invocateur déjà présent sur le board
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-1234"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Et qu'on insère dans la collection "Summoner" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "name": "Krazytop",
        "tag": "EUW",
        "level": 450,
        "icon": "29",
        "region": "EUW1",
        "updateDate": "2026-11-22T16:00:00Z",
        "spentTime": 123456,
        "playedSeasons": [16]
      }
      """
    Quand j'appelle POST "/board/board-123/summoner/EUW/Krazytop"
    Alors le statut HTTP est 409
    Et la réponse JSON est :
      """json
      {
        "detail": "L'invocateur puuid-1234 est déjà dans le board board-123",
        "instance": "/board/board-123/summoner/EUW/Krazytop",
        "status": 409,
        "title": "Conflict",
        "type": "about:blank"
      }
      """

  Scénario: Ajouter un invocateur à un board ayant atteint sa capacité maximale
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team Pleine",
        "puuids": [
          "puuid-1",
          "puuid-2",
          "puuid-3",
          "puuid-4",
          "puuid-5",
          "puuid-6",
          "puuid-7",
          "puuid-8",
          "puuid-9",
          "puuid-10"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Et qu'on insère dans la collection "Summoner" le document suivant :
      """json
      {
        "puuid": "puuid-11",
        "name": "OnziemeJoueur",
        "tag": "EUW",
        "level": 200,
        "icon": "10",
        "region": "EUW1",
        "updateDate": "2026-11-22T16:00:00Z",
        "spentTime": 50000,
        "playedSeasons": [16]
      }
      """
    Quand j'appelle POST "/board/board-123/summoner/EUW/OnziemeJoueur"
    Alors le statut HTTP est 400
    Et la réponse JSON est :
      """json
      {
        "detail": "L'invocateur puuid-11 ne peux pas être ajouté sur le board board-123 car le nombre d'invocateur maximum est atteint",
        "instance": "/board/board-123/summoner/EUW/OnziemeJoueur",
        "status": 400,
        "title": "Bad Request",
        "type": "about:blank"
      }
      """

  Scénario: Ajouter un invocateur à un board inexistant
    Etant donné que la collection "Board" est vide
    Quand j'appelle POST "/board/board-inconnu/summoner/EUW/Krazytop"
    Alors le statut HTTP est 404
    Et la réponse JSON est :
      """json
      {
        "detail": "Le board board-inconnu n'existe pas",
        "instance": "/board/board-inconnu/summoner/EUW/Krazytop",
        "status": 404,
        "title": "Not Found",
        "type": "about:blank"
      }
      """

  Scénario: Retirer un invocateur présent sur un board
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-1234",
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Quand j'appelle DELETE "/board/board-123/summoner/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Board" contient uniquement le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """

  Scénario: Retirer un invocateur absent d'un board existant
    Etant donné qu'on insère dans la collection "Board" le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """
    Quand j'appelle DELETE "/board/board-123/summoner/puuid-absent"
    Alors le statut HTTP est 200
    Et la collection "Board" contient uniquement le document suivant :
      """json
      {
        "id": "board-123",
        "name": "Ma Team",
        "puuids": [
          "puuid-5678"
        ],
        "updateDate": "2026-11-22T16:00:00Z"
      }
      """

  Scénario: Retirer un invocateur d'un board inexistant
    Etant donné que la collection "Board" est vide
    Quand j'appelle DELETE "/board/board-inconnu/summoner/puuid-1234"
    Alors le statut HTTP est 404
    Et la réponse JSON est :
      """json
      {
        "detail": "Le board board-inconnu n'existe pas",
        "instance": "/board/board-inconnu/summoner/puuid-1234",
        "status": 404,
        "title": "Not Found",
        "type": "about:blank"
      }
      """

  Scénario: Rafraîchir un board inexistant
    Etant donné que la collection "Board" est vide
    Quand j'appelle PUT "/board/board-inconnu"
    Alors le statut HTTP est 404
    Et la réponse JSON est :
      """json
      {
        "detail": "Le board board-inconnu n'existe pas",
        "instance": "/board/board-inconnu",
        "status": 404,
        "title": "Not Found",
        "type": "about:blank"
      }
      """