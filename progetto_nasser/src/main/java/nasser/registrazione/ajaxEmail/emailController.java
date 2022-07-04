package nasser.registrazione.ajaxEmail;

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
 * Servlet implementation class emailController
 */
@WebServlet("/emailController")
public class emailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public emailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		Query q;
		
		try {
			q = DB.generaQuery(DB.emailVerify, email);
			DB.execute(q, "select");
			
			//verifico lo stato della query; se la query restituisce un risultato allora
			//verrà ritornato come risposta il messaggio 'esiste'
			if(q.getStatoQuery() == DB.RESULT) {
				out.print("esiste");
			}
			else {
				out.print("non esiste");
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
