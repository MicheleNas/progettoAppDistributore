# progettoAppDistributore

SMART VENDING MACHINE

GENERAL DESCRIPTION
La piattaforma realizzata si occupa della gestione degli acquisti nei distributori automatici offrendo due viste. Tramite la vista per il cliente quest’ultimo potrà registrarsi alla piattaforma, ricaricare il proprio borsellino elettronico e connettersi al distributore. Tramite la vista distributore, il cliente, dopo essersi connesso, potrà procedere con l’acquisto del prodotto.

SYSTEM ARCHITECTURE
Tecnologie utilizzate:
1.	HTML5, CSS3 e Bootstrap per la gestione delle varie interfacce. 
2.	JavaScript e Jquery per lo scripting di funzioni di utilità grafica.
3.	Ajax per la gestione delle richieste asincrone al server Apache Tomcat per il deployment dell’applicazione.
1.	Classi Java Servlet per la gestione di richieste http e per il processo di validazione ed elaborazione dei dati inseriti dal client.
2.	JSP per la costruzione di pagine web di risposta al client.
3.	MySql come DBMS relazionale per la memorizzazione dei dati dei clienti e distributori. 
4.	JDBC come driver per l’integrazione con il DB.
5.	Eclipse IDE for Java EE Developers come ambiente di sviluppo.


FUNCTIONAL REQUIREMENTS
Generali:
1.	La piattaforma presenterà inizialmente l’interfaccia di login. In essa verrà mostrato un riferimento ad una pagina di registrazione per il cliente.

2.	L’utente può accedere come Cliente o Distributore.


3.	Interfaccia grafica dopo l’accesso:

1.	Cliente: 
L’interfaccia grafica conterrà una navbar la quale permette al cliente di selezionare l’interfaccia per la connessione con il distributore oppure l’interfaccia per la ricarica del borsellino elettronico.
Dopo l’accesso viene mostrata di default l’interfaccia di connessione con il distributore.

2.	Distributore: 
Dopo aver fatto l’accesso come distributore verrà mostrata la lista contenente tutti i prodotti del distributore; ogni prodotto sarà affiancato dal tasto acquista. 
La pagina conterrà due contenitori grafici: nel primo contenitore verranno mostrate tutte le informazioni relative alla connessione con il cliente (Connesso/Disconnesso, Nome Cliente Connesso, Credito Cliente), il secondo contenitore mostrerà la lista dei prodotti.

4.	Dopo che il cliente si connette al distributore, tramite la vista-Cliente, potrà selezionare il prodotto da acquistare tramite la vista-Distributore.

5.	Dopo aver effettuato l’acquisto l’utente può proseguire con l’acquisto di altri prodotti oppure disconnettersi dal distributore

6.	Se il cliente non effettua la disconnessione essa avverrà in automatico dopo un certo intervallo di tempo.


Client side:
Lo stile, lo scripting frontend e le interfacce in generale sono stati realizzati attraverso componenti Bootstrap in HTML, CSS e JavaScript. Dopo aver effettuato l’accesso la piattaforma si basa su una single-page la quale viene aggiornata dinamicamente tramite l’interazione dell’utente attraverso delle richieste asincrone con Ajax.
Funzionalità Cliente:
1.	Inserisce il codice distributore e viene inviato tramite Ajax.
2.	Se è connesso verrà mostrato il tasto disconnettiti; se premuto verrà inviata una richiesta ajax per la disconnessione.
3.	Inserisce il valore della ricarica e viene inviata tramite Ajax.
Ad ogni richiesta ajax seguirà una risposta testuale all’interno di un contenitore o in certi casi di un Allert.
Funzionalità Distributore: 
1.	Tramite la funzione setInterval() di JavaScript verranno inviate delle richieste periodiche per verificare lo stato dell’utente. Se l’utente risulta essere connesso all’ora nella vista distributore risulterà la connessione con esso, altrimenti il distributore mostrerà il la scritta “Disconnesso”.
2.	Il tasto Acquista invierà una richiesta Ajax per effettuare l’acquisto. Se ritorna esito positivo verrà mostrata un Modal che simula l’erogazione del prodotto.

Server side:
Ogni richiesta inviata verrà gestita secondo il modello MVC o delle singole Java Servlet. Per l’accertamento di determinati dati inseriti vengono usate le Servlet, mentre per operazioni più complesse che comprendo il controllo di dati, seguito dal controllo, elaborazione e invio di dati in risposta, viene usato il modello MVC.
DATA MODEL
![image](https://user-images.githubusercontent.com/108678151/177190522-a989e19a-9ae2-4007-9f04-ad6e1af98950.png)

 
NOTE
La tabella credenziali serve per poter agevolare la verifica dell’esistenza dell’email (l’email deve essere univoca) e per verificare che tipo di utente sta accedendo in modo da fornirgli in output la vista adeguata (cliente o distributore).
Il registro dei log tiene traccia di tutte le connessioni che avvengono tra gli utenti e i distributori. Se l’ultima riga inserita, tra tutte le righe in cui avviene la corrispondenza con il codice distributore, da come stato della connessione il valore ‘0’ vuol dire che il distributore non è impegnato in nessuna connessione; se lo stato è ‘1’ allora il distributore è già impegnato.
