package sevlet;

import cn.htsc.zyz.listner.session.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

public class Bservlet extends HttpServlet implements Serializable {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        HttpSession session = req.getSession();
        Person person1 = (Person)session.getAttribute("person");
        System.out.println(person1);
    }
}
