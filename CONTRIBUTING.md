# Guide de contribution 

Je rappelle ici les règles de contributions.

# Stratégie Git
Pour travailler sur une issue, creer une branche a partir de main en respectant la convention de nommage suivante :

```
feature/<numero-issue>-nom-feature
```

Exemples :

- `feature/3-setup-react`
- `feature/12-article-editor`
- `feature/31-ci-cd-pipeline`

1. Realiser les modifications sur cette branche.
2. Pousser la branche sur le depot distant.
3. Ouvrir une Pull Request vers main.

# Pull Requests

Chaque Pull Request doit :

- Avoir un titre clair et concis decrivant les changements.
- Faire reference a l'issue associee (par exemple : Closes #3).
- Contenir une description des modifications apportees.
- Etre approuvee par au moins un reviewer avant d'etre mergee.

Les merges se font via merge commit 

# Commits

Les messages de commit doivent suivre la convention Conventional Commits :

```
<type>(<scope>): <description>
```

Types courants :

- feat : ajout d'une fonctionnalite
- fix : correction d'un bug
- docs : modification de la documentation
- style : changements de formatage (pas de modification de logique)
- refactor : refactorisation du code
- test : ajout ou modification de tests
- chore : taches de maintenance (dependances, config, etc.)

Exemples :
- `feat(blog): ajoute la page de listing des articles`
- `fix(auth): corrige la redirection apres connexion`
- `chore(frontend): met a jour les dependances`
