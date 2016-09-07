package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetOrderServlet
 */
@WebServlet("/order")
public class GetOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String queryForName(DbHelper db, String res) {
    	//memberuid, masteruid, professionalid, pdate, rdate, rplace, estate, sn
		String sOut = "";
		String[] resArray = res.split(",");
		
		sOut += resArray[0];
		sOut += ",";
		sOut += db.queryOneRow("SELECT name FROM MemberData WHERE uid=" + resArray[0]);
		sOut += ",";
		
		sOut += resArray[1];
		sOut += ",";
		sOut += db.queryOneRow("SELECT name FROM MemberData WHERE uid=" + resArray[1]);
		sOut += ",";
		
		sOut += db.queryOneRow("SELECT professional FROM MasProfData WHERE id=" + resArray[2]);
		sOut += ",";
		
		sOut += resArray[3];	//pdate
		sOut += ",";

		sOut += resArray[4];	//rdate
		sOut += ",";
		
		sOut += db.queryOneRow("SELECT place FROM MasPlaData WHERE id=" + resArray[5]);
		sOut += ",";

		sOut += resArray[6];	//estate
		sOut += ",";

		sOut += resArray[7];	//sn

    	return sOut;
    }
    
   	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbHelper db = new DbHelper();
		String sOut = "";
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		if (type != null) {
			if (type.equals("one")) {
				String s = "SELECT memberuid, masteruid, professionalid, pdate, rdate, rplace, estate, sn FROM OrderTab WHERE id=" + id;
				String res = db.query(s);
				sOut = queryForName(db, res);
			}
			else {
				String s = "SELECT memberuid, masteruid, professionalid, pdate, rdate, rplace, estate, sn FROM OrderTab WHERE memberuid=" + id + " or masteruid=" + id + ";";
				String res = db.query(s);
				String[] strArray = res.split("\n");
				for (int i = 0; i < strArray.length; i++) {
					strArray[i] = queryForName(db, strArray[i]);
				}

				for (int i = 0; i < strArray.length; i++) {
					sOut += strArray[i];
					sOut += "\n";
				}
			}
		}
		response.setContentType("text/html;charset=UTF-8");		
		response.getWriter().append(sOut);
		
		System.out.println(sOut);
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
