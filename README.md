# Architecture client/server
Projet 2026 : Réaliser un système informatique de gestion d'un stock de marchandises d'un vendeur. 
L'objectif ici est de mettre en place une architecture client/serveur.


# Informations
## Gestion du stock

Le système informatique dispose d’une base de données où sont décrits tous les articles du magasin. 
Pour chacun des articles, on dispose des informations suivantes:

- La référence précise de l’article
- La famille de l’article
- Le prix unitaire de l’article
- Le nombre total d’exemplaires en stock

Deux articles distincts diffèrent par leur référence. On considère dans le cadre de ce projet que le
stock préexiste.

## Facturation des clients

Le magasin dispose d’un fichier qui décrit l’ensemble des factures (tickets de caisse) que les
clients devront acquitter.

Ce fichier contient pour chaque client :

- Le total de la facture pour un client
- Le détail des articles achetés
- Le mode de payement
- La date de facturation

On peut voir ici comment sera organisé la base de données

<img width="700" height="242" alt="entite-asso" src="https://github.com/user-attachments/assets/0284a222-70bd-46f7-add0-3512db0b7949" />

## Opérations possibles sur les données

Le système informatique doit permettre de réaliser les opérations suivantes :

-  Consulter le stock d’un article en donnant la référence d’un article, on doit pouvoir récupérer les informations le concernant (quantité en stock, prix unitaire, etc.)
-  Rechercher un article : en donnant une famille d’articles, on doit pouvoir récupérer toutes les références des articles de cette famille. Seules les références dont le stock n’est pas nul doivent être retournées
- Acheter un article : un client doit pouvoir acheter un article en stock
- Payer une facture : un client peut payer ce qu’il doit au magasin 
- Consulter une facture : il doit être possible de voir la facture (ticket de caisse, format du fichier au choix)
- Calculer le chiffre d’affaire à une date donnée en fonction des factures de cette date
- Jouter un produit : on peut ajouter un certain nombre d’exemplaires d’un produit dans le catalogue (la référence du produit doit déjà exister)

Les prix sont mis à jour tous les matins par le serveur du siège de l’entreprise. L’ensemble des facture est sauvegardé tous les soirs sur le serveur du de siège de l’entreprise.

## Architecture du système informatisé

On souhaite mettre en place un système informatique composé des éléments suivants :

- Un serveur central situé au siège de l’entreprise
- Un serveur dans chacun des magasins
- Des postes clients (caisses) dans chacun des magasins.

Les postes clients doivent permettre, grâce à une interface homme-machine appropriée, de réaliser les différentes opérations prévues.


Le fonctionnement sera le suivant :
1. Selon la saisie de l’utilisateur, le client préparera une requête à envoyer au serveur.
2. La requête sera envoyée au serveur et le client se mettra en attente de la réponse.
3. Le serveur réceptionnera la requête et la traitera pour comprendre la demande du client.
4. Il effectuera ensuite le traitement associé,
5. Il enverra le résultat de ce traitement au client.
6. Le client réceptionnera le résultat et pourra enchaîner sur une nouvelle requête

# CONTRAINTES
- Application serveur/client => Java
- Middleware => RMI ([tuto](https://waytolearnx.com/2020/06/tutoriel-java-rmi.html))
- SGBD => MySQL
