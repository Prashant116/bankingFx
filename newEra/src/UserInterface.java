import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.Scene;

public class UserInterface{
    private Stage window;
    private BankAccount tempAccount ;
    private static Label errorMsg = new Label(" ");
    // private String userAccessName;

    public UserInterface(Stage window){
        this.window = window;
    }

    public void showUserInterface(String userAccessName){
        //layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        //top user Details:
        VBox userDetails = new VBox();
        userDetails.setPadding(new Insets(0,0,20,0));
        Label username = new Label("Name: " + userAccessName);

        //intialize tempAccount
        // DbConnection db = new DbConnection();

        try{
            tempAccount = DbConnection.readAccount(userAccessName);
        }
        catch(DbConnection.UserDoesntExistException e){
            errorMsg.setText(e.getMessage());
        }
        Label balance = new Label("Balance: " + tempAccount.getBalance());

        
        //top interface
        HBox topButtons = new HBox();
        topButtons.setSpacing(10);
        topButtons.setPadding(new Insets(20,0,20,0));

        //buttons
        Button depositTopButton = new Button("Deposit");
        Button withdrawTopButton = new Button("Withdraw");
        Button transferFundTopButton = new Button("Transfer Fund");
        Button logoutTopButton = new Button("Logout");
        
        
        topButtons.getChildren().addAll(depositTopButton, withdrawTopButton, transferFundTopButton, logoutTopButton);

        //add HBox in VBox
        errorMsg.getStyleClass().add("error-message");
        userDetails.getChildren().addAll(username, balance, topButtons,errorMsg);
        
        layout.setTop(userDetails);

        //deposit UI
        VBox depositUI = new VBox();
        depositUI.setSpacing(10);
        
        //add components to depositUI
        Label depositLabel = new Label("Deposit Amount");
        TextField depositAmount = new TextField();
        // depositAmount.setMaxWidth(150);
        Button depositBtn = new Button("Deposit");
        depositBtn.getStyleClass().add("green-button");
        
        depositUI.getChildren().addAll(depositLabel, depositAmount, depositBtn);

        //withdraw UI
        VBox withdrawUI = new VBox();
        withdrawUI.setSpacing(10);
        
        //add components to depositUI
        Label withdrawLabel = new Label("Withdraw Amount");
        TextField withdrawAmount = new TextField();
        // withdrawAmount.setMaxWidth(150);
        Button withdrawBtn = new Button("withdraw");
        withdrawBtn.getStyleClass().add("green-button");
        
        withdrawUI.getChildren().addAll(withdrawLabel, withdrawAmount, withdrawBtn);


        //transferFund UI
        VBox transferFundUI = new VBox();
        transferFundUI.setSpacing(10);
        
        //add components to depositUI
        Label transferFundNameLabel = new Label("Fund transfer to(Name): ");
        TextField transferFundName = new TextField();
        // transferFundName.setMaxWidth(150);
        Label transferFundLabel = new Label("Transfer Fund Amount");
        TextField transferFundAmount = new TextField();
        // transferFundAmount.setMaxWidth(150);
        Button transferFundBtn = new Button("Transfer Fund");
        transferFundBtn.getStyleClass().add("green-button");
        
        transferFundUI.getChildren().addAll(transferFundNameLabel,transferFundName,transferFundLabel, transferFundAmount, transferFundBtn);
        
        //set center layout
        layout.setCenter(depositUI);
        
        //create scene
        Scene scene = new Scene(layout, 350, 350);

        //apply CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        //set scene
        window.setScene(scene);
        
        window.show();
        
        //functions of each button
        depositTopButton.setOnAction((event)->{
            layout.setCenter(depositUI);
        });

        withdrawTopButton.setOnAction((event)->{
            layout.setCenter(withdrawUI);
        });

        transferFundTopButton.setOnAction((event)->{
            layout.setCenter(transferFundUI);
        });

        logoutTopButton.setOnAction((event)->{
            LoginScreen loginScreen = new LoginScreen(window);
            loginScreen.showLogin();
        });

        depositBtn.setOnAction((event) ->{
            try{
                tempAccount.depositMoney(Integer.parseInt(depositAmount.getText()));
                DbConnection.updateBalance(tempAccount);
                depositAmount.clear();
                balance.setText("Balance: "+tempAccount.getBalance());

                }
            catch(Exception e){
                System.out.println("Error");
            }
        });

        withdrawBtn.setOnAction((event) ->{
            try{
                if(!withdrawAmount.getText().equals("")){

                    tempAccount.withdrawMoney(Integer.valueOf(withdrawAmount.getText()));
                    DbConnection.updateBalance(tempAccount);
                    withdrawAmount.clear();
                    balance.setText("Balance: "+tempAccount.getBalance());
                    }
                    else{
                        errorMsg.setText("Enter Amount");
                    }
                }
            catch(BankAccount.ExceedAmountException e){
                System.out.println("Error " + e);
                errorMsg.setText(e.getMessage());
            }

        });
        
        transferFundBtn.setOnAction((event) ->{
            try{
                if(!transferFundAmount.getText().isEmpty()){                    
                    BankAccount transferAccount = DbConnection.readAccount(transferFundName.getText());
                    // System.out.println("Code here");
                    if(transferAccount != null){
                        tempAccount.withdrawMoney(Integer.valueOf(transferFundAmount.getText()));
                        DbConnection.updateBalance(tempAccount);
                        transferAccount.depositMoney(Integer.valueOf(transferFundAmount.getText()));
                        DbConnection.updateBalance(transferAccount);
                        balance.setText("Balance: "+tempAccount.getBalance());
                        transferFundAmount.clear();
                        transferFundName.clear();
                    }
                }
                else{
                    errorMsg.setText("Enter Amount");
                }
            }
            catch(RuntimeException userException){
                System.out.println("code into exception");
                errorMsg.setText(userException.getMessage());
            }
            catch(BankAccount.ExceedAmountException e){
                errorMsg.setText(e.getMessage());
            }
            catch(Exception e){
                errorMsg.setText(e.getMessage());

            }

        });

        

    }

    // public VBox depositScreen(){
    //     VBox layout = new VBox();
    //     layout.getChildren().addAll(new Label)
    // };
    // public void withdrawScreen();
    // public void transferFundScreen();
}