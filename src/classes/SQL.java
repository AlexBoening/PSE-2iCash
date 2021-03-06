package classes;
import java.sql.*;
import java.util.ArrayList;

public class SQL {

	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static void getConnection() throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/icash", "root", "");
			stmt = conn.createStatement();
		}
		catch (ClassNotFoundException err) {
			System.out.println("DB-Driver not found!");
			System.out.println(err);
		}
		/*catch (SQLException err) {
			System.out.println("Connect not possible");
			System.out.println(err);
		}*/
	}
	
	public static String[][] select(String[] column, String table, String[] condition, String connector) 
			                 throws SQLException {
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[][] result;
		String[] line = new String[column.length];
		
		String sql = "select ";
		for (int i=0; i<column.length; i++) {
			if (i>0)
				sql += ", ";
			sql += column[i];
		}
		sql += " from " + table + " ";
		for (int i=0; i<condition.length; i++) 
			if (i==0)
				sql += "where " + condition[i] + " ";
			else
				sql += connector + " " + condition[i] + " ";
		//try {
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    int j=0;
		    while (rs.next()) {
		    	j++;
		    	line = new String[column.length];
		    	for (int i=0; i<column.length; i++)
		    		line[i] = rs.getString(i+1);
		    	list.add(line);
		    }
		    result = new String[j][column.length];
		    result = list.toArray(result);
		/*}
		catch (SQLException err) {
			System.out.println("Error when selecting from table " + table);
			result = new String[0][0];
		}*/
		return result;
	}
	
	public static void insert(String[] value, String table) throws SQLException {
		
		String sql = "insert into " + table + " values ('";
		for (int i=0; i<value.length; i++) {
			if (i>0)
				sql += "', '";
			sql += value[i];
		}
		sql += "')";
		//try {
		    int lines = stmt.executeUpdate(sql);
		    if (lines < 1)
		    	throw new SQLException();
		/*}
		catch (SQLException err) {
			System.out.println("Error when inserting into table " + table);
		}*/
	}
	
	/*public static void update(String column, String value, String table, String[] condition, String connector) 
			           throws SQLException {
		
		String sql = "update " + table + " set " + column + " = '" + value + "' ";
		for (int i=0 ; i<condition.length; i++) {
			if (i==0) 
				sql += "where " + condition[i] + " ";
			else
				sql += connector + " " + condition[i] + " ";
		}
		//try {
		    int lines = stmt.executeUpdate(sql);
		    if (lines < 1)
		    	throw new SQLException();
		/*}
		catch (SQLException err) {
			System.out.println("Error when updating table " + table);
		}
	}*/
	
	public static void update(String[] column, String[] value, String table, String[] condition, String connector)
	                   throws SQLException {
		String sql = "update " + table + " set " + column[0] + " = '" + value[0] + "' ";
		for (int i=1; i<value.length; i++)
			sql += ", " + column[i] + " = '" + value[i] + "' ";
		for (int i=0; i<condition.length; i++)
		    if (i==0)
		    	sql+= "where " + condition[i] + " ";
		    else
		    	sql += connector + " " + condition[i] + " ";
		int lines = stmt.executeUpdate(sql);
		if (lines < 1)
	    	throw new SQLException();
	}
	
	public static int getID(String column, String table) throws SQLException {
		
		String sql = "select max( " + column + " ) from " + table;
		
		//try {
		    rs = stmt.executeQuery(sql);
		    rs.beforeFirst();
		    if (rs.next()) 
		    	return rs.getInt(1) + 1;
		    else
		    	return 1;
		/*}
		catch (SQLException err) {
			System.out.println("Error when selecting from table " + table);
			return 0;
		}*/
	}
}
