package org.fx.home.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fx.home.controller.HomeController;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class HomeView {

    public void init() throws Exception {
        Stage stage = FXRobotHelper.getStages().get(0);
        //        窗口最大化
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/index.fxml"));
//        加载外部css
        Scene scene = stage.getScene();
        scene.setRoot(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/home/css/index.css").toExternalForm());
        stage.setScene(scene);
//        显示
        stage.show();
//        最新订单提醒
        new HomeController().sssx();
    }
}
