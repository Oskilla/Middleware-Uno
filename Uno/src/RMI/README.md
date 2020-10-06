Pour changer le port de départ de RMI : "rmiregistry numeroduport &"

Pour lancer le serveur : java -Djava.security.policy=RMI/src/myPolicy.policy RMI/src/LanceServeur


To run a client : 
- Dans le fichier "Client.java", mettre d'adresse du serveur actuel
- Se placer dans Uno/src
- Si necessaire, supprimer les .class : rm RMI/src/*.class
- Compiler les classes : javac -Xlint RMI/src/*.java
- Executer : - rmic RMI.src.InformationImpl
             - rmiregistry&  ---> sur windows: start rmiregistry
             - java RMI/src/Client.java
             
 Si un problème de port se pose : 
    - Pour trouver le PID d'un processus : "lsof -i:1124"
    - Puis "kill -9 numeropid"
