package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbHelper db = new DbHelper();
		String uid = request.getParameter("uid");
		String passwd = request.getParameter("passwd");
		String s = "Select uid, phone, email from MemberData where name='" + uid + "' and passwd='" + passwd +"'";
		String res = db.query(s);
		if (!res.equals("")) {
			response.getWriter().append(res);
			String[] strArray = res.split(",");
			s = "Select uid from MasterData where uid='" + strArray[0] +"'";
			System.out.println("isMaster query:" + s);
			res = db.query(s);
			System.out.println("isMaster:" + res);
			if (res.equals("")) {
				response.getWriter().append("0");
			}
			else {
				response.getWriter().append("1");
			}
		}
		db.finish();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
