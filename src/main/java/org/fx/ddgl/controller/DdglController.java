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
import javafx.scene.input.MouseEvent;
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
    private int page;
    private int page2;

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
            getData(0, 1, 0);
        });
    }

    public void getData(int page, int page2, int ddlx) {
        this.ddlx = ddlx;
        this.page = page;
        this.page2 = page2;
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
        order_ddzt.setCellFactory((col) -> {
            TableCell<OrderModel, Integer> cell = new TableCell<OrderModel, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        String s = "";
                        if (item == 0)
                            s = "未完成";
                        if (item == 1)
                            s = "已完成";
                        if (item == 2)
                            s = "已关闭";
                        this.setText(s);
                    } else
                        this.setText(null);
                }
            };
            return cell;
        });
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
                            query(this.getTableView().getItems().get(this.getIndex()).getUuid());
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

    //未完成订单
    @FXML
    private void wwcdd(MouseEvent event) {
        getData(0, 1, 0);
    }

    //已完成订单
    @FXML
    private void ywcdd(MouseEvent event) {
        getData(0, 1, 1);
    }

    //已关闭订单
    @FXML
    private void ygbdd(MouseEvent event) {
        getData(0, 1, 2);
    }

    //    上一页
    @FXML
    private void pageUp(MouseEvent event) {
        getData(page + 1, page2 + 1, ddlx);
    }

    //    下一页
    @FXML
    private void pageNext(MouseEvent event) {
        page = page - 1 < 0 ? 0 : page;
        page2 = page2 - 1 < 1 ? 1 : page;
        getData(page, page2, ddlx);
    }

    //   实时提醒
    public void sstx() {
        if (ddlx == 0)
            getData(0, 1, 0);
    }

    //    查看具体商品
    private void query(String id) {
//        data2.clear();
//        try {
//            ResponseResult<String> result = orderInterface.getSp(pageNow, 15, id, StaticToken.getToken());
////        更新订单列表
//            if (result.isSuccess()) {
//                String s = result.getData().substring(result.getData().lastIndexOf("]") + 1, result.getData().length());
//                StaticToken.setToken(s);
//                try {
//                    String json = result.getData().substring(0, result.getData().lastIndexOf("]") + 1);
//                    List<OrderSpModel> beanList = objectMapper.readValue(json, new TypeReference<List<OrderSpModel>>() {
//                    });
//                    data2.addAll(beanList);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    mp3Util.mp3("/mp3/error.mp3");
//                    logger.info(new LoggerUtil(OrderController.class, "query", "数据转换错误").toString());
//                    alertUtil.f_alert_informationDialog("警告", "数据转换错误");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mp3Util.mp3("/mp3/error.mp3");
//                    logger.info(new LoggerUtil(OrderController.class, "query", "获取数据失败").toString());
//                    alertUtil.f_alert_informationDialog("警告", "获取数据失败");
//                }
//            } else {
//                StaticToken.setToken(result.getData());
////                mp3Util.mp3("/mp3/error.mp3");
//                logger.info(new LoggerUtil(OrderController.class, "query", result.getMessage()).toString());
//                alertUtil.f_alert_informationDialog("警告", result.getMessage());
//            }
//        } catch (RuntimeException e) {
////            mp3Util.mp3("/mp3/error.mp3");
//            logger.info(new LoggerUtil(OrderController.class, "query", "服务器链接失败，请从新登录").toString());
//            alertUtil.f_alert_informationDialog("警告", "服务器链接失败，请从新登录");
//        }
    }
}
