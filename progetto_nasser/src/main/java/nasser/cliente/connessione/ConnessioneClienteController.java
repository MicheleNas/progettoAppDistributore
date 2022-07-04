package nasser.cliente.connessione;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nasser.cliente.ClienteBean;
import nasser.db.DB;
import nasser.db.Query;

/**
 * Servlet implementation class ConnessioneClienteController
 */
@WebServlet({ "/ConnessioneClienteController", "/connessioneCliente" })
public class ConnessioneClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnessioneClienteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ClienteBean clienteBean = (ClienteBean) session.getAttribute("clienteBean");
		String codDistributore = request.getParameter("codDistributore");
		String idCredenzialiCliente = clienteBean.getIdCredenziali();
		Query queryStatoCliente;
		Query queryStatoDistributore;
		Query queryEsistenzaDistributore;
		Query queryConnessione;
		ConnessioneClienteModel clienteModel = new ConnessioneClienteModel(idCredenzialiCliente, codDistributore);
		
		try {
			//verifico lo stato dell'utente
			queryStatoCliente = DB.generaQuery(DB.queryStatoCliente, idCredenzialiCliente);
			DB.execute(queryStatoCliente,"select");
			clienteModel.setStatoCliente(queryStatoCliente);
			
			//verifico se esiste il distributore
			queryEsistenzaDistributore = DB.generaQuery(DB.queryEsistenzaDistributore, codDistributore);
			DB.execute(queryEsistenzaDistributore,"select");
			
			//Verifico lo stato del distributore SE ESISTE
			if(queryEsistenzaDistributore.getStatoQuery() == DB.RESULT) {
				queryStatoDistributore = DB.generaQuery(DB.queryStatoDistributore, codDistributore);
				DB.execute(queryStatoDistributore,"select");
				clienteModel.setStatoDistributore(queryStatoDistributore);
			}
			
			//gestisco connessione in dipendenza degli stati del cliente e del distributore
			if(queryEsistenzaDistributore.getStatoQuery()==DB.NORESULT) {
				//se non abbiamo ottenuto nessun risultato allora il codDistributore inserito non esiste
				out.print("codiceInesistente");
			}
			else if(!clienteModel.getStatoCliente() && !clienteModel.getStatoDistributore() && !clienteModel.getConnessioneAperta()) {
				
				queryConnessione = DB.generaQuery(DB.insertLogCliente, idCredenzialiCliente, codDistributore,1); //1 = connesso
				DB.execute(queryConnessione,"insert");
				 
				if(queryConnessione.getStatoQuery()==DB.NORESULT) {
					//memorizzo il bean nella sessione in modo da richiamarmi i dati nella servlet disconnessione
					session.setAttribute("connessioneBean", clienteModel);
					
					request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneCliente/connesso.jsp").forward(request, response);	
				}
				else {
					out.print("Si è verificato un problema con lo stato della query; statoQuery: "+queryConnessione.getStatoQuery());
				}
			}
			else if(clienteModel.getConnessioneAperta()) {
				//Se la connessione risulta già aperta tra il cliente e il distributore, ritorno all'utente la vista CONNESSIONE
				//però non memorizzo la connessione nella lista_log poichè già risulta aperta.
				
				//memorizzo il bean nella sessione in modo da richiamarmi i dati nella servlet disconnessione
				session.setAttribute("connessioneBean", clienteModel);
				
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneCliente/connesso.jsp").forward(request, response);
			}
			else if(clienteModel.getStatoCliente()) {
				out.print("impegnato");
				}
			else if(clienteModel.getStatoDistributore()) {
				out.print("disImpegnato");
			}
			else {
				out.print("Errore");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
