import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;



@WebServlet("SchedulingServlet")
public class SchedulingServlet extends HttpServlet
{
	
	protected void doGet(XMLHttpRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		
		// connect to database
		String JdbcURL = "jdbc:mysql://303.itpwebdev.com:3306";
        String Username = "pluiz_sachi";
        String password = "csci201final";
        Connection con = null;
        try 
        {      	
        	con=DriverManager.getConnection(JdbcURL, Username, password);
        }
      	catch(Exception e) 
      	{
        	e.printStackTrace();
      	}

      	// get info from backend
      	// array of strings (class names) and boolean (sort by rmp score / distance)
      	boolean valforopt;

      	// we call the algorithm function, 
      	// public Schedule buildBestSchedule(Course[] courses, int schedulesDesired, boolean metric)

      	Schedule myClasses = buildBestSchedule(1, valforopt)

      	// get the Schedule
      	// save classes into database sessions table

      	// save Schedule classes as JSON (containing only classIDs)

    }

}
