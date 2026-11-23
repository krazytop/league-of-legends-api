# language: fr
Fonctionnalité: Recherche paginée des matchs d'un invocateur (Match)

  Contexte:
    Etant donné qu'on insère dans la collection "Match" les documents suivants :
      """json
      [
        {
          "id": "EUW1_7939943973",
          "version": "16.15",
          "season": 16,
          "datetime": "2026-08-04T13:28:32.620Z",
          "duration": 2268,
          "remake": false,
          "queue": "420",
          "owners": [
            "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw"
          ],
          "teams": [
            {
              "id": "100",
              "hasWin": false,
              "objectives": {
                "baronKills": 1,
                "championKills": 51,
                "dragonKills": 2,
                "hordeKills": 3,
                "inhibitorKills": 0,
                "riftHeraldKills": 1,
                "towerKills": 5,
                "atakhanKills": 0
              },
              "bans": ["147", "74", "18", "24", "233"],
              "participants": [
                {
                  "champLevel": 20,
                  "kills": 12,
                  "assists": 7,
                  "deaths": 9,
                  "role": "TOP",
                  "visionScore": 23,
                  "minions": 238,
                  "neutralMinions": 4,
                  "doubleKills": 2,
                  "tripleKills": 0,
                  "quadraKills": 0,
                  "pentaKills": 0,
                  "physicalDamageDealtToChampions": 45893,
                  "magicDamageDealtToChampions": 0,
                  "trueDamageDealtToChampions": 17594,
                  "physicalDamageTaken": 32511,
                  "magicDamageTaken": 29405,
                  "trueDamageTaken": 6431,
                  "golds": 16241,
                  "gameEndedInEarlySurrender": false,
                  "runes": {
                    "stats": {
                      "defense": "5001",
                      "flex": "5008",
                      "offense": "5005"
                    },
                    "runeCategories": [
                      {
                        "style": "8000",
                        "perks": ["8010", "9111", "9104", "8299"]
                      },
                      {
                        "style": "8200",
                        "perks": ["8224", "8234"]
                      }
                    ]
                  },
                  "champion": "122",
                  "item0": "3143",
                  "item1": "3044",
                  "item2": "3142",
                  "item3": "3009",
                  "item4": "3053",
                  "item5": "3742",
                  "ward": "3340",
                  "summoner": {
                    "puuid": "30EQ2MYfgZvzSSGUaxwWd6mcz47wt4ZRcYl8SINmrsRnQblLVsomDlQzF7pmnN7TA43h4p3UO6KNCw",
                    "name": "Beewer",
                    "level": 548,
                    "icon": "4748",
                    "tag": "EUW"
                  },
                  "summonerSpell1": "4",
                  "summonerSpell2": "14"
                }
              ]
            },
            {
              "id": "200",
              "hasWin": true,
              "objectives": {
                "baronKills": 1,
                "championKills": 48,
                "dragonKills": 3,
                "hordeKills": 0,
                "inhibitorKills": 3,
                "riftHeraldKills": 0,
                "towerKills": 11,
                "atakhanKills": 0
              },
              "bans": ["29", "76", "711", "119", "17"],
              "participants": [
                {
                  "champLevel": 18,
                  "kills": 13,
                  "assists": 11,
                  "deaths": 10,
                  "role": "BOT",
                  "visionScore": 26,
                  "minions": 261,
                  "neutralMinions": 2,
                  "doubleKills": 1,
                  "tripleKills": 0,
                  "quadraKills": 0,
                  "pentaKills": 0,
                  "physicalDamageDealtToChampions": 34002,
                  "magicDamageDealtToChampions": 9589,
                  "trueDamageDealtToChampions": 7483,
                  "physicalDamageTaken": 24608,
                  "magicDamageTaken": 15350,
                  "trueDamageTaken": 3104,
                  "golds": 18213,
                  "gameEndedInEarlySurrender": false,
                  "runes": {
                    "stats": {
                      "defense": "5001",
                      "flex": "5010",
                      "offense": "5008"
                    },
                    "runeCategories": [
                      {
                        "style": "8200",
                        "perks": ["8992", "8275", "8234", "8236"]
                      },
                      {
                        "style": "8300",
                        "perks": ["8304", "8316"]
                      }
                    ]
                  },
                  "champion": "901",
                  "item0": "1055",
                  "item1": "3071",
                  "item2": "3508",
                  "item3": "3094",
                  "item4": "3031",
                  "item5": "2021",
                  "ward": "3340",
                  "summoner": {
                    "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
                    "name": "krazytop",
                    "level": 583,
                    "icon": "1641",
                    "tag": "EUW"
                  },
                  "summonerSpell1": "21",
                  "summonerSpell2": "4"
                }
              ]
            }
          ]
        },
        {
          "id": "EUW1_7941319192",
          "version": "15.15",
          "season": 15,
          "datetime": "2026-08-05T18:30:37.751Z",
          "duration": 1411,
          "queue": "1750",
          "owners": [
            "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw"
          ],
          "teams": [
            {
              "id": "5",
              "hasWin": true,
              "placement": 1,
              "participants": [
                {
                  "champLevel": 18,
                  "kills": 9,
                  "assists": 14,
                  "deaths": 3,
                  "role": "Invalid",
                  "visionScore": 0,
                  "minions": 0,
                  "neutralMinions": 0,
                  "doubleKills": 0,
                  "tripleKills": 0,
                  "quadraKills": 0,
                  "pentaKills": 0,
                  "physicalDamageDealtToChampions": 7297,
                  "magicDamageDealtToChampions": 22037,
                  "trueDamageDealtToChampions": 0,
                  "physicalDamageTaken": 17512,
                  "magicDamageTaken": 10295,
                  "trueDamageTaken": 2880,
                  "golds": 13908,
                  "gameEndedInEarlySurrender": false,
                  "runes": {
                    "stats": {
                      "defense": "0",
                      "flex": "0",
                      "offense": "0"
                    },
                    "runeCategories": [
                      {
                        "style": "0",
                        "perks": ["0", "0", "0", "0"]
                      },
                      {
                        "style": "0",
                        "perks": ["0", "0"]
                      }
                    ]
                  },
                  "champion": "201",
                  "item0": "223748",
                  "item1": "446632",
                  "item2": "447111",
                  "item3": "223084",
                  "item4": "223075",
                  "item5": "223111",
                  "ward": "3348",
                  "summoner": {
                    "puuid": "bca8klxLKDL3_lx-bhqw73nzsTGhKBK937yqcZArHi1mn3YVKur9I21PO-7DkJgzHfOOTRQHZM5rUg",
                    "name": "a 200 dans lmur",
                    "level": 567,
                    "icon": "29",
                    "tag": "dead"
                  },
                  "summonerSpell1": "2202",
                  "summonerSpell2": "2201",
                  "augment1": "193",
                  "augment2": "216",
                  "augment3": "219"
                }
              ]
            },
            {
              "id": "1",
              "hasWin": true,
              "placement": 2,
              "participants": [
                {
                  "champLevel": 18,
                  "kills": 15,
                  "assists": 12,
                  "deaths": 5,
                  "role": "Invalid",
                  "visionScore": 0,
                  "minions": 0,
                  "neutralMinions": 0,
                  "doubleKills": 2,
                  "tripleKills": 1,
                  "quadraKills": 0,
                  "pentaKills": 0,
                  "physicalDamageDealtToChampions": 10845,
                  "magicDamageDealtToChampions": 34570,
                  "trueDamageDealtToChampions": 29387,
                  "physicalDamageTaken": 22738,
                  "magicDamageTaken": 12597,
                  "trueDamageTaken": 1304,
                  "golds": 17450,
                  "gameEndedInEarlySurrender": false,
                  "runes": {
                    "stats": {
                      "defense": "0",
                      "flex": "0",
                      "offense": "0"
                    },
                    "runeCategories": [
                      {
                        "style": "0",
                        "perks": ["0", "0", "0", "0"]
                      },
                      {
                        "style": "0",
                        "perks": ["0", "0"]
                      }
                    ]
                  },
                  "champion": "89",
                  "item0": "223065",
                  "item1": "443054",
                  "item2": "223075",
                  "item3": "447114",
                  "item4": "223084",
                  "item5": "223068",
                  "ward": "3348",
                  "summoner": {
                    "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
                    "name": "krazytop",
                    "level": 584,
                    "icon": "1641",
                    "tag": "EUW"
                  },
                  "summonerSpell1": "2201",
                  "summonerSpell2": "2202",
                  "augment1": "18",
                  "augment2": "193",
                  "augment3": "181",
                  "augment4": "313",
                  "augment5": "85"
                }
              ]
            },
            {
              "id": "4",
              "hasWin": true,
              "placement": 3,
              "participants": [
                {
                  "champLevel": 17,
                  "kills": 4,
                  "assists": 9,
                  "deaths": 12,
                  "role": "Invalid",
                  "visionScore": 0,
                  "minions": 0,
                  "neutralMinions": 0,
                  "doubleKills": 0,
                  "tripleKills": 0,
                  "quadraKills": 0,
                  "pentaKills": 0,
                  "physicalDamageDealtToChampions": 1745,
                  "magicDamageDealtToChampions": 30899,
                  "trueDamageDealtToChampions": 388,
                  "physicalDamageTaken": 13498,
                  "magicDamageTaken": 9449,
                  "trueDamageTaken": 6486,
                  "golds": 20585,
                  "gameEndedInEarlySurrender": false,
                  "runes": {
                    "stats": {
                      "defense": "0",
                      "flex": "0",
                      "offense": "0"
                    },
                    "runeCategories": [
                      {
                        "style": "0",
                        "perks": ["0", "0", "0", "0"]
                      },
                      {
                        "style": "0",
                        "perks": ["0", "0"]
                      }
                    ]
                  },
                  "champion": "4",
                  "item0": "220012",
                  "ward": "3348",
                  "summoner": {
                    "puuid": "h1E4iUOTDzUWpxCyv7AY_SLhQAgtsJv3iGl0aCusGEJ98A6EjbiTkEsDAxLhBwxWs-kp9N_OYHVF8A",
                    "name": "StefanoValentini",
                    "level": 249,
                    "icon": "6228",
                    "tag": "123"
                  },
                  "summonerSpell1": "2201",
                  "summonerSpell2": "2202",
                  "augment1": "44",
                  "augment2": "2008",
                  "augment3": "316",
                  "augment4": "378",
                  "augment5": "195",
                  "augment6": "45"
                }
              ]
            }
          ]
        }
      ]
      """

  Scénario: Rechercher tous les matchs d'un invocateur sans filtre (triés du plus récent au plus ancien)
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw"
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 2,
        "totalPages": 1,
        "content": [
          {
            "id": "EUW1_7941319192",
            "version": "15.15",
            "season": 15,
            "datetime": "2026-08-05T18:30:37.751Z",
            "duration": 1411,
            "queue": "1750",
            "remake": false,
            "teams": [
              {
                "id": "5",
                "hasWin": true,
                "placement": 1,
                "bans": [],
                "participants": [
                  {
                    "champLevel": 18,
                    "kills": 9,
                    "assists": 14,
                    "deaths": 3,
                    "role": "Invalid",
                    "visionScore": 0,
                    "minions": 0,
                    "neutralMinions": 0,
                    "doubleKills": 0,
                    "tripleKills": 0,
                    "quadraKills": 0,
                    "pentaKills": 0,
                    "physicalDamageDealtToChampions": 7297,
                    "magicDamageDealtToChampions": 22037,
                    "trueDamageDealtToChampions": 0,
                    "physicalDamageTaken": 17512,
                    "magicDamageTaken": 10295,
                    "trueDamageTaken": 2880,
                    "golds": 13908,
                    "gameEndedInEarlySurrender": false,
                    "runes": {
                      "stats": {
                        "defense": "0",
                        "flex": "0",
                        "offense": "0"
                      },
                      "runeCategories": [
                        {
                          "style": "0",
                          "perks": ["0", "0", "0", "0"]
                        },
                        {
                          "style": "0",
                          "perks": ["0", "0"]
                        }
                      ]
                    },
                    "champion": "201",
                    "item0": "223748",
                    "item1": "446632",
                    "item2": "447111",
                    "item3": "223084",
                    "item4": "223075",
                    "item5": "223111",
                    "ward": "3348",
                    "summoner": {
                      "puuid": "bca8klxLKDL3_lx-bhqw73nzsTGhKBK937yqcZArHi1mn3YVKur9I21PO-7DkJgzHfOOTRQHZM5rUg",
                      "name": "a 200 dans lmur",
                      "level": 567,
                      "icon": "29",
                      "tag": "dead"
                    },
                    "summonerSpell1": "2202",
                    "summonerSpell2": "2201",
                    "augment1": "193",
                    "augment2": "216",
                    "augment3": "219"
                  }
                ]
              },
              {
                "id": "1",
                "hasWin": true,
                "placement": 2,
                "bans": [],
                "participants": [
                  {
                    "champLevel": 18,
                    "kills": 15,
                    "assists": 12,
                    "deaths": 5,
                    "role": "Invalid",
                    "visionScore": 0,
                    "minions": 0,
                    "neutralMinions": 0,
                    "doubleKills": 2,
                    "tripleKills": 1,
                    "quadraKills": 0,
                    "pentaKills": 0,
                    "physicalDamageDealtToChampions": 10845,
                    "magicDamageDealtToChampions": 34570,
                    "trueDamageDealtToChampions": 29387,
                    "physicalDamageTaken": 22738,
                    "magicDamageTaken": 12597,
                    "trueDamageTaken": 1304,
                    "golds": 17450,
                    "gameEndedInEarlySurrender": false,
                    "runes": {
                      "stats": {
                        "defense": "0",
                        "flex": "0",
                        "offense": "0"
                      },
                      "runeCategories": [
                        {
                          "style": "0",
                          "perks": ["0", "0", "0", "0"]
                        },
                        {
                          "style": "0",
                          "perks": ["0", "0"]
                        }
                      ]
                    },
                    "champion": "89",
                    "item0": "223065",
                    "item1": "443054",
                    "item2": "223075",
                    "item3": "447114",
                    "item4": "223084",
                    "item5": "223068",
                    "ward": "3348",
                    "summoner": {
                      "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
                      "name": "krazytop",
                      "level": 584,
                      "icon": "1641",
                      "tag": "EUW"
                    },
                    "summonerSpell1": "2201",
                    "summonerSpell2": "2202",
                    "augment1": "18",
                    "augment2": "193",
                    "augment3": "181",
                    "augment4": "313",
                    "augment5": "85"
                  }
                ]
              },
              {
                "id": "4",
                "hasWin": true,
                "placement": 3,
                "bans": [],
                "participants": [
                  {
                    "champLevel": 17,
                    "kills": 4,
                    "assists": 9,
                    "deaths": 12,
                    "role": "Invalid",
                    "visionScore": 0,
                    "minions": 0,
                    "neutralMinions": 0,
                    "doubleKills": 0,
                    "tripleKills": 0,
                    "quadraKills": 0,
                    "pentaKills": 0,
                    "physicalDamageDealtToChampions": 1745,
                    "magicDamageDealtToChampions": 30899,
                    "trueDamageDealtToChampions": 388,
                    "physicalDamageTaken": 13498,
                    "magicDamageTaken": 9449,
                    "trueDamageTaken": 6486,
                    "golds": 20585,
                    "gameEndedInEarlySurrender": false,
                    "runes": {
                      "stats": {
                        "defense": "0",
                        "flex": "0",
                        "offense": "0"
                      },
                      "runeCategories": [
                        {
                          "style": "0",
                          "perks": ["0", "0", "0", "0"]
                        },
                        {
                          "style": "0",
                          "perks": ["0", "0"]
                        }
                      ]
                    },
                    "champion": "4",
                    "item0": "220012",
                    "ward": "3348",
                    "summoner": {
                      "puuid": "h1E4iUOTDzUWpxCyv7AY_SLhQAgtsJv3iGl0aCusGEJ98A6EjbiTkEsDAxLhBwxWs-kp9N_OYHVF8A",
                      "name": "StefanoValentini",
                      "level": 249,
                      "icon": "6228",
                      "tag": "123"
                    },
                    "summonerSpell1": "2201",
                    "summonerSpell2": "2202",
                    "augment1": "44",
                    "augment2": "2008",
                    "augment3": "316",
                    "augment4": "378",
                    "augment5": "195",
                    "augment6": "45"
                  }
                ]
              }
            ]
          },
          {
            "id": "EUW1_7939943973",
            "version": "16.15",
            "season": 16,
            "datetime": "2026-08-04T13:28:32.620Z",
            "duration": 2268,
            "remake": false,
            "queue": "420",
            "teams": [
              {
                "id": "100",
                "hasWin": false,
                "objectives": {
                  "baronKills": 1,
                  "championKills": 51,
                  "dragonKills": 2,
                  "hordeKills": 3,
                  "inhibitorKills": 0,
                  "riftHeraldKills": 1,
                  "towerKills": 5,
                  "atakhanKills": 0
                },
                "bans": ["147", "74", "18", "24", "233"],
                "participants": [
                  {
                    "champLevel": 20,
                    "kills": 12,
                    "assists": 7,
                    "deaths": 9,
                    "role": "TOP",
                    "visionScore": 23,
                    "minions": 238,
                    "neutralMinions": 4,
                    "doubleKills": 2,
                    "tripleKills": 0,
                    "quadraKills": 0,
                    "pentaKills": 0,
                    "physicalDamageDealtToChampions": 45893,
                    "magicDamageDealtToChampions": 0,
                    "trueDamageDealtToChampions": 17594,
                    "physicalDamageTaken": 32511,
                    "magicDamageTaken": 29405,
                    "trueDamageTaken": 6431,
                    "golds": 16241,
                    "gameEndedInEarlySurrender": false,
                    "runes": {
                      "stats": {
                         "defense": "5001",
                        "flex": "5008",
                        "offense": "5005"
                      },
                      "runeCategories": [
                        {
                          "style": "8000",
                          "perks": ["8010", "9111", "9104", "8299"]
                        },
                        {
                          "style": "8200",
                          "perks": ["8224", "8234"]
                        }
                      ]
                    },
                    "champion": "122",
                    "item0": "3143",
                    "item1": "3044",
                    "item2": "3142",
                    "item3": "3009",
                    "item4": "3053",
                    "item5": "3742",
                    "ward": "3340",
                    "summoner": {
                      "puuid": "30EQ2MYfgZvzSSGUaxwWd6mcz47wt4ZRcYl8SINmrsRnQblLVsomDlQzF7pmnN7TA43h4p3UO6KNCw",
                      "name": "Beewer",
                      "level": 548,
                      "icon": "4748",
                      "tag": "EUW"
                    },
                    "summonerSpell1": "4",
                    "summonerSpell2": "14"
                  }
                ]
              },
              {
                "id": "200",
                "hasWin": true,
                "objectives": {
                  "baronKills": 1,
                  "championKills": 48,
                  "dragonKills": 3,
                  "hordeKills": 0,
                  "inhibitorKills": 3,
                  "riftHeraldKills": 0,
                  "towerKills": 11,
                  "atakhanKills": 0
                },
                "bans": ["29", "76", "711", "119", "17"],
                "participants": [
                  {
                    "champLevel": 18,
                    "kills": 13,
                    "assists": 11,
                    "deaths": 10,
                    "role": "BOT",
                    "visionScore": 26,
                    "minions": 261,
                    "neutralMinions": 2,
                    "doubleKills": 1,
                    "tripleKills": 0,
                    "quadraKills": 0,
                    "pentaKills": 0,
                    "physicalDamageDealtToChampions": 34002,
                    "magicDamageDealtToChampions": 9589,
                    "trueDamageDealtToChampions": 7483,
                    "physicalDamageTaken": 24608,
                    "magicDamageTaken": 15350,
                    "trueDamageTaken": 3104,
                    "golds": 18213,
                    "gameEndedInEarlySurrender": false,
                    "runes": {
                      "stats": {
                        "defense": "5001",
                        "flex": "5010",
                        "offense": "5008"
                      },
                      "runeCategories": [
                        {
                          "style": "8200",
                          "perks": ["8992", "8275", "8234", "8236"]
                        },
                        {
                          "style": "8300",
                          "perks": ["8304", "8316"]
                        }
                      ]
                    },
                    "champion": "901",
                    "item0": "1055",
                    "item1": "3071",
                    "item2": "3508",
                    "item3": "3094",
                    "item4": "3031",
                    "item5": "2021",
                    "ward": "3340",
                    "summoner": {
                      "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
                      "name": "krazytop",
                      "level": 583,
                      "icon": "1641",
                      "tag": "EUW"
                    },
                    "summonerSpell1": "21",
                    "summonerSpell2": "4"
                  }
                ]
              }
            ]
          }
        ]
      }
      """

  Scénario: Paginer les matchs avec une taille de page de 1 (page 1 = match normal)
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 1,
        "pageSize": 1,
        "searchCriteria": {
          "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw"
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON contient :
      """json
      {
        "pageNumber": 1,
        "pageSize": 1,
        "totalElements": 2,
        "totalPages": 2,
        "content": [
          {
            "id": "EUW1_7939943973"
          }
        ]
      }
      """

  Scénario: Filtrer les matchs par queue
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
          "queue": "ARENA"
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON contient :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 1,
        "totalPages": 1,
        "content": [
          {
            "id": "EUW1_7941319192",
            "queue": "1750"
          }
        ]
      }
      """

  Scénario: Filtrer les matchs par rôle de l'invocateur ciblé
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
          "role": "BOTTOM"
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON contient :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 1,
        "totalPages": 1,
        "content": [
          {
            "id": "EUW1_7939943973"
          }
        ]
      }
      """

  Scénario: Filtrer les matchs par saison
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw",
          "season": 15
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON contient :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 1,
        "totalPages": 1,
        "content": [
          {
            "id": "EUW1_7941319192",
            "season": 15
          }
        ]
      }
      """

  Scénario: Rechercher les matchs d'un invocateur sans historique retourne une page vide
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "unknown-puuid-123"
        }
      }
      """
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 0,
        "totalPages": 0,
        "content": []
      }
      """

  Scénario: Rechercher avec des paramètres de pagination invalides retourne 400
    Quand j'appelle POST "/match/search" avec le body :
      """json
      {
        "pageNumber": -1,
        "pageSize": 10,
        "searchCriteria": {
          "puuid": "unknown-puuid-123"
        }
      }
      """
    Alors le statut HTTP est 400