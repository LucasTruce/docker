import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
 
public class App {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full_2020_086300:3306/AJaczynski";
 
    static final String USER = "root";
    static final String PASS = "root";
 
    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE IF NOT EXISTS Students (ID int, NAME varchar(255), LASTNAME varchar(255), EMAIL varchar(255) );";
    private static final String SELECT_ALL_FROM_STUDENTS = "SELECT ID, NAME, LASTNAME, EMAIL FROM Students";
 
    public static void main(String[] args) {
 
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Connecting to database...");
            stmt.executeUpdate(CREATE_TABLE_STUDENTS);
            String selectedOperation;
            do
            {
                System.out.println("1. Show DB data\n2. Insert student\n3. Edit student by ID\n4. Delete student by ID\nS. Press E to Exit");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(stmt);
                        break;
                    case "2" :
                        insertStudent(stmt);
                        break;
                    case "3" :
                        updateStudent(stmt);
                        break;
                    case "4" :
                        deleteStudentById(stmt);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("E"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    private static void deleteStudentById(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery(SELECT_ALL_FROM_STUDENTS);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Enter ID to delete");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Students WHERE ID= '"+id+"';";
        stmt.executeUpdate(deleteSql);
    }
 
    private static void updateStudent(Statement stmt) throws SQLException {
        String id;
        String name;
        String lastname;
        String sql;
        String email;
        ResultSet rss = stmt.executeQuery(SELECT_ALL_FROM_STUDENTS);
        printOutHeader();
 
        printOutResult(rss);
        rss.close();
        System.out.println("Enter ID to edit");
        id = in.nextLine();
 
        System.out.println("Name: ");
        name = in.nextLine();
 
        System.out.println("Last name: ");
        lastname = in.nextLine();
 
        System.out.println("Email:");
        email = in.nextLine();
 
        sql = " UPDATE Students SET NAME = '"+name+"' , LASTNAME = '"+lastname+"', EMAIL = '"+email+"' WHERE ID= '"+id+"';";
        stmt.executeUpdate(sql);
    }
 
    private static void insertStudent(Statement stmt) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();
 
        System.out.println("Name:");
        final String name = in.nextLine();
 
        System.out.println("Last name:");
        final String lastname = in.nextLine();
 
        System.out.println("Email:");
        final String email = in.nextLine();
 
        String sql = " INSERT INTO Students (ID, NAME, LASTNAME, EMAIL) VALUES ('"+id+"', '"+name+"', '"+lastname+"', '"+email+"')";
        stmt.executeUpdate(sql);
    }
 
    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_STUDENTS);
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }
 
    private static void printOutHeader() {
        System.out.println("ID    NAME    LASTNAME    EMAIL");
    }
 
    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String first;
        String last;
        String address;
        String city;
        while (rs.next()) {
            id = rs.getInt("ID");
            first = rs.getString("NAME");
            last = rs.getString("LASTNAME");
            address = rs.getString("EMAIL");
 
            System.out.println(id + "    " + first + "    " + last + "    " + address);
        }
    }
}
