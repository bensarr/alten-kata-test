# Guide de démarrage pour l'environnement de développement

Ce guide vous aidera à configurer, lancer et tester l'application Spring Boot avec toutes ses API.

## Table des matières

1. [Prérequis](#prérequis)
2. [Installation](#installation)
3. [Lancement de l'application](#lancement-de-lapplication)
4. [Documentation des API](#documentation-des-api)
5. [Test des API avec Swagger UI](#test-des-api-avec-swagger-ui)
6. [Test des API avec Postman](#test-des-api-avec-postman)
7. [Structure de l'application](#structure-de-lapplication)
8. [Base de données H2](#base-de-données-h2)
9. [Authentification et autorisations](#authentification-et-autorisations)
10. [Troubleshooting](#troubleshooting)

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (ou version ultérieure)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [Postman](https://www.postman.com/downloads/) (optionnel pour tester les API)

## Installation

1. Clonez le dépôt Git
```bash
git clone <URL_DU_REPO>
cd <NOM_DU_DOSSIER>
```

2. Compilez le projet avec Maven
```bash
mvn clean install
```

## Lancement de l'application

1. Exécutez l'application
```bash
mvn spring-boot:run
```

2. L'application sera disponible à l'adresse suivante : [http://localhost:8081](http://localhost:8081)

## Documentation des API

Cette application utilise SpringDoc OpenAPI (Swagger) pour documenter les API. Une fois l'application lancée, vous pouvez consulter la documentation à l'adresse suivante :

- Swagger UI : [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- Documentation OpenAPI JSON : [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Test des API avec Swagger UI

### Authentification et création d'un compte

1. Commencez par créer un compte utilisateur :
    - Accédez à [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
    - Naviguez vers la section "Authentication" et ouvrez l'endpoint `/account` (POST)
    - Cliquez sur "Try it out" et fournissez les informations requises dans le format suivant :
   ```json
   {
     "username": "user",
     "firstname": "Test",
     "email": "user@example.com",
     "password": "password123"
   }
   ```
    - Cliquez sur "Execute" pour créer le compte

2. Générez un token d'authentification :
    - Toujours dans la section "Authentication", ouvrez l'endpoint `/token` (POST)
    - Cliquez sur "Try it out" et fournissez les identifiants :
   ```json
   {
     "email": "user@example.com",
     "password": "password123"
   }
   ```
    - Copiez le token JWT retourné pour l'utiliser dans les requêtes suivantes

3. Configurer l'authentification pour toutes les API :
    - En haut de la page Swagger UI, cliquez sur le bouton "Authorize"
    - Dans le champ "Value", saisissez `Bearer <votre_token>` (remplacez `<votre_token>` par le token JWT copié précédemment)
    - Cliquez sur "Authorize" puis sur "Close"

### Test des fonctionnalités principales

#### API de produits (Products)

Pour tester les opérations CRUD sur les produits :

1. **Créer un produit** (réservé à l'admin) :
    - Utilisez l'email admin (`admin@admin.com`) pour obtenir un token d'authentification
    - Naviguez vers la section "Products" et ouvrez l'endpoint `/products` (POST)
    - Cliquez sur "Try it out" et fournissez les détails du produit :
   ```json
   {
     "name": "string",
     "description": "string",
     "image": "string",
     "category": "string",
     "price": 0.1,
     "quantity": 1073741824,
     "rating": 5,
     "internal_reference": "string",
     "shell_id": 9007199254740991,
     "inventory_status": "INSTOCK"
   }
   ```

2. **Récupérer tous les produits** :
    - Toujours dans la section "Products", ouvrez l'endpoint `/products` (GET)
    - Cliquez sur "Execute" pour récupérer la liste des produits

3. **Récupérer un produit par ID** :
    - Ouvrez l'endpoint `/products/{id}` (GET)
    - Entrez l'ID du produit créé précédemment
    - Cliquez sur "Execute"

4. **Mettre à jour un produit** (réservé à l'admin) :
    - Ouvrez l'endpoint `/products/{id}` (PATCH)
    - Entrez l'ID du produit à mettre à jour
    - Fournissez les détails à mettre à jour :
   ```json
   {
     "name": "Smartphone Updated",
     "description": "string",
     "image": "string",
     "category": "string",
     "price": 0.1,
     "quantity": 1073741824,
     "rating": 5,
     "internal_reference": "string",
     "shell_id": 9007199254740991,
     "inventory_status": "INSTOCK"
   }
   ```

5. **Supprimer un produit** (réservé à l'admin) :
    - Ouvrez l'endpoint `/products/{id}` (DELETE)
    - Entrez l'ID du produit à supprimer
    - Cliquez sur "Execute"

#### API de panier (Shopping Cart)

1. **Consulter le panier** :
    - Naviguez vers la section "Shopping Cart" et ouvrez l'endpoint `/cart` (GET)
    - Cliquez sur "Execute" pour voir le panier actuel

2. **Ajouter un produit au panier** :
    - Ouvrez l'endpoint `/cart/items` (POST)
    - Fournissez les détails :
   ```json
   {
     "product_id": 1,
     "quantity": 2
   }
   ```

3. **Mettre à jour la quantité d'un produit** :
    - Ouvrez l'endpoint `/cart/items/{productId}` (PATCH)
    - Entrez l'ID du produit dans le panier
    - Fournissez la nouvelle quantité :
   ```json
   {
     "quantity": 3
   }
   ```

4. **Supprimer un produit du panier** :
    - Ouvrez l'endpoint `/cart/items/{productId}` (DELETE)
    - Entrez l'ID du produit à retirer
    - Cliquez sur "Execute"

5. **Vider le panier** :
    - Ouvrez l'endpoint `/cart` (DELETE)
    - Cliquez sur "Execute"

#### API de liste de souhaits (Wishlist)

1. **Consulter la liste de souhaits** :
    - Naviguez vers la section "Wishlist" et ouvrez l'endpoint `/wishlist` (GET)
    - Cliquez sur "Execute"

2. **Ajouter un produit à la liste de souhaits** :
    - Ouvrez l'endpoint `/wishlist/items` (POST)
    - Fournissez les détails :
   ```json
   {
     "product_id": 1
   }
   ```

3. **Supprimer un produit de la liste de souhaits** :
    - Ouvrez l'endpoint `/wishlist/items/{productId}` (DELETE)
    - Entrez l'ID du produit à retirer
    - Cliquez sur "Execute"

4. **Vider la liste de souhaits** :
    - Ouvrez l'endpoint `/wishlist` (DELETE)
    - Cliquez sur "Execute"

## Test des API avec Postman

Si vous préférez utiliser Postman pour tester les API :

1. Créez une nouvelle collection Postman
2. Ajoutez une requête POST pour l'endpoint `/token` avec le body :
   ```json
   {
     "email": "user@example.com",
     "password": "password123"
   }
   ```
3. Exécutez cette requête et copiez le token JWT retourné
4. Pour toutes les autres requêtes, ajoutez un header d'autorisation :
    - Key: `Authorization`
    - Value: `Bearer <votre_token>` (remplacez `<votre_token>` par le token JWT)

## Structure de l'application

L'application est organisée en plusieurs modules fonctionnels :

- **auth** : Gestion de l'authentification et des utilisateurs
- **product** : Gestion des produits
- **cart** : Gestion du panier d'achat
- **wishlist** : Gestion de la liste de souhaits
- **common** : Classes communes et utilitaires

## Base de données H2

L'application utilise une base de données H2 en mémoire pour le développement et les tests :

- Console H2 : [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
- JDBC URL : `jdbc:h2:mem:testdb`
- Utilisateur : `sa`
- Mot de passe : (laissez vide)

## Authentification et autorisations

L'application utilise JWT (JSON Web Token) pour l'authentification :

- Les jetons JWT expirent après une période définie
- Certaines opérations (création, modification et suppression de produits) sont réservées à l'utilisateur admin

### Utilisateur admin par défaut
```
Email: admin@admin.com
```

Pour tester les opérations d'administration, créez d'abord un compte avec cet email, puis obtenez un token pour cet utilisateur.

## Troubleshooting

### Problèmes courants et solutions

1. **L'application ne démarre pas**
    - Vérifiez que le port 8081 n'est pas déjà utilisé par une autre application
    - Assurez-vous d'avoir Java 17+ installé (`java -version`)

2. **Erreur 401 Unauthorized**
    - Vérifiez que le token JWT est valide et n'a pas expiré
    - Assurez-vous que le format du header d'autorisation est correct : `Bearer <token>`

3. **Erreur 403 Forbidden lors de la modification des produits**
    - Ces opérations sont réservées à l'utilisateur admin
    - Assurez-vous d'être connecté avec l'email `admin@admin.com`

4. **Les données ne persistent pas**
    - C'est normal, l'application utilise une base de données H2 en mémoire
    - Les données sont réinitialisées à chaque redémarrage de l'application

5. **Swagger UI n'est pas accessible**
    - Vérifiez que l'application est bien démarrée sur le port 8081
    - Essayez d'accéder à l'API directement pour voir si elle répond

Pour toute autre question ou problème, n'hésitez pas à me contacter.