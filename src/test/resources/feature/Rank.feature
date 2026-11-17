# language: fr
Fonctionnalité: Gestion des classements des invocateurs

  Scénario: Récupérer les classements d'un invocateur existant
    Etant donné qu'on insère dans la collection "Rank" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2026-11-05T21:48:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 60,
                    "wins": 105,
                    "losses": 105
                  },
                  {
                    "date": "2026-11-08T14:55:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 84,
                    "wins": 106,
                    "losses": 105
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    Quand j'appelle GET "/rank/puuid-1234"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      [
        {
          "seasonOrSetNumber": 16,
          "queueRanks": [
            {
              "name": "RANKED_SOLO_5x5",
              "rankInformations": [
                {
                  "date": "2026-11-05T21:48:32Z",
                  "tier": "PLATINUM",
                  "rank": "III",
                  "leaguePoints": 60,
                  "wins": 105,
                  "losses": 105
                },
                {
                  "date": "2026-11-08T14:55:32Z",
                  "tier": "PLATINUM",
                  "rank": "III",
                  "leaguePoints": 84,
                  "wins": 106,
                  "losses": 105
                }
              ]
            }
          ]
        }
      ]
      """

  Scénario: Récupérer les classements d'un nouvel invocateur
    Etant donné que la collection "Rank" est vide
    Quand j'appelle GET "/rank/puuid-inconnu"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      []
      """

  Scénario: Mettre à jour les classements d'un invocateur existant
    Etant donné qu'on insère dans la collection "Metadata" le document suivant :
      """json
      {
        "id": 1,
        "currentSeason": 16
      }
      """
    Et qu'on insère dans la collection "Rank" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 15,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2025-11-10T18:00:00Z",
                    "tier": "EMERALD",
                    "rank": "IV",
                    "leaguePoints": 40,
                    "wins": 150,
                    "losses": 140
                  }
                ]
              }
            ]
          },
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2026-11-05T21:48:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 60,
                    "wins": 105,
                    "losses": 105
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    Et que l'API Riot renvoie les classements suivants pour le puuid "puuid-1234" :
      """json
      [
        {
          "queueType": "RANKED_SOLO_5X5",
          "tier": "PLATINUM",
          "rank": "III",
          "puuid": "puuid-1234",
          "leaguePoints": 84,
          "wins": 106,
          "losses": 105,
          "veteran": false,
          "inactive": false,
          "freshBlood": false,
          "hotStreak": false
        },
        {
          "queueType": "RANKED_FLEX_SR",
          "tier": "GOLD",
          "rank": "I",
          "puuid": "puuid-1234",
          "leaguePoints": 32,
          "wins": 12,
          "losses": 8,
          "veteran": false,
          "inactive": false,
          "freshBlood": false,
          "hotStreak": true
        }
      ]
      """
    Quand j'appelle PUT "/rank/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Rank" contient uniquement le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 15,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2025-11-10T18:00:00Z",
                    "tier": "EMERALD",
                    "rank": "IV",
                    "leaguePoints": 40,
                    "wins": 150,
                    "losses": 140
                  }
                ]
              }
            ]
          },
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2026-11-05T21:48:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 60,
                    "wins": 105,
                    "losses": 105
                  },
                  {
                    "date": "@INSTANT_ACTUEL@",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 84,
                    "wins": 106,
                    "losses": 105
                  }
                ]
              },
              {
                "name": "RANKED_FLEX_SR",
                "rankInformations": [
                  {
                    "date": "@INSTANT_ACTUEL@",
                    "tier": "GOLD",
                    "rank": "I",
                    "leaguePoints": 32,
                    "wins": 12,
                    "losses": 8
                  }
                ]
              }
            ]
          }
        ]
      }
      """

  Scénario: Ne pas mettre à jour les classements d'un invocateur existant si pas de nouvelles parties
    Etant donné qu'on insère dans la collection "Metadata" le document suivant :
      """json
      {
        "id": 1,
        "currentSeason": 16
      }
      """
    Et qu'on insère dans la collection "Rank" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2026-11-05T21:48:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 84,
                    "wins": 106,
                    "losses": 105
                  }
                ]
              }
            ]
          }
        ]
      }
      """
    Et que l'API Riot renvoie les classements suivants pour le puuid "puuid-1234" :
      """json
      [
        {
          "queueType": "RANKED_SOLO_5X5",
          "tier": "PLATINUM",
          "rank": "III",
          "puuid": "puuid-1234",
          "leaguePoints": 84,
          "wins": 106,
          "losses": 105,
          "veteran": false,
          "inactive": false,
          "freshBlood": false,
          "hotStreak": false
        }
      ]
      """
    Quand j'appelle PUT "/rank/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Rank" contient uniquement le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "2026-11-05T21:48:32Z",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 84,
                    "wins": 106,
                    "losses": 105
                  }
                ]
              }
            ]
          }
        ]
      }
      """

  Scénario: Mettre à jour les classements d'un nouvel invocateur
    Etant donné qu'on insère dans la collection "Metadata" le document suivant :
      """json
      {
        "id": 1,
        "currentSeason": 16
      }
      """
    Et que la collection "Rank" est vide
    Et que l'API Riot renvoie les classements suivants pour le puuid "puuid-1234" :
      """json
      [
        {
          "queueType": "RANKED_SOLO_5X5",
          "tier": "PLATINUM",
          "rank": "III",
          "puuid": "puuid-1234",
          "leaguePoints": 84,
          "wins": 106,
          "losses": 105,
          "veteran": false,
          "inactive": false,
          "freshBlood": false,
          "hotStreak": false
        }
      ]
      """
    Quand j'appelle PUT "/rank/puuid-1234"
    Alors le statut HTTP est 200
    Et la collection "Rank" contient uniquement le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "seasonOrSetRanks": [
          {
            "seasonOrSetNumber": 16,
            "queueRanks": [
              {
                "name": "RANKED_SOLO_5x5",
                "rankInformations": [
                  {
                    "date": "@INSTANT_ACTUEL@",
                    "tier": "PLATINUM",
                    "rank": "III",
                    "leaguePoints": 84,
                    "wins": 106,
                    "losses": 105
                  }
                ]
              }
            ]
          }
        ]
      }
      """