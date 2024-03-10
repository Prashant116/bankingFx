import java.sql.Connection;
import java.sql.ResultSet;
// import com.mysql.cj.jdbc.Driver;
// import com.mysql.cj.protocol.Resultset;

import java.sql.PreparedStatement;
import java.sql.DriverManager;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String USER = "root";
    private static final String PASS = "";
    // private static Connection conn;

    private String selectedTable = null;
    private String accessUser = null;

    public DbConnection(String dbTable){
        this.selectedTable = dbTable;
    }

    public DbConnection(){
        
    }

    //UserDoesntExistException
    public static class UserDoesntExistException extends Exception{
        public UserDoesntExistException(String message){
            super(message);
        }
    }

    //function to check if the user exists
    public int checkAccess(String inputUsername, String inputPassword){
        int correctInput = 0;
        try{
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            if(conn != null){
                String query = null;
                if(selectedTable.equals("users")){
                    query = "SELECT * FROM users";
                }
                else if(selectedTable.equals("admin")){
                    query = "SELECT * FROM admin";
                }
                PreparedStatement statement = conn.prepareStatement(query);

                ResultSet result = statement.executeQuery();

                while(result.next()){
                    String username = result.getString("username");
                    String password = result.getString("password");

                    if(inputUsername.equals(username) && inputPassword.equals(password)){
                        correctInput = 1;
                        accessUser = username;
                        break;
                    }
                }
                result.close();
                statement.close();
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println("Error while connecting database " + e);
        }
        return correctInput;
    }

    public String getAccessUsername(){
        return accessUser;
    }

    //function to read BankAccount
    public static BankAccount readAccount(String username) throws UserDoesntExistException{
        BankAccount returnAccount = null;
        try{
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            if(conn != null)
            {
                String readQuery = "SELECT * FROM accounts where name = ?";

                PreparedStatement statement = conn.prepareStatement(readQuery);
                statement.setString(1, username);

                ResultSet selectResult = statement.executeQuery();

                if(selectResult.next()){
                    int acc_no = selectResult.getInt("acc_no");
                    String accName = selectResult.getString("name");
                    int accAge = selectResult.getInt("age");
                    String accAddress = selectResult.getString("address");
                    int accBalance = selectResult.getInt("balance");
        
                    returnAccount = new BankAccount(accName, accAge, accAddress, acc_no);
                    returnAccount.setBalance(accBalance);
                    statement.close();
                    selectResult.close();
                }
                else{
                    // System.out.println("No user found!");
                    throw new RuntimeException(new UserDoesntExistException("Account Not Found"));
                }
            }

            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Handled here");
        }
        return returnAccount;
    }

    public static void updateBalance(BankAccount tempAccount){

        try{
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            if(conn != null)
            {
                String updateQuery = "UPDATE accounts SET balance = (?) WHERE name = (?)";
        
                PreparedStatement statement = conn.prepareStatement(updateQuery);
        
                statement.setString(1, "" + tempAccount.getBalance());
                statement.setString(2, tempAccount.getName());
        
                statement.executeUpdate();
        
                statement.close();
            }

            conn.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

}
