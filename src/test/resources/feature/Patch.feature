# language: fr
Fonctionnalité: Consultation des données de référence par patch (Patch)

  Contexte:
    Etant donné qu'on insère dans la collection "Patch" le document suivant :
      """json
      {
        "id": "16.22_fr_FR",
        "patchId": "16.22",
        "language": "fr_FR",
        "season": 16,
        "champions": [
          {
            "id": "266",
            "name": "Aatrox",
            "image": "Aatrox.png",
            "description": "Épée des Darkin",
            "title": "l'Épée des Darkin",
            "stats": {
              "hp": 650.0,
              "armor": 38.0
            },
            "tags": ["Fighter", "Tank"]
          }
        ],
        "items": [
          {
            "id": "3031",
            "name": "Lame d'infini",
            "image": "3031.png",
            "description": "Augmente les dégâts critiques",
            "plainText": "Dégâts d'attaque et coups critiques",
            "baseGold": 625,
            "totalGold": 3400,
            "tags": ["Damage", "CriticalStrike"],
            "stats": {
              "FlatPhysicalDamageMod": 70.0
            },
            "toItems": ["1028", "1027", "1008"],
            "fromItems": ["1038", "1037", "1018"]
          }
        ],
        "summonerSpells": [
          {
            "id": "SummonerFlash",
            "name": "Saut éclair",
            "image": "SummonerFlash.png",
            "description": "Téléporte votre champion sur une courte distance",
            "tooltip": "Téléporte le champion vers le curseur.",
            "cooldownBurn": 300.0
          }
        ],
        "augments": [
          {
            "id": "Augment_1",
            "name": "Symphonie de la guerre",
            "image": "augment1.png",
            "description": "Confère Tempo mortel et Conquérant",
            "dataValues": {
              "AttackSpeed": [0.1, 0.2]
            }
          }
        ],
        "runes": [
          {
            "id": "8000",
            "name": "Précision",
            "image": "7201_Precision.png",
            "description": "Attaques améliorées et dégâts soutenus",
            "perks": [
              [
                {
                  "id": "8005",
                  "name": "Attaque soutenue",
                  "image": "PressTheAttack.png",
                  "description": "Toucher 3 fois un champion inflige des dégâts bonus",
                  "longDescription": "Description détaillée de Attaque soutenue"
                }
              ]
            ]
          }
        ],
        "queues": [
          {
            "id": "420",
            "name": "Classée Solo/Duo",
            "description": "Faille de l'invocateur 5v5"
          }
        ]
      }
      """

  Scénario: Récupérer un patch existant en base pour une langue donnée
    Quand j'appelle GET "/patch/16.22" avec le header "Accept-Language" égal à "fr_FR"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "id": "16.22_fr_FR",
        "patchId": "16.22",
        "language": "fr_FR",
        "season": 16,
        "champions": [
          {
            "id": "266",
            "name": "Aatrox",
            "image": "Aatrox.png",
            "description": "Épée des Darkin",
            "title": "l'Épée des Darkin",
            "stats": {
              "hp": 650.0,
              "armor": 38.0
            },
            "tags": ["Fighter", "Tank"]
          }
        ],
        "items": [
          {
            "id": "3031",
            "name": "Lame d'infini",
            "image": "3031.png",
            "description": "Augmente les dégâts critiques",
            "plainText": "Dégâts d'attaque et coups critiques",
            "baseGold": 625,
            "totalGold": 3400,
            "tags": ["Damage", "CriticalStrike"],
            "stats": {
              "FlatPhysicalDamageMod": 70.0
            },
            "toItems": ["1028", "1027", "1008"],
            "fromItems": ["1038", "1037", "1018"]
          }
        ],
        "summonerSpells": [
          {
            "id": "SummonerFlash",
            "name": "Saut éclair",
            "image": "SummonerFlash.png",
            "description": "Téléporte votre champion sur une courte distance",
            "tooltip": "Téléporte le champion vers le curseur.",
            "cooldownBurn": 300.0
          }
        ],
        "augments": [
          {
            "id": "Augment_1",
            "name": "Symphonie de la guerre",
            "image": "augment1.png",
            "description": "Confère Tempo mortel et Conquérant",
            "dataValues": {
              "AttackSpeed": [0.1, 0.2]
            }
          }
        ],
        "runes": [
          {
            "id": "8000",
            "name": "Précision",
            "image": "7201_Precision.png",
            "description": "Attaques améliorées et dégâts soutenus",
            "perks": [
              [
                {
                  "id": "8005",
                  "name": "Attaque soutenue",
                  "image": "PressTheAttack.png",
                  "description": "Toucher 3 fois un champion inflige des dégâts bonus",
                  "longDescription": "Description détaillée de Attaque soutenue"
                }
              ]
            ]
          }
        ],
        "queues": [
          {
            "id": "420",
            "name": "Classée Solo/Duo",
            "description": "Faille de l'invocateur 5v5"
          }
        ]
      }
      """

  Scénario: Récupérer un patch existant mais dans une langue non disponible retourne 404
    Quand j'appelle GET "/patch/16.22" avec le header "Accept-Language" égal à "en_US"
    Alors le statut HTTP est 404

  Scénario: Récupérer un patch inexistant retourne 404
    Quand j'appelle GET "/patch/99.99" avec le header "Accept-Language" égal à "fr_FR"
    Alors le statut HTTP est 404

  Scénario: Appeler GET /patch sans le header obligatoire Accept-Language retourne 400
    Quand j'appelle GET "/patch/16.22"
    Alors le statut HTTP est 400