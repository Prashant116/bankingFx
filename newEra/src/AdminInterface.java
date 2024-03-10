import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.Scene;


public class AdminInterface{
    private Stage window;

    public AdminInterface(Stage window){
        this.window = window;
    }

    public void showAdminInterface(){
        BorderPane mainLayout = new BorderPane();

        Label welcomeLabel = new Label("Welcome Admin");
        
        mainLayout.setTop(welcomeLabel);
        
        //addComponents to mainLayout
        // mainLayout.getChildren().add(welcomeLabel);

        Scene scene = new Scene(mainLayout);

        window.setScene(scene);
        window.show();

    }
}