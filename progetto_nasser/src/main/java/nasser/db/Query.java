package nasser.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Nell'oggetto query memorizzo il risultato arrivato come risposta dal DB
//in modo da gestirlo successivamente nella model
public class Query{
	
	private Date data_ora = null;
	private String sql;
	private List<Object> parametri = new ArrayList<>();
	private List<List<String>> risultati = new ArrayList<>();
	private int uc = 0;
	private int statoQuery = -1;
	
	
	
	public Query(String sql, List<Object> parametri) {
		this.sql = sql;
		this.parametri = parametri;
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public List<Object> getParametri(){
		return this.parametri;
	}
	
	public void setStatoQuery() {
		if(this.risultati.size() == 1 || this.uc == 1) {
			this.statoQuery = DB.NORESULT;
		}
		else if(this.risultati.size() > 1){
			this.statoQuery = DB.RESULT;
		}
		else {
			this.statoQuery = DB.ERROR;
		}
	}

	public int getStatoQuery() {
		return this.statoQuery;
	}
	
	public int getUpdateCount() {
		return uc;
	}

	public void setUpdateCount(int uc) {
		this.uc = uc;
		this.setStatoQuery();
	}
	
	public void setRisultati(ResultSet rs) {
		try {
			
			//dichiaro una variabile contente i metadati, cioè i nomi delle colonne
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			// il primo elemento della lista sarà un arraylist contente i nomi delle colonne
			List<String> nomiColonne = new ArrayList<>();	
			for (int i = 0; i < columnCount; i++) {
				nomiColonne.add(rsmd.getColumnLabel(i + 1));
			}
			this.risultati.add(nomiColonne);
			
			// memorizzo le restanti righe	
			while (rs.next()) {
				
				List<String> row = new ArrayList<>();
				for (int i = 0; i < columnCount; i++) {
					
					//memorizzo un eventuale dato di tipo Date; mi serve per il timer della lista_log
					if((rs.getObject(i+1) instanceof java.time.LocalDateTime)) {
						java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("data_ora");    
						this.data_ora = new java.util.Date(dbSqlTimestamp.getTime());
					}
					
					row.add(rs.getString(i + 1));
				}
				this.risultati.add(row);
			}
			
			this.setStatoQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setStatoQuery();
		}
	}
	
	public List<List<String>> getRisultati() {
		return this.risultati;
	}
	
	public Date getDataOra() {
		if(this.data_ora.getTime()>0) {
			return this.data_ora;
		}
		else {
			return null;
		}
	}
}