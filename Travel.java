import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class Travel {

    final public static void printResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }
    public static String flight(){
        return "select f.flight_no,A.city as Destenation, TIMESTAMPDIFF(hour,concat(dep_date,\" \",dep_time),concat(arr_date,\" \" ,arr_time)) as \"Flying TIME\" \n" +
                "from flights F inner join Airports A on F.arr_loc= A.a_id\n" +
                "where f.dep_date = current_date();";
    }
    public static String airports(){
        return "select F.flight_no,F.arr_date,F.arr_time from flights F\n" +
                "inner join airports A on A.a_id=f.dep_loc\n" +
                "where F.dep_loc=1;";
    }
    public static void executeStoredProcedure(Connection con) {
        ResultSet rs;
        String call = "{call traveling(?)}";
        //traveling is the name of the sp and '?' is the number of parameters(in this case 1)
        try {
            PreparedStatement pstmt = con.prepareStatement(call);
            pstmt.setInt(1, inputPassID());
            rs = pstmt.executeQuery();
            System.out.println("city:");
            printResultSet(rs);
            try {

            }catch (Exception ex){ex.printStackTrace();
            }

        }catch (Exception ex){ex.printStackTrace();
        }

    }
    enum options {airports,flights};
    public static List<JSONObject> getFormattedResult(ResultSet rs) {
        List<JSONObject> resList = new ArrayList<JSONObject>();
        try {
            // get column names
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for(int i=1;i<=columnCnt;i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }

            while(rs.next()) { // convert each object to an human readable JSON object
                JSONObject obj = new JSONObject();
                for(int i=1;i<=columnCnt;i++) {
                    String key = columnNames.get(i - 1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }


    public static int inputPassID() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter passenger id: ");
        int passId = myObj.nextInt();  // Read user input
        return passId;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try{
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "peleg122")){
                Scanner myObj = new Scanner(System.in);
                System.out.print("enter option (flights or airports):");
                String req = myObj.nextLine();
               switch (req){
                   case "flights":
                       String query = flight();
                       System.out.println("Flights:");
                       PreparedStatement pstmt = con.prepareStatement(query);
                       ResultSet rs = pstmt.executeQuery();
                       printResultSet(rs);
                       break;
                   case "airports":
                       String query2 = airports();
                       System.out.println("airports:");
                       PreparedStatement pstmt2 = con.prepareStatement(query2);
                       ResultSet rs2 = pstmt2.executeQuery();
                       List<JSONObject> o = getFormattedResult(rs2);
                       for(int i=0;i<o.size();i++)
                           System.out.println(o.get(i).toString());
                       break;

               }
                executeStoredProcedure(con);
            }
        } catch (Exception ex){ex.printStackTrace();
        }
    }

}
