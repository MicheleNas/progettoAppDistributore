package nasser.distributore.lista_prodotti;

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
import nasser.distributore.DistributoreBean;

/**
 * Servlet implementation class ListaProdottiController
 */
@WebServlet({ "/ListaProdottiController", "/lista" })
public class ListaProdottiController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaProdottiController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		DistributoreBean distributoreBean = (DistributoreBean) request.getSession().getAttribute("distributoreBean");
		String emailDistributore = distributoreBean.getEmail();
		Query q = null;
		
		ListaProdottiModel listaDistributoreBean = new ListaProdottiModel();	
		
		try {
			q = DB.generaQuery(DB.queryCodDistributore, emailDistributore);
			DB.execute(q, "select");
			
			listaDistributoreBean.setRisultati(q.getRisultati());
			listaDistributoreBean.generaJson();
			
			//setto codDistributore in distribuotoreBean
			distributoreBean.setCodDistributore(listaDistributoreBean.getCodDistributore());
			
			request.setAttribute("beanListaDistributore",listaDistributoreBean);
			
			//verifico se la query ha restituito dei risultati
			if(q.getStatoQuery()== DB.NORESULT) {
				out.print("Nessun Risultato");
			}
			else if(q.getStatoQuery() == DB.RESULT) {
				request.getRequestDispatcher("/WEB-INF/view/distributore/vistaDistributore.jsp").forward(request, response);
			}
			else {
				out.print("Errore");
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
