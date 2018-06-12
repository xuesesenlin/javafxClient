package org.fx.ddgl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.fx.ddgl.model.OrderModel;
import org.fx.ddgl.model.OrderSpModel;
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
    private TableView order_table2;
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
    @FXML
    private Label pageNow;
    @FXML
    private TableColumn sp_xh;
    @FXML
    private TableColumn sp_name;
    @FXML
    private TableColumn sp_pp;
    @FXML
    private TableColumn sp_sl;
    @FXML
    private TableColumn sp_dj;
    @FXML
    private TableColumn sp_zj;
    @FXML
    private TableColumn sp_zt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData(0, 1, 0);
    }

    public void getData(int page, int page2, int ddlx) {
        this.ddlx = ddlx;
        Platform.runLater(() -> {
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
                                update(this.getTableView().getItems().get(this.getIndex()).getUuid());
                            });
                            button2.getStyleClass().add("button");
                            button2.getStyleClass().add("button2");
                            Button button3 = new Button("关闭");
                            button3.setOnMouseClicked(o -> {
                                del(this.getTableView().getItems().get(this.getIndex()).getUuid());
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
                        pageNow.setText(page2 + "");
                        order_table.setItems(list);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                pageNow.setText(page + "");
                order_table.setItems(list);
            }
        });
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
        String text = pageNow.getText();
        int page2 = Integer.parseInt(text);
        int page = page2 - 1;
        page = page - 1 < 0 ? 0 : page;
        page2 = page2 - 1 < 1 ? 1 : page;
        getData(page, page2, ddlx);
    }

    //    下一页
    @FXML
    private void pageNext(MouseEvent event) {
        String text = pageNow.getText();
        int page2 = Integer.parseInt(text);
        getData(page2, page2 + 1, ddlx);
    }

    //   实时提醒
    public void sstx() {
        if (ddlx == 0)
            getData(0, 1, 0);
    }

    //    查看具体商品
    private void query(String id) {
        order_table2.getItems().clear();
        sp_xh.setCellFactory((col) -> {
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
        sp_name.setCellValueFactory(new PropertyValueFactory("spid"));
        sp_pp.setCellValueFactory(new PropertyValueFactory("pp"));
        sp_sl.setCellValueFactory(new PropertyValueFactory("sl"));
        sp_dj.setCellValueFactory(new PropertyValueFactory("spdj"));
        sp_zj.setCellValueFactory(new PropertyValueFactory("spzj"));
        sp_zt.setCellValueFactory(new PropertyValueFactory("zt"));

        Platform.runLater(() -> {
            try {
                ResponseResult<String> result = orderInterface.getSp(0, 1000, id, StaticToken.getToken());
//        更新订单列表
                if (result.isSuccess()) {
                    String s = result.getData().substring(result.getData().lastIndexOf("]") + 1, result.getData().length());
                    StaticToken.setToken(s);
                    try {
                        String json = result.getData().substring(0, result.getData().lastIndexOf("]") + 1);
                        List<OrderSpModel> beanList = objectMapper.readValue(json, new TypeReference<List<OrderSpModel>>() {
                        });
                        order_table2.getItems().addAll(beanList);
                    } catch (IOException e) {
                        e.printStackTrace();
                        alert.f_alert_informationDialog("警告", "数据转换错误");
                    } catch (Exception e) {
                        e.printStackTrace();
                        alert.f_alert_informationDialog("警告", "获取数据失败");
                    }
                } else {
                    StaticToken.setToken(result.getData());
                    alert.f_alert_informationDialog("警告", result.getMessage());
                }
            } catch (RuntimeException e) {
                alert.f_alert_informationDialog("警告", "服务器链接失败，请从新登录");
            }
        });
    }

    //关闭订单
    private void del(String id) {
        ResponseResult<String> result = orderInterface.update(id, 2, StaticToken.getToken());
        if (result.isSuccess()) {
            String s = result.getData().substring(result.getData().lastIndexOf("}") + 1, result.getData().length());
            StaticToken.setToken(s);
            String text = pageNow.getText();
            int page2 = Integer.parseInt(text);
            getData(page2 - 1, page2, ddlx);
            alert.f_alert_informationDialog("提示", "成功");
        } else {
            StaticToken.setToken(result.getData());
            alert.f_alert_informationDialog("警告", result.getMessage());
        }
    }

    private void update(String uuid) {
        try {
            ResponseResult<String> result = orderInterface.update(uuid, 1, StaticToken.getToken());
            if (result.isSuccess()) {
                String s = result.getData().substring(result.getData().lastIndexOf("}") + 1, result.getData().length());
                StaticToken.setToken(s);
                String text = pageNow.getText();
                int page2 = Integer.parseInt(text);
                getData(page2 - 1, page2, ddlx);
                alert.f_alert_informationDialog("提示", "成功");
            } else {
                StaticToken.setToken(result.getData());
                alert.f_alert_informationDialog("警告", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert.f_alert_informationDialog("警告", "远程服务链接失败");
        }
    }
}
