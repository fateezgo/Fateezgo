package tw.com.fateezgo;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

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
		System.out.println(type);
		String s = "";
		if (type != null) {
			if (type.equals("estate")) {
				String estate = request.getParameter("value");
				String id = request.getParameter("id");
				s = "UPDATE OrderTab SET estate='" + estate + "' where id=" + id + ";";
				System.out.println(s);
				db.update(s);
			}
			else if (type.equals("insert")) {
				Timestamp dd = new Timestamp(System.currentTimeMillis());
				String prof = request.getParameter("professionalid");
				String[] profs = prof.split(",");
				for (int i = 0 ; i < profs.length; i++) { 
					/*s = "INSERT INTO OrderTab('memberuid', 'masteruid', 'professionalid', 'pdate', 'estate', 'sn') VALUES ('" +
							request.getParameter("memuid") + "', '" +
							request.getParameter("masteruid") + "', '" +
							profs[i] + "', '" +
							"2016-09-01 03:36:08" + "', 'N', '" +
							System.currentTimeMillis() + "');";*/
					s = "INSERT INTO `a105t2`.`OrderTab` (`id`, `memberuid`, `masteruid`, `professionalid`, `pdate`, `rdate`, `rplace`, `estate`, `sn`, `star`, `description`) VALUES (NULL, '" +
							request.getParameter("memuid") + "', '" +
							request.getParameter("masteruid") + "', '" +
							profs[i] + "', '" +
							dd + "', NULL, NULL, 'N', '" +
							System.currentTimeMillis() + "', NULL, NULL)";
					System.out.println(s);
					db.update(s);
				}
			}
			else if (type.equals("rdate")) {
				String id = request.getParameter("id");
				String date = request.getParameter("date");
				s = "UPDATE OrderTab SET rdate='"+ date + "' where id=" + id + ";";
				System.out.println(s);
				db.update(s);
			}
		}
		else {
			s = "UPDATE OrderData SET star=4 where id=1;";
			db.update(s);
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
