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
      	Course[] courses = new Course[list.size()];
      	for(int i = 0; i < list.size(); i++)
      	{
      		try
      		{
      			courses[i] = SOC_API.get_course(list.get(i), 20203);
      		}
      		catch(Exception e)
      		{
      			System.out.println("Exception when converting String abbreviations to Courses ");
      		}
      	}
      	


      	

      	// we call the algorithm function, 
      	// public Schedule buildBestSchedule(Course[] courses, int schedulesDesired, boolean metric)
		// get the Schedule
      	Schedule myClasses = buildBestSchedule(courses, 1, valforopt)

      	// save classes into database sections table
      	for(int i = 0; i < myClasses.sections.size(); i++)
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
      			System.out.println("Exception when inserting into instructors table");
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
      		catch(SQLException e)
      		{
      			System.out.println("Exception when getting instructorID");
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
				System.out.println("Exception when adding section info into Sections table of database");
      		}		
      	}

      	// get classIDs
      	// just loop through myClasses and get each entry's .id Integer


      	// save classIDs as JSON, then send back out. done below:
	
	Integer[] test = new Integer[5];
	    for(int i = 0; i < 5; i++)
	    {
	    	test[i] = i;
	    }
	    //test[0] = 1;
	    //creates JSON object that will be put into the JSON array
	    JSONObject jo = new JSONObject();
    	
    	//populates JSON object with course IDs
    	for(int i = 0; i < test.length; i++)
    	{
    		//jo.put("courseID", test[i]);
    		jo.append("courseID", test[i]);
    	}
    	//creates JSON array that will be populated with JSON object that represents a user's class IDs
    	JSONArray ja = new JSONArray();
    	//populates JSON object with user's packaged class IDs
    	ja.put(jo);
    	
    	//prints out json object within the json array we're passing pilar
    	for (int i = 0; i < ja.length(); i++)
    	{
    	    JSONObject pet = ja.getJSONObject(i);
    	    System.out.println(pet);
    	}
	

    }

}
