import javafx.application.Application;
// import java.util.Arrays;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class tempCode extends Application{

    //start method
    @Override
    public void start(Stage window){
        window.setTitle("Banking");
        Label resultLabel = new Label();
        Button depositBtn = new Button("Deposit");
        Button clearBtn = new Button("Clear");
        
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(depositBtn, clearBtn, resultLabel);
        
        //set object class
        depositBtn.getStyleClass().add("testBtn");

        TextArea editorArea = new TextArea();
        
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(buttons);
        mainLayout.setCenter(editorArea);

        //create scene
        Scene scene = new Scene(mainLayout);

        Button backBtn = new Button("Back");

        HBox labelLayout = new HBox();
        labelLayout.getChildren().addAll(new Label("first"), new Label("second"), backBtn);

        Scene labelScene  = new Scene(labelLayout);

        window.setScene(scene);
        window.show();

        backBtn.setOnAction((event) ->{
            window.setScene(scene);
        });

        depositBtn.setOnAction((event) ->{
            resultLabel.setText(editorArea.getText());
        });

        clearBtn.setOnAction((event) ->{
            // editorArea.clear();
            // resultLabel.setText("");
            window.setScene(labelScene);
        });

        // editorArea.textProperty().addListener(new ChangeListener<String>() {
        // @Override
        // public void changed(ObservableValue<? extends String> change,
        //         String oldValue, String newValue) {

        //     // System.out.println(oldValue + " -> " + newValue);
        //     String[] parts = newValue.split(" ");
        //     String longestString = null;
        //     for(String word: parts){
        //         if(word.length() > longestString.length()){
        //             longestString = word;
        //         }
        //     }
        //     resultLabel.setText("Longest word: " + longestString);
        //     }
        // });
        
        editorArea.textProperty().addListener((change, oldValue , newValue) -> {
            // System.out.println("Old value->" + oldValue);
            
            String[] parts = newValue.split(" ");
            // String longest = Arrays.stream(parts).sorted((s1, s2) -> s2.length() - s1.length()).findFirst().get();
            
            String longest = "";
            for(String word: parts){
                        if(word.length() > longest.length()){
                            longest = word;
                        }
                    }

            resultLabel.setText("Longest word: " + longest);
        });

        //apply CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }
    public static void main(String[] args) {
        launch(newEra.class);
    }
}
