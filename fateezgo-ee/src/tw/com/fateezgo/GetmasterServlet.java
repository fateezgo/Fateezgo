package tw.com.fateezgo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class GetmasterServlet
 */
@WebServlet("/getmaster")
public class GetmasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetmasterServlet() {
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
		String requestType =request.getParameter("qtype");
	
		DbHelper db = new DbHelper();

		if (requestType.equals("free")) {
			
			String s="SELECT MemberData.name,MasProfData.professional "
								+ "FROM MemberData,MasProfData "
								+ "WHERE MemberData.uid=MasProfData.uid and MasProfData.cost=0 "
								+ "ORDER BY MemberData.uid";
			response.getWriter().append(db.query(s));
		} else if(requestType.equals("prof")){

		}else {
			
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
