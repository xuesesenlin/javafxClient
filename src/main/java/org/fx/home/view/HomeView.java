package org.fx.home.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class HomeView {

    public void init() throws Exception {
        FXRobotHelper.getStages().get(0).close();
        Stage stage = new Stage();
        //        窗口最大化
//        primaryStage.setMaximized(true);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.initStyle(TRANSPARENT);

        Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/index.fxml"));
        Scene scene = new Scene(root);
//        加载外部css
        scene.getStylesheets().add(getClass().getResource("/home/css/index.css").toExternalForm());
        stage.setScene(scene);
//        显示
        FXRobotHelper.getStages().add(stage);
        stage.show();
    }
}
