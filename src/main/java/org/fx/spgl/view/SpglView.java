package org.fx.spgl.view;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.fx.spgl.controller.SpglController;
import org.fx.spgl.model.SpglModel;
import org.fx.urils.ResponseResult;

import java.util.List;

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
        TableView tableView = (TableView) root2.lookup("#spgl_table");

        tableView.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory<SpglModel,String>("cname"));
//        ((TableColumn) root2.lookup("#spgl_table_cname")).setCellValueFactory(new PropertyValueFactory<>("cname"));
//            ((TableColumn) columns.get(1)).setCellValueFactory(new PropertyValueFactory<>("cname"));
//            ((TableColumn) columns.get(2)).setCellValueFactory(new PropertyValueFactory<>("jg"));
//            ((TableColumn) columns.get(3)).setCellValueFactory(new PropertyValueFactory<>("dw"));
//            ((TableColumn) columns.get(4)).setCellValueFactory(new PropertyValueFactory<>("gg"));
//            ((TableColumn) columns.get(5)).setCellValueFactory(new PropertyValueFactory<>("pp"));
//            ((TableColumn) columns.get(6)).setCellValueFactory(new PropertyValueFactory<>("sl"));
//            ((TableColumn) columns.get(7)).setCellValueFactory(new PropertyValueFactory<>("lm"));
//            ((TableColumn) columns.get(8)).setCellValueFactory(new PropertyValueFactory<>("sxj_string"));

            ObservableList<Object> arrayList = FXCollections.observableArrayList();
            ResponseResult<List<SpglModel>> result = new SpglController().index(0);
            if (result.isSuccess())
                arrayList.add(result.getData());
            tableView.setItems(arrayList);
        lookup.getChildren().clear();
        lookup.getChildren().addAll(root2);
    }
}
