package org.fx.spgl.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.fx.spgl.controller.SpglController;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class SpglView {
    public void init() throws Exception {
        Parent root = FXRobotHelper.getStages().get(0).getScene().getRoot();
        Parent root2 = FXMLLoader.load(getClass().getResource("/spgl/fxml/index.fxml"));
        AnchorPane lookup = (AnchorPane) root.lookup("#bodys");
        AnchorPane node = (AnchorPane) root2.lookup("#spzl_index");
        node.setPrefWidth(lookup.getWidth());
        node.setPrefHeight(lookup.getHeight());
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }
}
