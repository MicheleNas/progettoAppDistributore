package nasser.cliente.connessione;

//import java.util.List;

import nasser.db.DB;
import nasser.db.Query;

public class ConnessioneClienteModel{
	private int statoCliente;
	private int statoDistributore;
	private String idCredenziali;
	private String codDistributore;
	private boolean connessioneAperta = false;
	
	
	
	public ConnessioneClienteModel(String utente, String distributore){
		this.idCredenziali = utente;
		this.codDistributore = distributore;
	}
	
	public void setStatoCliente(Query queryStatoUtente){
		if(queryStatoUtente.getStatoQuery()==DB.NORESULT) {
			this.statoCliente = 0;
		}
		else {
			this.statoCliente = Integer.parseInt(queryStatoUtente.getRisultati().get(1).get(0));
		}
	}
	
	public boolean getStatoCliente() {
		if(this.statoCliente == 0) {
			return false;
		}
		else if(this.statoCliente == 1) {
			return true;
		}
		else {
			System.out.println("In ConnessioneClienteModel.getStatoCliente() lo stato non è stato settato");
			return false;
		}
	}
	
	public void setStatoDistributore(Query queryStatoDistributore){
		if(queryStatoDistributore.getStatoQuery()==DB.RESULT) {
			this.statoDistributore = Integer.parseInt(queryStatoDistributore.getRisultati().get(1).get(0));
		
			//verifico se la connessione è già aperta
			String credenziali = queryStatoDistributore.getRisultati().get(1).get(1);
			if(this.statoDistributore == 1 && this.idCredenziali.equals(credenziali)) {
				this.connessioneAperta = true;	
			}
		}
		else if(queryStatoDistributore.getStatoQuery()==DB.NORESULT) {
			this.statoDistributore = 0;						//caso in cui la lista dei log è vuota
		}
	}
	
	public boolean getStatoDistributore() {
		if(this.statoDistributore == 0) {
			return false;
		}
		else if(this.statoDistributore == 1) {
			return true;
		}
		else {
			System.out.println("ConnessioneClienteModel.getStatoDistributore() lo stato non è stato settato");
			return false;
		}
	}
	
	public String getIdCredenziali() {
		return this.idCredenziali;
	}
	
	public String getCodDistributore() {
		return this.codDistributore;
	}
	
	//true: il cliente è già connesso con questo distirbutore
	public boolean getConnessioneAperta() {
		return this.connessioneAperta;
	}
}