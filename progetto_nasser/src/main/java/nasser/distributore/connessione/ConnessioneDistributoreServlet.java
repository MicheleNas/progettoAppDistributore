package nasser.distributore.connessione;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nasser.db.DB;
import nasser.db.Query;
import nasser.distributore.DistributoreBean;

/**
 * Servlet implementation class ConnessioneDistributoreServlet
 */
@WebServlet("/ConnessioneDistributoreServlet")
public class ConnessioneDistributoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnessioneDistributoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Integer statoDistributore = 0;
		String idCredenzialiClienteConnesso = "";
		boolean checkTimeout = false;
		
		HttpSession session = request.getSession();
		DistributoreBean distributoreBean = (DistributoreBean) session.getAttribute("distributoreBean");
		
		String codDistributore = distributoreBean.getCodDistributore();
		
		try {
			//verifico lo stato del distributore
			Query queryStatoDistributore = DB.generaQuery(DB.queryStatoDistributore, codDistributore);
			DB.execute(queryStatoDistributore,"select");
			
			//se la query ritorna dei risultati vuol dire che nella lista dei log esiste almeno un log
			//quindi posso inizializzare le seguenti variabili
			if(queryStatoDistributore.getStatoQuery() == DB.RESULT) {
				
				statoDistributore = Integer.parseInt(queryStatoDistributore.getRisultati().get(1).get(0));
				idCredenzialiClienteConnesso = queryStatoDistributore.getRisultati().get(1).get(1);
				
				if(statoDistributore == 1) {
					//setto Data ora
					distributoreBean.setDataOra(queryStatoDistributore.getDataOra());
					checkTimeout = distributoreBean.checkTimeout();	//restituisce il valore in minuti
				}
			}
			
			if(statoDistributore == 1 && checkTimeout) {
				//con questa query ricevo le informazioni del cliente: nome e credito; passo come parametro l'id delle credenziali
				//dell'utente che ricevo dalla queryStatoDistributore
				Query queryInfoCliente = DB.generaQuery(DB.queryCreditoCliente, idCredenzialiClienteConnesso);
				DB.execute(queryInfoCliente, "select");
				Float creditoCliente = Float.parseFloat(queryInfoCliente.getRisultati().get(1).get(0));
				String nomeCliente = queryInfoCliente.getRisultati().get(1).get(1);
				
				//memorizzo i dati in distributoreBean (istanziato durante il login e memorizzato nella sessione)
				distributoreBean.setClienteConnesso(idCredenzialiClienteConnesso, nomeCliente, creditoCliente);
				distributoreBean.setStatoDistributore(1);
				
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneDistributore/connessoDistributore.jsp").forward(request, response);
			}
			else if(statoDistributore == 1 && !checkTimeout) {
				//chiudo la connessione se è passato troppo tempo
				Query queryConnessione = DB.generaQuery(DB.insertLogCliente, idCredenzialiClienteConnesso, codDistributore,0);
				DB.execute(queryConnessione,"insert");
				
				distributoreBean.setStatoDistributore(0);
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneDistributore/disconnessoDistributore.jsp").forward(request, response);
			}
			else if(statoDistributore == 0) {
				distributoreBean.setStatoDistributore(0);
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneDistributore/disconnessoDistributore.jsp").forward(request, response);
			}
			else {
				out.print("Errore");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
