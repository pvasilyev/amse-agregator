import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleGui extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String query = request.getParameter("query");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Agregator!!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello Agregator!</h1>");
        
        out.println("<form action=\"SimpleGui\" method=POST>");
        out.println("<input type=text name=query>");
        out.println("<input type=submit value=\"Go!\">");
        out.println("</form>");
        if (query == null || query == "") {
            out.println("Please type your query..<br>");
        } else {
        	out.println("Results for your query: \"" + query +"\":<br><br>");
        	//query - сейчас тут хранится строка которую ввел пользователь.
        	//её нужно отдать поисковику и тут же получить от него результат
        	//чтобы его тут и отрисовать =)
        	out.println("<b>Это результаты которые нам отдал поисковик.</b>");
        }         
        out.println("</body>");
        out.println("</html>");
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        doGet(request, response);
    }
}
