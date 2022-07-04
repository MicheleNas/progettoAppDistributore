package nasser.distributore.acquista;

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
 * Servlet implementation class AcquistaServlet
 */
@WebServlet({ "/AcquistaServlet", "/acquista" })
public class AcquistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcquistaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DistributoreBean distributoreBean = (DistributoreBean) session.getAttribute("distributoreBean");
		PrintWriter out = response.getWriter();
		String codDistributore = distributoreBean.getCodDistributore();
		Float prezzo = Float.parseFloat(request.getParameter("prezzo"));
		String codProdotto = request.getParameter("codProdotto");
		
		Float lastCreditoCliente = distributoreBean.getCreditoClienteConnesso();
		String credenzialiCliente = distributoreBean.getCredenzialiClienteConnesso();
		int statoDistributore = distributoreBean.getStatoDistributore();
		
		try {
			//verifico che il prodotto non sia esaurito
			Query queryQuantitaProdotto = DB.generaQuery(DB.selectQuantitaProdotto, codDistributore,codProdotto);
			DB.execute(queryQuantitaProdotto, "select");
			int quantitaProdotto = Integer.parseInt(queryQuantitaProdotto.getRisultati().get(1).get(0));
			
			if(statoDistributore == 1 && lastCreditoCliente >= prezzo && quantitaProdotto > 0) {
				Float newCreditoCliente = lastCreditoCliente - prezzo;
				
				//Aggiorno credito cliente
				Query queryUpdateCredito = DB.generaQuery(DB.updateCredito, newCreditoCliente, credenzialiCliente);
				DB.execute(queryUpdateCredito, "insert");
				
				//decremento la quantità del prodotto presente nel distributore
				Query queryQuantita = DB.generaQuery(DB.updateListaProdotti, codDistributore, codProdotto);
				DB.execute(queryQuantita, "insert");
				
				//ritorno true alla richiesta Ajax
				out.print("ok");
			}
			else if(statoDistributore == 1 && lastCreditoCliente < prezzo) {
				//ritorno false alla richiesta ajax
				out.print("ricarica");
			}
			else if(statoDistributore == 1 && quantitaProdotto <= 0) {
				out.print("esaurito");
			}
			else if(statoDistributore == 0) {
				out.print("disconnesso");
			}
				
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
