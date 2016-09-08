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
		String profType = request.getParameter("profType");
	
		DbHelper db = new DbHelper();
		if (requestType.equals("free")) {
			
			String s="SELECT MemberData.uid, MemberData.name,MasProfData.professional "
								+ "FROM MemberData,MasProfData "
								+ "WHERE MemberData.uid=MasProfData.uid and MasProfData.cost=0";
			response.getWriter().append(db.query(s));
		} else if(requestType.equals("prof")){
			String s="";
				switch (profType) {
				case "astromast":
					 s ="SELECT md.uid, md.name, mp.professional FROM MemberData md JOIN MasProfData mp ON md.uid=mp.uid JOIN MasterData ma ON md.uid=ma.uid WHERE mp.professional=\"占星\"ORDER BY ma.evasum DESC";
					 
					break;

				case "tarot":
					 s ="SELECT md.uid, md.name, mp.professional FROM MemberData md JOIN MasProfData mp ON md.uid=mp.uid JOIN MasterData ma ON md.uid=ma.uid WHERE mp.professional=\"塔羅\"ORDER BY ma.evasum DESC";
					break;
				}
			
			response.getWriter().append(db.query(s));
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
