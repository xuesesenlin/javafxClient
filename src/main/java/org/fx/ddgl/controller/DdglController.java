package org.fx.ddgl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.fx.ddgl.model.OrderModel;
import org.fx.feign.OrderInterface;
import org.fx.feign.SpglInterface;
import org.fx.urils.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DdglController implements Initializable {

    private static Logger logger = Logger.getLogger(DdglController.class.toString());
    private OrderInterface orderInterface = FeignUtil.feign()
            .target(OrderInterface.class, new FeignRequest().URL());
    private SpglInterface spglInterface = FeignUtil.feign()
            .target(SpglInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private AlertUtil alert = new AlertUtil();
    private int ddlx;

    @FXML
    private TableView order_table;
    @FXML
    private TableColumn order_uuid;
    @FXML
    private TableColumn order_xh;
    @FXML
    private TableColumn order_dddh;
    @FXML
    private TableColumn order_khmc;
    @FXML
    private TableColumn order_dh;
    @FXML
    private TableColumn order_dz;
    @FXML
    private TableColumn order_ddzt;
    @FXML
    private TableColumn order_cz;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            getData(0, 1);
        });
    }

    public void getData(int page, int page2) {
        ObservableList<OrderModel> list = FXCollections.observableArrayList();
//        映射
        order_uuid.setCellValueFactory(new PropertyValueFactory("uuid"));
        order_xh.setCellFactory((col) -> {
            TableCell<OrderModel, String> cell = new TableCell<OrderModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex() + 1;
                        this.setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });
        order_dddh.setCellValueFactory(new PropertyValueFactory("ddbh"));
        order_khmc.setCellValueFactory(new PropertyValueFactory("account"));
        order_dh.setCellValueFactory(new PropertyValueFactory("phone"));
        order_dz.setCellValueFactory(new PropertyValueFactory("address"));
        order_ddzt.setCellValueFactory(new PropertyValueFactory("type"));
        order_cz.setCellFactory((col) -> {
            TableCell<OrderModel, String> cell = new TableCell<OrderModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button button = new Button("查看");
                        button.setOnMouseClicked(o -> {
//                            delete(o, this.getTableView().getItems().get(this.getIndex()).getUuid());
                        });
                        button.getStyleClass().add("button");
                        button.getStyleClass().add("button2");
                        Button button2 = new Button("完成");
                        button2.setOnMouseClicked(o -> {
//                            update(o, this.getTableView().getItems().get(this.getIndex()).getUuid());
                        });
                        button2.getStyleClass().add("button");
                        button2.getStyleClass().add("button2");
                        Button button3 = new Button("关闭");
                        button3.setOnMouseClicked(o -> {
//                            update(o, this.getTableView().getItems().get(this.getIndex()).getUuid());
                        });
                        button3.getStyleClass().add("button");
                        button3.getStyleClass().add("button2");
                        HBox hBox = new HBox();
                        hBox.setSpacing(5);
                        hBox.getChildren().addAll(button, button2, button3);
                        this.setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        ResponseResult<String> result = orderInterface.page(page, 15, ddlx, StaticToken.getToken());
        if (result.isSuccess()) {
            String json = result.getData().substring(0, result.getData().lastIndexOf("]") + 1);
            try {
                List<OrderModel> beanList = objectMapper.readValue(json, new TypeReference<List<OrderModel>>() {
                });
                String s = result.getData().substring(result.getData().lastIndexOf("]") + 1, result.getData().length());
                StaticToken.setToken(s);
                list.addAll(beanList);
                Platform.runLater(() -> {
//                    pageNow.setText(page2 + "");
                    order_table.setItems(list);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
//                pageNow.setText(page + "");
            order_table.setItems(list);
        }
    }
//

//
//    //    上一页
//    @FXML
//    private void pageUp(MouseEvent event) {
//        String s = pageNow.getText();
//        int i = Integer.parseInt(s);
//        i = i - 1;
//        if (i < 1)
//            i = 1;
//        getData(i - 1, i);
//    }
//
//    //    下一页
//    @FXML
//    private void pageNext(MouseEvent event) {
//        String s = pageNow.getText();
//        int i = Integer.parseInt(s);
//        getData(i + 1, i + 1);
//    }
//
//    //    新增
//    @FXML
//    private void add(MouseEvent event) {
//        try {
//            new SpglView().add();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    删除
//    private void delete(MouseEvent event, String uuid) {
//        boolean b = alert.f_alert_confirmDialog("警告", "是否确定删除?");
//        if (b) {
//            ResponseResult<String> result = spglInterface.del(uuid, StaticToken.getToken());
//            if (result.isSuccess()) {
//                String s = result.getData().substring(result.getData().lastIndexOf("}") + 1, result.getData().length());
//                StaticToken.setToken(s);
//                String s1 = pageNow.getText();
//                int i2 = Integer.parseInt(s1);
//                getData(i2 - 1, i2);
//            } else
//                StaticToken.setToken(result.getData());
//
//        }
//    }
//
//    //    修改
//    private void update(MouseEvent event, String uuid) {
//        try {
//            new SpglView().update(uuid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
