package nasser.distributore.connessione.timer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nasser.distributore.DistributoreBean;

/**
 * Servlet implementation class TimerDistributore
 */
@WebServlet({ "/TimerDistributore", "/timer" })
public class TimerDistributore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimerDistributore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		DistributoreBean distributoreBean = (DistributoreBean) session.getAttribute("distributoreBean");
		
		if(distributoreBean.getStatoDistributore()== 1 && distributoreBean.checkTimeout()) {
			
			long timer = distributoreBean.getTimer();
			out.print(timer);
		}
		else {
			out.print("disconnesso");
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
