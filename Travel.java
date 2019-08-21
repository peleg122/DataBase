import java.sql.*;
import java.util.Scanner;


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
                       printResultSet(rs2);
                       break;

               }
                executeStoredProcedure(con);
            }
        } catch (Exception ex){ex.printStackTrace();
        }
    }

}