# language: fr
Fonctionnalité: Gestion des achèvements d'Arena

  Scénario: Récupérer les champions complétés en Arena d'un invocateur existant
    Etant donné qu'on insère dans la collection "ArenaCompletion" le document suivant :
      """json
      {
        "puuid": "puuid-1234",
        "champions": [
          "121",
          "18"
        ]
      }
      """
    Quand j'appelle GET "/arena-completion/puuid-1234"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      [
        "121",
        "18"
      ]
      """

  Scénario: Récupérer la complétion Arena d'un nouvel invocateur
    Etant donné que la collection "ArenaCompletion" est vide
    Quand j'appelle GET "/arena-completion/puuid-inconnu"
    Alors le statut HTTP est 200
    Et la réponse JSON est :
      """json
      []
      """