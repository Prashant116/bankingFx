// import bankingService.BankAccount;
import javafx.collections.FXCollections;
// import java.util.Arrays;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.geometry.Insets;
// import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScreen {

    private Stage window;
    private Label errorMsg = new Label();
    private String userAccessName;

    public LoginScreen(Stage window){
        this.window = window;
    }

    //showLogin method
    public void showLogin(){
        window.setTitle("Login");

        //items in main Layout
        Label loginLabel = new Label("Bank Login");
        loginLabel.getStyleClass().add("login");

        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("green-button");

        //main layout   
        BorderPane layout = new BorderPane();

        layout.setPadding(new Insets(20,20,20,20));

        layout.setTop(loginLabel);

        //sub layout
        VBox centerLayout = new VBox();
        centerLayout.setPadding(new Insets(30,0,30,0));
        centerLayout.setPrefSize(200,200);

        //items in sub layout
        Label username = new Label("Username");
        TextField userText = new TextField();
        userText.getStyleClass().add("login-field");
        // userText.setPrefHeight(20);

        Label password = new Label("Password");
        PasswordField passText = new PasswordField();
        // passText.setPrefHeight(20);
        passText.getStyleClass().add("login-field");

        //add combobox
        ComboBox<String> rolesBox = new ComboBox<>();
        rolesBox.setItems(FXCollections.observableArrayList("Admin", "User"));

        rolesBox.setOnKeyTyped((event) ->{
            for(String item: rolesBox.getItems()){
                if(item.startsWith(event.getCharacter().toUpperCase())){
                    rolesBox.getSelectionModel().select(item);
                }
            }
        });

        //add items to centerLayout
        centerLayout.getChildren().addAll(errorMsg, username, userText, password, passText, new Label("Account type"),
        rolesBox
        );
        
        loginBtn.setOnAction((event) -> {
            handleLogin(userText.getText(), passText.getText(), rolesBox.getValue());
        });

        //remove label text
        userText.textProperty().addListener((change, oldValue, newValue) ->{
            errorMsg.setText("");
        });

        passText.textProperty().addListener((change, oldValue, newValue) ->{
            errorMsg.setText("");
        });

        layout.setCenter(centerLayout);
        layout.setBottom(loginBtn);

        Scene scene  = new Scene(layout, 250, 320);

        window.setResizable(false);

        
        window.setScene(scene);
        window.show();
        
        
        //apply CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    private void handleLogin(String username, String password, String role){
        if(role.equals("User")){
                if(checkAccessDetails(username, password, "users")){
                    UserInterface userUI = new UserInterface(window);
                    userUI.showUserInterface(userAccessName);
                }
                else{
                    errorMsg.setText("Invalid credentials");
                    errorMsg.getStyleClass().add("error-message");
                }
            }
        else if (role.equals("Admin")){
            if(checkAccessDetails(username, password, "admin")){
                AdminInterface adminUI = new AdminInterface(window);
                adminUI.showAdminInterface();
            }
            else{
                errorMsg.setText("Invalid credentials");
            }
        }
        else{
            errorMsg.setText("Select a role");
        }
    }

    private boolean checkAccessDetails(String username, String password, String table){
        DbConnection dbData = new DbConnection(table);
        int result = dbData.checkAccess(username, password);
        if(result == 1){
            userAccessName = dbData.getAccessUsername();
            return true;
        }
        else return false;
        
    }
}