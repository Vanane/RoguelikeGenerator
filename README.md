# RoguelikeGenerator
Projet de M2 MIAGE Architecture logicielle

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

`javac -cp "src;lib/*;" .\src\Main.java -d bin`

3. Toujours depuis l'invite de commande, exécutez le programme ainsi :

`java -cp "bin;lib/*;" Main`

Il est possible de souhaiter compiler vers une autre destination. Dans ce cas, les fichiers de librairies externes devront être copiées manuellement dans le dossier de destination, et le dossier devra être référencé dans la commande d'exécution : 

`java -cp "bin;chemin/vers/les/librairies/*;" Main`

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