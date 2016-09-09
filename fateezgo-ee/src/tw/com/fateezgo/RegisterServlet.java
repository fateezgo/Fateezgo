package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void registMem(DbHelper db, String name, String passwd, String phone, String email) {
		String s = "INSERT INTO `a105t2`.`MemberData` (`uid`, `name`, `passwd`, `phone`, `email`) VALUES (NULL, '" +
				name + "', '" +
				passwd + "', '" +
				phone + "', '" +
				email + "')";
		System.out.println(s);
		db.update(s);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbHelper db = new DbHelper();
		String type = request.getParameter("type");
		System.out.println(type);
		String s = "";
		if (type.equals("member")) {
			registMem(db, request.getParameter("name"), request.getParameter("passwd"), request.getParameter("phone"),request.getParameter("email"));
		}
		else if (type.equals("master")) {
			registMem(db, request.getParameter("name"), request.getParameter("passwd"), request.getParameter("phone"),request.getParameter("email"));
			s = "SELECT uid FROM MemberData WHERE name='" + request.getParameter("name") + "' and passwd='" + request.getParameter("passwd") + "';";
			System.out.println(s);
			String id = db.queryOneRow(s);
			System.out.println("id:" + s);
			s = "INSERT INTO `a105t2`.`MasterData` (`id`, `uid`, `gender`, `introduction`, `evasum`, `link`) VALUES (NULL, '" +
					id + "', NULL, NULL, NULL, NULL);";
			System.out.println(s);
			db.update(s);
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
