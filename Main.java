package com.giuseppe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("viewAddress/viewAddress.fxml"));
        primaryStage.setTitle("Address Database");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception
    {
        if(!Datasource.getDatasource().open())
        {
            System.out.println("Cannot open the database.");
            return;
        }

        if(!Datasource.getDatasource().create())
        {
            System.out.println("Cannot create the table.");
            return;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop()
    {
        ManageClients.getManageClients().saveClient();
        Datasource.getDatasource().close();
    }
}
