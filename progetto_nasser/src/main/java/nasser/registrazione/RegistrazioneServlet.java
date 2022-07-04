package nasser.registrazione;

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
 * Servlet implementation class RegistrazioneController
 */
@WebServlet({ "/RegistrazioneController", "/registrazioneController" })
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
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
		Query q1, q2;
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		
		try {
			q1 = DB.generaQuery(DB.insertCredenziali, email, password);
			q2 = DB.generaQuery(DB.insertCliente, nome, cognome, email);
			DB.execute(q1,"insert");
			DB.execute(q2, "insert");
			
			//verifico lo stato delld query; mi aspetto che restituisca NORESULT poichè le query sono delle Insert e non Select
			if(q2.getStatoQuery() == DB.NORESULT && q1.getStatoQuery() == DB.NORESULT){
				request.getRequestDispatcher("./WEB-INF/view/registrazione/registrazioneSuccesso.jsp").forward(request, response);
			}
			else{
				out.print("Errore, stato query: "+q2.getStatoQuery());
			}
			
		} catch (SQLException e) {
			//l'errore 1062 ci avvisa che l'email esista già nel DB
			if(e.getErrorCode() == 1062) {
				request.getRequestDispatcher("./WEB-INF/view/registrazione/registrazioneEmailEsistente.jsp").forward(request, response);
			}
			else {
				e.printStackTrace();
			}
			
		}
	}

}
