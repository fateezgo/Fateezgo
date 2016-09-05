package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetOrderServlet
 */
@WebServlet("/SetOrder")
public class SetOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbHelper db = new DbHelper();
		String type = request.getParameter("type");
		String s = "";
		if (type.equals("estate")) {
			String estate = request.getParameter("value");
			String id = request.getParameter("id");
			s = "UPDATE OrderData SET estate='" + estate + "' where id=" + id + ";";
			System.out.println(s);
		}
		else {
			s = "UPDATE OrderData SET star=4 where id=1;";
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
