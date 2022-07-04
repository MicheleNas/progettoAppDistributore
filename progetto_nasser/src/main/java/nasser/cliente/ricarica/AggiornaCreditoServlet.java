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
 * Servlet implementation class AggiornaCredito
 */
@WebServlet({ "/AggiornaCredito", "/aggiornaCredito" })
public class AggiornaCreditoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiornaCreditoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ClienteBean clienteBean = (ClienteBean) request.getSession().getAttribute("clienteBean");
		String idCredenziali = clienteBean.getIdCredenziali();
		
		try {
			//prelevo dal DB il credito
			Query queryInfoCliente = DB.generaQuery(DB.queryCreditoCliente, idCredenziali);
			DB.execute(queryInfoCliente, "select");
			Float creditoCliente = Float.parseFloat(queryInfoCliente.getRisultati().get(1).get(0));
			
			out.print(creditoCliente);
			
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
