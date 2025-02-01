# Application de Gestion d'École

## Description

Cette application web permet de gérer les étudiants, les professeurs, les modules et les inscriptions au sein d'une école. Elle intègre des fonctionnalités modernes telles que la sécurité utilisateur et des services distribués.

## Fonctionnalités principales

1. **Gestion des étudiants :**
   - Ajouter, modifier, supprimer et rechercher des étudiants.
   - Afficher les détails d'un étudiant (matricule, nom, prénom, etc.).
   - Gérer les modules inscrits par chaque étudiant.

2. **Gestion des professeurs :**
   - Ajouter, modifier et supprimer des professeurs.
   - Assigner des modules aux professeurs.

3. **Gestion des modules :**
   - Créer et gérer les modules.
   - Consulter les étudiants inscrits dans chaque module.

4. **Gestion des inscriptions :**
   - Inscrire des étudiants à des modules.
   - Annuler une inscription.
   - Visualiser les inscriptions par module.

5. **Tableau de bord :**
   - Afficher des statistiques : nombre total d'étudiants, de modules, d'inscriptions, etc.
   - Identifier les modules les plus populaires.

6. **Sécurité :**
   - Authentification avec Spring Security.
   - Gestion des rôles : Administrateur, Secrétaire, Professeur.
   - Autorisations spécifiques basées sur les rôles.

7. **Services distribués avec Spring Cloud :**
   - Découverte des services avec Eureka.
   - Configuration centralisée avec Spring Cloud Config.

## Architecture

### Backend

- **Spring Boot :** Gestion des API et des services.
- **Spring Data JPA :** Gestion des interactions avec MySQL.
- **Spring Security :** Gestion des utilisateurs et des rôles.
- **Spring Cloud :** Gestion des microservices .

## Installation

1. **Cloner le dépôt :**

   ```bash
   git clone https://github.com/saraboulaid/Syst-me-de-Gestion-d-cole
   
