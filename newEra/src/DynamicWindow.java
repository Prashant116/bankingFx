import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class DynamicWindow extends Application{
    public void start(Stage window){

        //main layout
        BorderPane mainLayout = new BorderPane();

        //create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20,20,20,20));
        menu.setSpacing(10);

        //buttons for menu
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        menu.getChildren().addAll(depositBtn, withdrawBtn);

        mainLayout.setTop(menu);

        //create sublayout
        StackPane firstLayout = createLayout("Deposited !");
        StackPane secondLayout = createLayout("Withdraw successfull.");

        //set subviews to button
        depositBtn.setOnAction((event) -> mainLayout.setCenter(firstLayout));
        withdrawBtn.setOnAction((event) -> mainLayout.setCenter(secondLayout));

        //set initial view
        mainLayout.setCenter(firstLayout);

        //create scene
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();

    }

    private StackPane createLayout(String text){
        StackPane layout = new StackPane();
        layout.setPrefSize(500,300);
        layout.getChildren().add(new Label(text));
        return layout;
    }


    public static void main(String[] args) {
        launch();
    }
}