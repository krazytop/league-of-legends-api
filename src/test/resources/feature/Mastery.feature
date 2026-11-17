# language: fr
Fonctionnalité: Gestion des maîtrises de champions

  Scénario: Récupérer les maîtrises d'un invocateur existant
    Etant donné qu'on insère dans la collection "Mastery" le document suivant :
    """json
    {
      "puuid": "puuid-1234",
      "champions": [
        {
          "champion": "121",
          "level": 68,
          "points": 717400,
          "lastPlayTime": "2026-11-05T21:48:32Z"
        },
        {
          "champion": "18",
          "level": 57,
          "points": 599044,
          "lastPlayTime": "2026-11-08T14:55:32Z"
        }
      ]
    }
    """
    Quand j'appelle GET "/mastery/puuid-1234"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      [
        {
          "champion": "121",
          "level": 68,
          "points": 717400,
          "lastPlayTime": "2026-11-05T21:48:32Z"
        },
        {
          "champion": "18",
          "level": 57,
          "points": 599044,
          "lastPlayTime": "2026-11-08T14:55:32Z"
        }
      ]
      """

  Scénario: Récupérer les maîtrises d'un nouvel invocateur
    Etant donné que la collection "Mastery" est vide
    Quand j'appelle GET "/mastery/puuid-inconnu"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      []
      """

  Scénario: Mettre à jour les maîtrises d'un invocateur existant
    Etant donné qu'on insère dans la collection "Mastery" le document suivant :
      """json
      {
      "puuid": "puuid-1234",
      "champions": [
        {
          "champion": "121",
          "level": 60,
          "points": 340877,
          "lastPlayTime": "2025-04-06T07:34:12Z"
        }
      ]
    }
      """
    Et que l'API Riot renvoie les maîtrises suivantes pour le puuid "puuid-1234" :
    """json
    [
      {
        "puuid": "puuid-1234",
        "championId": 121,
        "championLevel": 68,
        "championPoints": 717400,
        "lastPlayTime": 1793915312000,
        "championPointsSinceLastLevel": 3800,
        "championPointsUntilNextLevel": 7200,
        "markRequiredForNextLevel": 2,
        "tokensEarned": 7,
        "championSeasonMilestone": 2,
        "milestoneGrades": [],
        "nextSeasonMilestone": {
          "requireGradeCounts": {
            "S-": 1
          },
          "rewardMarks": 2,
          "bonus": false,
          "totalGamesRequires": 1
        }
      },
      {
        "puuid": "puuid-1234",
        "championId": 18,
        "championLevel": 57,
        "championPoints": 599044,
        "lastPlayTime": 1794149732000,
        "championPointsSinceLastLevel": 6444,
        "championPointsUntilNextLevel": 4556,
        "markRequiredForNextLevel": 2,
        "tokensEarned": 18,
        "championSeasonMilestone": 2,
        "milestoneGrades": [
          "B+",
          "D+"
        ],
        "nextSeasonMilestone": {
          "requireGradeCounts": {
            "S-": 1
          },
          "rewardMarks": 2,
          "bonus": false,
          "totalGamesRequires": 1
        }
      }
    ]
    """
    Quand j'appelle PUT "/mastery/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Mastery" contient uniquement le document suivant :
      """json
      {
      "puuid": "puuid-1234",
      "champions": [
        {
          "champion": "121",
          "level": 68,
          "points": 717400,
          "lastPlayTime": "2026-11-05T21:48:32Z"
        },
        {
          "champion": "18",
          "level": 57,
          "points": 599044,
          "lastPlayTime": "2026-11-08T14:55:32Z"
        }
      ]
    }
      """

  Scénario: Mettre à jour les maîtrises d'un nouvel invocateur
    Etant donné que la collection "Mastery" est vide
    Et que l'API Riot renvoie les maîtrises suivantes pour le puuid "puuid-1234" :
    """json
    [
      {
        "puuid": "puuid-1234",
        "championId": 121,
        "championLevel": 68,
        "championPoints": 717400,
        "lastPlayTime": 1793915312000,
        "championPointsSinceLastLevel": 3800,
        "championPointsUntilNextLevel": 7200,
        "markRequiredForNextLevel": 2,
        "tokensEarned": 7,
        "championSeasonMilestone": 2,
        "milestoneGrades": [],
        "nextSeasonMilestone": {
          "requireGradeCounts": {
            "S-": 1
          },
          "rewardMarks": 2,
          "bonus": false,
          "totalGamesRequires": 1
        }
      },
      {
        "puuid": "puuid-1234",
        "championId": 18,
        "championLevel": 57,
        "championPoints": 599044,
        "lastPlayTime": 1794149732000,
        "championPointsSinceLastLevel": 6444,
        "championPointsUntilNextLevel": 4556,
        "markRequiredForNextLevel": 2,
        "tokensEarned": 18,
        "championSeasonMilestone": 2,
        "milestoneGrades": [
          "B+",
          "D+"
        ],
        "nextSeasonMilestone": {
          "requireGradeCounts": {
            "S-": 1
          },
          "rewardMarks": 2,
          "bonus": false,
          "totalGamesRequires": 1
        }
      }
    ]
    """
    Quand j'appelle PUT "/mastery/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Mastery" contient uniquement le document suivant :
      """json
      {
      "puuid": "puuid-1234",
      "champions": [
        {
          "champion": "121",
          "level": 68,
          "points": 717400,
          "lastPlayTime": "2026-11-05T21:48:32Z"
        },
        {
          "champion": "18",
          "level": 57,
          "points": 599044,
          "lastPlayTime": "2026-11-08T14:55:32Z"
        }
      ]
    }
      """