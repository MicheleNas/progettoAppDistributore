package nasser.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB{
	public static String queryLogin = "SELECT id_credenziali, tipo_utente FROM Credenziali WHERE BINARY email = ? and BINARY pass = ?";
	public static String queryCreditoCliente = "SELECT credito, nome FROM Cliente WHERE id_credenziali = ?";
	
	public static String queryCodDistributore = "SELECT prodotto.tipo_prodotto, prodotto.cod_prodotto, prodotto.nome_prodotto, prodotto.prezzo, lista_prodotti.cod_distributore "
												+"FROM lista_prodotti "
												+"INNER JOIN Prodotto ON prodotto.cod_prodotto = lista_prodotti.cod_prodotto and lista_prodotti.quantita != 0 "
												+					"and lista_prodotti.cod_distributore = (SELECT distributore.cod_distributore "
												+															"FROM Distributore "
												+                                                           "INNER JOIN Credenziali ON credenziali.email = ? "
												+																	"and credenziali.id_credenziali = distributore.id_credenziali) "
												+"ORDER BY prodotto.tipo_prodotto";
	
	public static String emailVerify = "SELECT email FROM Credenziali WHERE BINARY email = ?";
	public static String insertCredenziali = "INSERT INTO Credenziali (email, pass, tipo_utente) VALUES (?, ?, 'cliente') ";
	public static String insertCliente = "INSERT INTO Cliente (id_cliente, nome, cognome, credito, id_credenziali) VALUES (UUID(), ?, ?, 0, (select id_credenziali from Credenziali where email = ?))";
	
	public static String insertLogCliente = "INSERT INTO registro_connessioni (id_credenziali_cliente, cod_distributore, stato_connessione) VALUES (?, ?, ?)";
	public static String queryStatoCliente = "select stato_connessione from registro_connessioni where id_credenziali_cliente = ? order by cod_log desc limit 1";
	public static String queryStatoDistributore = "select stato_connessione, id_credenziali_cliente, data_ora from registro_connessioni where cod_distributore = ? order by cod_log desc limit 1";
	public static String queryEsistenzaDistributore ="SELECT * FROM distributore WHERE cod_distributore = ?";
	
	public static String updateCredito = "UPDATE cliente SET credito = ?  WHERE id_credenziali = ?";
	
	public static String updateListaProdotti = "UPDATE lista_prodotti SET quantita = quantita - 1  WHERE cod_distributore = ? and cod_prodotto = ?";
	
	public static String selectQuantitaProdotto = "SELECT quantita FROM lista_prodotti WHERE cod_distributore = ? and cod_prodotto = ?";
	
	public static final int RESULT = 1;
	public static final int NORESULT = 2;
	public static final int ERROR = 3;

	
	
	public static Query generaQuery(String tipoQuery, Object... p) throws SQLException {
		String querySql = tipoQuery;
		Query q;
		
		if(p.length >= 1) {
			List<Object> parametri = new ArrayList<>();
			for(int i=0; i<p.length; i++) {
				parametri.add(p[i]);
			}
			q = new Query(querySql, parametri);
		}
		else {
			System.out.println("Errore in DB.generaQuery(): non sono stati passati dei parametri");
			q = new Query(querySql, null);
		}
		return q;
	}
	
	
	public static void execute(Query q, String tipo) throws SQLException {
		try{
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/nasser_db");
			try(Connection connection  = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(q.getSql());){
				if(tipo.equals("select")){
					setParametri(statement, q.getParametri());
					statement.executeQuery();
					q.setRisultati(statement.getResultSet());
				}
				else if(tipo.equals("insert")) {
					setParametri(statement, q.getParametri());
					statement.executeUpdate();
					q.setUpdateCount(statement.getUpdateCount());
				}	
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
//il metodo setParametri permette di inserire tutti i parametri prelevati dal form all'interno della query
	public static void setParametri(PreparedStatement statement, List<Object> parametri) {
		try {		
			for(int i=0; i< parametri.size(); i++) {
					statement.setObject(i+1, parametri.get(i) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}