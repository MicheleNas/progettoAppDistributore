package nasser.distributore.lista_prodotti;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ListaProdottiModel {

	private List<List<String>> risultati = new ArrayList<>();
	private JsonObject fileJson = new JsonObject();
	private String codDistributore;
	private List<String> categorieProdotti = new ArrayList<>();
	
	
	
	public void setRisultati(List<List<String>> risultati) {
		this.risultati = risultati;
		this.codDistributore = risultati.get(1).get(4);
	}
	
	
	
	//genero un file Json per la lista prodotti in modo da facilitare l'accesso ai dati 
	//quando mi ritroverò a prelevarli nella jsp vistaDistributore
	public void generaJson() {
		for(int i=1; i<risultati.size(); i++) {										//Memorizzo in una lista tutte le categorie-prodotto del distributore.					
			if(i == 1) {															//Per aggiungerlo verifico se è uguale con quello precedente poichè la 
				categorieProdotti.add(risultati.get(i).get(0));						//query mi restituisce un lista ordinata per categoria
			}
			else if(!risultati.get(i).get(0).equals(risultati.get(i-1).get(0))) {
				categorieProdotti.add(risultati.get(i).get(0));
			}
		}
		//intestazione
		JsonArray intestazione = new JsonArray();
		for(int l=1; l<risultati.get(0).size()-1; l++) {
			intestazione.add(risultati.get(0).get(l));
		}
		this.fileJson.add("intestazione", intestazione);
		
		//aggiungo attributi al fileJson per categoria
		for(int i=0; i < categorieProdotti.size(); i++) {
			this.fileJson.add(categorieProdotti.get(i), new JsonArray());
		}
		//scorriamo tutte le righe
		for(int i=1; i<risultati.size(); i++) {
			
			//memorizzo i dati della riga in un JsonArray
			JsonArray riga = new JsonArray();
			for(int l=1; l<risultati.get(i).size()-1; l++) {       //il -1 serve per non far memorizzare l'ultimo elemento (codDistirbutore)
				riga.add(risultati.get(i).get(l));
			}
			this.fileJson.getAsJsonArray(risultati.get(i).get(0)).add(riga);
		}
	}
	
	public String getCodDistributore() {
		return this.codDistributore;
	}
	
	public JsonObject getFileJson() {
		return this.fileJson;
	}
	
	//questo metodo tornerà utile nella jsp vistaDistributore quando dovranno essere generate le tabs
	public List<String> getCategorieProdotti(){
		return this.categorieProdotti;
	}
}