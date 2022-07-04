package nasser.cliente.ricarica;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nasser.cliente.ClienteBean;
import nasser.db.DB;
import nasser.db.Query;

/**
 * Servlet implementation class RicaricaServlet
 */
@WebServlet({ "/RicaricaServlet", "/ricarica" })
public class RicaricaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicaricaServlet() {
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
		ClienteBean clienteBean = (ClienteBean) request.getSession().getAttribute("clienteBean");
		String idCredenziali = clienteBean.getIdCredenziali();
		Float ricarica = Float.parseFloat(request.getParameter("ricarica"));
		
		try {
			//prelevo dal DB il vecchio credito
			Query queryInfoCliente = DB.generaQuery(DB.queryCreditoCliente, idCredenziali);
			DB.execute(queryInfoCliente, "select");
			Float creditoCliente = Float.parseFloat(queryInfoCliente.getRisultati().get(1).get(0));
			
			Float newCredito = creditoCliente + ricarica;
			
			//aggiorno DB
			Query q;
			q = DB.generaQuery(DB.updateCredito, newCredito, idCredenziali);
			DB.execute(q,"insert");
			
			if(q.getStatoQuery() == DB.NORESULT) {
				//aggiorno credito nel bean cliente
				clienteBean.setCredito(String.valueOf(newCredito));
				
				out.print(newCredito);
			}
			else {
				out.print("fail");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
