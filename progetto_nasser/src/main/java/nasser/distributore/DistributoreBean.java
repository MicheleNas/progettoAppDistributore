package nasser.distributore;

//import java.sql.SQLException;

import java.util.Date;

public class DistributoreBean{
	private String email;
	private String credenzialiDistributore;
	private String codDistributore;
	private int statoDistributore;
	
	//memorizzo informazioni per il timer di controllo connessione
	private Date dataStatoDis = new java.util.Date(0);				//contiene la data che risale all'ultimo aggiornamento sulla connessione del distibutore
	private long timeout = 120000;	//con questa variabile setto il timer della connessione in millisecondi; attualmente è impostata ad un minuto
	
	//attributi per memorizzare i dati dell'utente connesso
	private String credenzialiClienteConnesso;
	private String nomeClienteConnesso;
	private Float creditoClienteConnesso;
	
	
	public DistributoreBean(String email){
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	
	public void setIdCredenziali(String cod) {
		this.credenzialiDistributore = cod;
	}
	
	public String getIdCredenziali() {
		return this.credenzialiDistributore;
	}
	
	public void setCodDistributore(String cod) {
		this.codDistributore = cod;
	}
	
	public String getCodDistributore() {
		return this.codDistributore;
	}

	public void setStatoDistributore(int stato) {
		this.statoDistributore = stato;
	}
	
	public int getStatoDistributore() {
		return this.statoDistributore;
	}
	
	public void setClienteConnesso(String credenziali, String nome, Float credito) {
		this.credenzialiClienteConnesso = credenziali;
		this.nomeClienteConnesso = nome;
		this.creditoClienteConnesso = credito;
	}
	
	public String getCredenzialiClienteConnesso() {
		return this.credenzialiClienteConnesso;
	}
	
	public String getNomeClienteConnesso() {
		return this.nomeClienteConnesso;
	}
	
	public Float getCreditoClienteConnesso() {
		return this.creditoClienteConnesso;
	}
	
	public void setDataOra(Date dataOra) {
		this.dataStatoDis = dataOra;
	}
	
	//restituisce quanto tempo è trascorso dall'ultima connessione che ha avuto il distributore
	public long getTimer() {
		if(this.dataStatoDis.getTime() > 0) {
			return (this.dataStatoDis.getTime()+this.timeout)-(new java.util.Date()).getTime();
		}
		else {
			return 0;
		}
	}
	
	//viene chiamato quando il distributore risulta connesso e verifica se è scaduto il tempo disponibile per l'acquisto del prodotto
	//questo controllo torna utile quando l'utente dimentica la connessione aperta
	public boolean checkTimeout() {
		long diff = (new java.util.Date()).getTime() - this.dataStatoDis.getTime();
		if(diff < this.timeout && diff > 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
}