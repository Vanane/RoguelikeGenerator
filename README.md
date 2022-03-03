# RoguelikeGenerator
Projet de M2 MIAGE Architecture logicielle

## TODO

- Display player health on UI

- Add comments

- See for other plugins ideas

- Add UI legend

- Add Game Over layout for terminal version

- Keep the plugin chooser windows open, and make it reload at runtime when pressing EXIT


### Idées de plugins
- Changement de l'IA du zombie
    - Mode random, bouge au hasard, plugin par défaut
    - Mode chasse, si le joueur est à moins de X cases, il se dirige vers lui. Ca implique que le zombie connait la position du joueur, ou qu'il soit au moins notifié de sa position.
        - Pour que le zombie suive une cible générique, et pas juste le joueur : le zombie connait  la liste de toutes les créatures. A chaque tick : vérifie si une créature de la liste est proche et hostile, et la prend en chasse.
- Ajouter des effets au joueur (pas un plugin)
    - Poison : à chaque pas il perd 1pv

- Plugins par rapport aux effets
    - Dégats : par défaut, rien
        - plugin pour afficher le bord en rouge (cela demande de notifier le moteur graphique via le world)

--> Idée de plugins
   -> TP, regénérer la map au runtime, UI changes (grey scale), speed up entity

### Comment implémenter un plugin
- Décider du comportement à transformer en plugin (ex : le comportement d'un zombie)
- Abstraire ce comportement, en définissant une interface (ex : ICreatureBehaviour)
- Implémenter cette interface par une classe par défaut (ex : DefaultBehaviour)
- Pour ajouter un plugin, implémenter cette interface et choisir de charger ce plugin plutôt que le défaut.

## Prérequis
Les prérequis pour pouvoir télécharger, compiler et exécuter ce logiciel sont :
- Un ordinateur
- Une connexion internet
- Un clavier (souris optionnelle, mais recommandée)
- Java JDK 17.x (compilation)
- Java JRE 17.x (exécution)

## Utilisation
1. Clonez ou téléchargez ce repository
### Windows (par ligne de commande)
2. Depuis la racine du projet dans un invite de commande, compilez le programme :

`javac -cp "src;lib/*;" .\src\com\pitchounous\PluginLoader.java -d bin`

3. Toujours depuis l'invite de commande, exécutez le programme ainsi :

`java -cp "bin;plugins;lib/*;" com.pitchounous.PluginLoader`

Il est possible de souhaiter compiler vers une autre destination. Dans ce cas, les fichiers de librairies externes devront être copiées manuellement dans le dossier de destination, et le dossier devra être référencé dans la commande d'exécution :

`java -cp "bin;chemin/vers/les/librairies/*;" com.pitchounous.PluginLoader`

### Linux (par ligne de commande)
2. Suivez la procédure pour Windows, mais remplacez les ; par des :

### Windows/Linux (par VS Code)
Vous aurez besoin des extensions suivantes pour pouvoir build correctement (VS Code les propose lors de l'ouverture du projet) :
- Debugger for Java
- Extension Pack for Java
- Project Manager for Java
2. Ouvrez la racine du projet dans VS Code
3. Appuyez sur F5, ou bien cliquez sur Run > Start Debugging
4. Si VS Code ne reconnait pas le projet, tentez d'ouvrir l'un des fichiers Java.

### Fichier JAR
Il est possible de créer un JAR exécutable à l'aide de la commande JAR. Un fichier JAR est plus facile à partager, et permet également d'intégrer les librairies externes dans un seul fichier.

## Ressources utiles

https://github.com/ZeroDoctor/yt-java-game/blob/master/game-decay/src/main/java/com/zerulus/game/GameLauncher.java
