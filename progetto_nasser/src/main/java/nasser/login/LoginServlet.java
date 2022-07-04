package nasser.login;

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
import nasser.distributore.DistributoreBean;

/**
 * Servlet implementation class Login
 */
@WebServlet({ "/Login", "/login", "/index" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Query q = null;
		HttpSession session = request.getSession();
		
		try {
			q = DB.generaQuery(DB.queryLogin, email, password);
			DB.execute(q,"select");
			
			//di seguito verrà verificato il tipo utente in modo da memorizzare le informazioni nel bean opportuno 
			//e restituire la vista corretta (homeCliente o vistaDistributore)
			
			if(q.getStatoQuery() == DB.NORESULT) {
				//out.print("Nessun Risultato");
				response.sendRedirect(request.getContextPath() + "/riprova.html");
			}
			//-----------------CLIENTE-----------------------------------------------------------------------------
			else if(q.getStatoQuery()== 1 && q.getRisultati().get(1).get(1).equals("cliente")) {
				//istanzio clienteBean
				ClienteBean clienteBean = new ClienteBean(email,q.getRisultati());
				clienteBean.setIdCredenziali(q.getRisultati().get(1).get(0));
				
				//memorizzo il credito del cliente nel bean
				Query qCredito = DB.generaQuery(DB.queryCreditoCliente, clienteBean.getIdCredenziali());
				DB.execute(qCredito, "select");
				clienteBean.setCredito(qCredito.getRisultati().get(1).get(0));
				
				//memorizzo bean nella sessione
				session.setAttribute("clienteBean", clienteBean);
				
				//forward
				request.getRequestDispatcher("./WEB-INF/view/cliente/homeCliente.jsp").forward(request, response);
			}
			//------------------- DISTRIBUTORE -------------------------------------------------------------------
			else if(q.getStatoQuery()== 1 && q.getRisultati().get(1).get(1).equals("distributore")) {
				//istanzio distributoreBean
				DistributoreBean distributoreBean = new DistributoreBean(email);//,q.getRisultati());
				distributoreBean.setIdCredenziali(q.getRisultati().get(1).get(0));
				
				//memorizzo il bean nella sessione
				session.setAttribute("distributoreBean", distributoreBean);
				
				//forward
				request.getRequestDispatcher("./ListaProdottiController").forward(request, response);
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
