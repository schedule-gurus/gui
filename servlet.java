import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



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
        //conn=DriverManager.getConnection(JdbcURL, Username, password);
        

      	List<String> list = request.getParameter(list);
      	boolean valforopt = request.getParameter(metric);

      	// get info from backend
      	// array of strings (class names) and boolean (sort by rmp score / distance)

      	// convert list of class abbreviations "CSCI-201" to Course[]




      	

      	// we call the algorithm function, 
      	// public Schedule buildBestSchedule(Course[] courses, int schedulesDesired, boolean metric)
		// get the Schedule
      	Schedule myClasses = buildBestSchedule(1, valforopt)

      	// save classes into database sections table
      	for(int i = 0; i < myClasses.sections.length(); i++)
      	{
			Section currSection = myClasses.sections.get(i);

			// get instructor data ready
      		String prof_first = currSection.instructors[0].first_name;
      		String prof_last = currSection.instructors[0].last_name;
      		double prof_rmp = currSection.instructors[0].rmp;

      		// insert into instructors table
      		String sql2 = "INSERT into pluiz_usc_schedule_db.instructors (first, last, rmp) VALUES (?, ?, ?) ";
      		try (conn=DriverManager.getConnection(JdbcURL, Username, password), PreparedStatement ps = conn.prepareStatement(sql2);)
      		{
      			ps.setString(1, prof_first);
      			ps.setString(2, prof_last);
      			ps.setDouble(3, prof_rmp);
      			ps.executeQuery();
      		}
      		catch(SQLException e)
      		{

      		}

      		// get instructorID
      		int prof_id = 0;
      		String sql3 = "GET i.ID FROM pluiz_usc_schedule_db.instructors as i WHERE i.first = ? AND i.last = ? ";
      		try (conn=DriverManager.getConnection(JdbcURL, Username, password), PreparedStatement ps = conn.prepareStatement(sql3);)
      		{
      			ps.setString(1, prof_first);
      			ps.setString(2, prof_last);
      			ResultSet rs = ps.executeQuery();
      			prof_id = rs.getInt("ID");
      		}

      		// add section info into sections table
      		String sql = "INSERT into pluiz_usc_schedule_db.sections ";
      		sql += "(ID, session, title, type, startTime, endTime, instructor, location, dotw, abrv) " 
      		sql += "VALUES (?,?,?,?,?,?,?,?,?,?) ";
      		try (conn=DriverManager.getConnection(JdbcURL, Username, password), PreparedStatement ps = conn.prepareStatement(sql);)
      		{
      			ps.setInt(1, currSection.id);
      			ps.setInt(2, currSection.session);
      			ps.setString(3, currSection.title);
      			ps.setString(4, currSection.type);
      			ps.setInt(5, currSection.start_time);
      			ps.setInt(6, currSection.end_time);
      			ps.setInt(7, prof_id);
      			ps.setString(8, currSection.location);
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
