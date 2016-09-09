package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EvaluationServlet
 */
@WebServlet("/weva")
public class EvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationServlet() {
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
		
		
		DbHelper db = new DbHelper();
		
		String type = request.getParameter("type");
		String s = "";
		if (type != null && type.equals("estate")) {
			String estate = request.getParameter("value");
			String id = request.getParameter("id");
			s = "UPDATE OrderData SET estate='" + estate + "' where id=" + id + ";";
			System.out.println(s);
		}
		else if (type != null && type.equals("star")) {
						
			String starvalue = request.getParameter("starvalue");
			String id = request.getParameter("id");
			s = "UPDATE OrderTab SET star='" + starvalue + "' where id=" + id + ";";
		}	
		else if (type != null && type.equals("description")) {
				String contentvalue = request.getParameter("contentvalue");
				String id = request.getParameter("id");
				s = "UPDATE OrderTab SET description='" + contentvalue + "' where id=" + id + ";";
		}else {
		}
		db.update(s);
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
