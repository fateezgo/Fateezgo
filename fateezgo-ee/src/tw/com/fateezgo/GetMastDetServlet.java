package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetMastDetServlet
 */
@WebServlet("/getmastdet")
public class GetMastDetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMastDetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		String mastuid =request.getParameter("mastname");
		int mastuidint = Integer.valueOf(mastuid);
		
		DbHelper db = new DbHelper();
		String s="SELECT md.uid, md.name, mp.professional, ma.introduction "
				+ "FROM MemberData md JOIN MasProfData mp ON md.uid=mp.uid "
				+ "JOIN MasterData ma ON md.uid=ma.uid "
				+ "WHERE md.uid='"+mastuidint+"'";
		response.getWriter().append(db.query(s));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
