import javafx.application.Application;
import javafx.stage.Stage;


public class newEra extends Application{

    public void start(Stage window){
        LoginScreen loginScreen = new LoginScreen(window);
        loginScreen.showLogin();
    }
    public static void main(String[] args) {
        launch();
    }
}