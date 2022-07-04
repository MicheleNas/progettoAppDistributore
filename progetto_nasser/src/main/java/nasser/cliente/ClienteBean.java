package nasser.cliente;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import nasser.db.model.Query;

public class ClienteBean{
	
	private List<List<String>> risultati = new ArrayList<>();
	private String email;
	private String idCredenziali;
	private float credito;
	
	
	public ClienteBean(String email, List<List<String>> risultati) {
		this.email = email;
		if(risultati.size() > 1){
			this.risultati = risultati;
			
		}
	}
	
	public List<List<String>> getRisultati(){
		return this.risultati;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setIdCredenziali(String id) {
		this.idCredenziali = id;
	}
	
	public String getIdCredenziali() {
		return this.idCredenziali;
	}
	
	public void setCredito(String credito) {
		this.credito = Float.parseFloat(credito);
	}
	
	public Float getCredito() {
		return this.credito;
	}
}