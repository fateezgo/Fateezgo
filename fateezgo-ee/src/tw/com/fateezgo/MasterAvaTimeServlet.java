package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MasterAvaTimeServlet
 */
@WebServlet("/AvaTime")
public class MasterAvaTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MasterAvaTimeServlet() {
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
			if (type.equals("set")) {
				String id = request.getParameter("id");
				String mon = request.getParameter("mon");
				String time = request.getParameter("time");
				s = "SELECT id FROM MasAvleTimeData WHERE uid=" + id + " and month='" + mon + "';";
				System.out.println(s);
				String res = db.query(s);
				System.out.println("ava time: " + res);
				if (res.equals("")) {
					s = "INSERT INTO `a105t2`.`MasAvleTimeData` (`uid`, `month`, `time`) VALUES ('"+ id +"', '" +
							mon + "', '" + 
							time + "');";
					System.out.println(s);
					db.update(s);
				}
				else {
					s = "UPDATE MasAvleTimeData SET time='"+ time + "' where uid=" + id + " and month='" + mon +"';";
					System.out.println(s);
					db.update(s);					
				}
			}
			else if (type.equals("get")) {
				String id = request.getParameter("id");
				String mon = request.getParameter("mon");
				s = "SELECT time FROM MasAvleTimeData WHERE uid=" + id + " and month='" + mon + "';";
				String res = db.query(s);
				String[] strArray = mon.split("-");
				int month = Integer.valueOf(strArray[1]) + 1;
				mon = strArray[0] + "-" + month + "-" + strArray[2];
				s = "SELECT time FROM MasAvleTimeData WHERE uid=" + id + " and month='" + mon + "';";
				res += db.query(s);
				System.out.println(res);
				response.setContentType("text/html;charset=UTF-8");		
				response.getWriter().append(res);
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
