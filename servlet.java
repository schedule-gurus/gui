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
        Connection conn = null;
        try 
        {      	
        	conn=DriverManager.getConnection(JdbcURL, Username, password);
        }
      	catch(Exception e) 
      	{
        	e.printStackTrace();
      	}

      	List<String> list = request.getParameter(list);
      	boolean valforopt = request.getParameter(metric);

      	// get info from backend
      	// array of strings (class names) and boolean (sort by rmp score / distance)
      	

      	// we call the algorithm function, 
      	// public Schedule buildBestSchedule(Course[] courses, int schedulesDesired, boolean metric)
		// get the Schedule
      	Schedule myClasses = buildBestSchedule(1, valforopt)

      	// save classes into database sections table
      	for(int i = 0; i < myClasses.sections.length(); i++)
      	{
      		Section currSection = myClasses.sections.get(i);
      		String sql2 = "GET "


      		String sql = "INSERT into pluiz_usc_schedule_db.sections ";
      		sql += "(ID, session, title, type, startTime, endTime, instructor, location, dotw, abrv) " 
      		sql += "VALUES (?,?,?,?,?,?,?,?,?,?) ";
      		try (conn, PreparedStatement ps = conn.prepareStatement(sql);)
      		{
      			ps.setInt(1, currSection.id);
      			ps.setInt(2, currSection.session);
      			ps.setString(3, currSection.title);
      			ps.setString(4, currSection.type);
      			ps.setString(5, currSection.);
      			ps.setString(6, currSection.);
      			ps.setString(7, currSection.);
      			ps.setString(8, currSection.);
      			ps.setString(9, currSection.day);
      			ps.setString(10, currSection.course_name);

      		}	
      		catch(SQLException sqle)
      		{

      		}		
      	}

      	// save classIDs as JSON, then send back out

    }

}
