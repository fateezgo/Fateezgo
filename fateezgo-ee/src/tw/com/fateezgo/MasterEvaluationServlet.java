package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MasterEvaluationServlet
 */
@WebServlet("/masev")
public class MasterEvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MasterEvaluationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; Charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String uid = request.getParameter("masuid");
		System.out.println("MasterEvaluation"+uid);
		int id = Integer.valueOf(uid);
		DbHelperMASEV db = new DbHelperMASEV();
		String s ="SELECT md.name, ot.description, ot.star FROM OrderTab ot "
			+ " JOIN MasterData ms ON ms.uid=ot.masteruid "
			+ " JOIN MemberData md ON md.uid=ot.memberuid "
			+ " WHERE ot.masteruid ="+ id + " ORDER BY md.uid ASC ;";		
		System.out.println(s);
		
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
