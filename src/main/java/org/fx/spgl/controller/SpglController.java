package org.fx.spgl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
import org.fx.spgl.view.SpglView;
import org.fx.urils.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SpglController implements Initializable {

    private static Logger logger = Logger.getLogger(SpglController.class.toString());
    private SpglInterface spglInterface = FeignUtil.feign()
            .target(SpglInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private AlertUtil alert = new AlertUtil();

    @FXML
    private Label pageNow;

    @FXML
    private TableView spgl_table;
    @FXML
    private TableColumn uuid;
    @FXML
    private TableColumn xh;
    @FXML
    private TableColumn cname;
    @FXML
    private TableColumn jg;
    @FXML
    private TableColumn dw;
    @FXML
    private TableColumn ge;
    @FXML
    private TableColumn pp;
    @FXML
    private TableColumn sl;
    @FXML
    private TableColumn lm;
    @FXML
    private TableColumn sxj_string;
    @FXML
    private TableColumn cz;
    @FXML
    private TableColumn zt;

    public void getData(int page, int page2) {
        ObservableList<SpglModel> list = FXCollections.observableArrayList();
//        映射
        uuid.setCellValueFactory(new PropertyValueFactory("uuid"));
        xh.setCellFactory((col) -> {
            TableCell<SpglModel, String> cell = new TableCell<SpglModel, String>() {
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
        cname.setCellValueFactory(new PropertyValueFactory("cname"));
        zt.setCellValueFactory(new PropertyValueFactory("zt"));
        zt.setCellFactory(new Callback<TableColumn<SpglModel, String>, TableCell<SpglModel, String>>() {
            public TableCell call(TableColumn param) {
                return new TableCell<SpglModel, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Image img = new Image(new FeignRequest().URL() + "/commodity/IoReadImage/" + item);
                            ImageView image = new ImageView(img);
                            image.setFitWidth(30);
                            image.setFitHeight(30);
                            this.setGraphic(image);
                        } else
                            this.setGraphic(null);
                    }
                };
            }
        });

        jg.setCellValueFactory(new PropertyValueFactory("jg"));
        dw.setCellValueFactory(new PropertyValueFactory("dw"));
        ge.setCellValueFactory(new PropertyValueFactory("ge"));
        pp.setCellValueFactory(new PropertyValueFactory("pp"));
        sl.setCellValueFactory(new PropertyValueFactory("sl"));
        lm.setCellValueFactory(new PropertyValueFactory("lm"));
        sxj_string.setCellValueFactory(new PropertyValueFactory("sxj_string"));
        cz.setCellFactory((col) -> {
            TableCell<SpglModel, String> cell = new TableCell<SpglModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button button = new Button("删除");
                        button.setOnMouseClicked(o -> {
                            delete(o, this.getTableView().getItems().get(this.getIndex()).getUuid());
                        });
                        button.getStyleClass().add("button");
                        button.getStyleClass().add("button2");
                        button.getStyleClass().add("button3");
                        Button button2 = new Button("修改");
                        button2.setOnMouseClicked(o -> {
                            update(o, this.getTableView().getItems().get(this.getIndex()));
                        });
                        button2.getStyleClass().add("button");
                        button2.getStyleClass().add("button2");
                        HBox hBox = new HBox();
                        hBox.setSpacing(5);
                        hBox.getChildren().addAll(button, button2);
                        this.setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        ResponseResult<String> result = spglInterface.page(page, 15, StaticToken.getToken());
        if (result.isSuccess()) {
            String json = result.getData().substring(0, result.getData().lastIndexOf("]") + 1);
            try {
                List<SpglModel> beanList = objectMapper.readValue(json, new TypeReference<List<SpglModel>>() {
                });
                String s = result.getData().substring(result.getData().lastIndexOf("]") + 1, result.getData().length());
                StaticToken.setToken(s);
                list.addAll(beanList);
                Platform.runLater(() -> {
                    pageNow.setText(page2 + "");
                    spgl_table.getItems().clear();
                    spgl_table.setItems(list);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> {
                pageNow.setText(page + "");
                spgl_table.getItems().clear();
                spgl_table.setItems(list);
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData(0, 1);
    }

    //    上一页
    @FXML
    private void pageUp(MouseEvent event) {
        String s = pageNow.getText();
        int i = Integer.parseInt(s);
        i = i - 1;
        if (i < 1)
            i = 1;
        getData(i - 1, i);
    }

    //    下一页
    @FXML
    private void pageNext(MouseEvent event) {
        String s = pageNow.getText();
        int i = Integer.parseInt(s);
        getData(i + 1, i + 1);
    }

    //    新增
    @FXML
    private void add(MouseEvent event) {
        try {
            new SpglView().add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    删除
    private void delete(MouseEvent event, String uuid) {
        boolean b = alert.f_alert_confirmDialog("警告", "是否确定删除?");
        if (b) {
            ResponseResult<String> result = spglInterface.del(uuid, StaticToken.getToken());
            if (result.isSuccess()) {
                String s = result.getData().substring(result.getData().lastIndexOf("}") + 1, result.getData().length());
                StaticToken.setToken(s);
                String s1 = pageNow.getText();
                int i2 = Integer.parseInt(s1);
                getData(i2 - 1, i2);
            } else
                StaticToken.setToken(result.getData());

        }
    }

    //    修改
    private void update(MouseEvent event, SpglModel model) {
        try {
            new SpglView().update(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    获取商品分类
    public String spfl_data() {
        try {
            ResponseResult<String> result = spglInterface.spfl();
            if (result.isSuccess())
                return result.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return "其它";
        }
        return "其它";
    }
}
