Pour run la commande rmic : sortir de RMI/src

Pour changer le port de départ de RMI : "rmiregistry numeroduport &"

Pour trouver le PID d'un processus : "lsof -i:1124"
Puis "kill -9 numeropid"

java -Djava.security.policy=RMI/src/myPolicy.policy RMI/src/LanceServeur


To run a client : 
