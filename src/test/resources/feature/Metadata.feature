# language: fr
Fonctionnalité: Gestion des métadonnées de l'application

  Scénario: Récupérer les métadonnées actuelles de League of Legends
    Etant donné que l'on insère dans la collection "Metadata" le document suivant :
      """json
      {
        "id": 1,
        "currentSeason": 16,
        "currentPatch": "16.21",
        "allPatches": [
          "16.20",
          "16.21"
        ]
      }
      """
    Quand j'appelle GET "/metadata"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      {
        "currentSeasonOrSet": 16,
        "currentPatch": "16.21"
      }
      """