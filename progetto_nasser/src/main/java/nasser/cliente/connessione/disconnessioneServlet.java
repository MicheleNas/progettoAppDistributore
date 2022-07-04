package nasser.cliente.connessione;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nasser.db.DB;
import nasser.db.Query;

/**
 * Servlet implementation class disconnessioneServlet
 */
@WebServlet("/disconnessioneServlet")
public class disconnessioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public disconnessioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ConnessioneClienteModel connessioneBean = (ConnessioneClienteModel) request.getSession().getAttribute("connessioneBean");
		String idCliente = connessioneBean.getIdCredenziali();
		String codDistributore = connessioneBean.getCodDistributore();
		
		try {
			//verifico che il distributore non si è disconnesso da solo 
			//(se si è disconnesso da solo non registro la disconnessione data dal tasto "Disconnettiti" situato in homeCliente
			//	poichè è stato il distributore stesso a registrarla)
			Integer statoDistributore = 1; 
			Query queryStatoDistributore = DB.generaQuery(DB.queryStatoDistributore, codDistributore);
			DB.execute(queryStatoDistributore,"select");
			if(queryStatoDistributore.getStatoQuery() == DB.RESULT) {
				statoDistributore = Integer.parseInt(queryStatoDistributore.getRisultati().get(1).get(0));
			}
			
			//effettuo disconessione; se è già disconesso non registro la disconnessione perchè è già stata registrata dal distributore stesso
			if(statoDistributore == 1) {
				//il parametro 1 serve per settare lo stato della connessione come "DISCONNESSO"
				Query queryConnessione = DB.generaQuery(DB.insertLogCliente, idCliente, codDistributore, 0);
				DB.execute(queryConnessione,"insert");
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneCliente/disconnesso.jsp").forward(request, response);	
			}
			else if(statoDistributore == 0) {
				request.getRequestDispatcher("/WEB-INF/view/componentiConnessione/connessioneCliente/disconnesso.jsp").forward(request, response);	
			}
			else {
				out.print("Si è verificato un problema");
			}
		}
		catch (SQLException e) {
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
