package org.fx.spgl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javafx.util.Callback;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
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
    private TableView spgl_table;
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

    public void getData(int page) {
        ObservableList<SpglModel> list = FXCollections.observableArrayList();
//        映射
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
                        button.setOnAction(o -> {
                            AlertUtil alertUtil = new AlertUtil();
                            boolean b = alertUtil.f_alert_confirmDialog("警告", "是否确定删除?");
//                            if (b) {
//                                int i = new SpglController().del(o, this.getTableView().getItems().get(this.getIndex()));
//                                if (i > 0)
//                                    alertUtil.f_alert_informationDialog("通知", "成功");
//                                else
//                                    alertUtil.f_alert_informationDialog("警告", "失败");
//                                new SpglController().spgl(pane, 0);
//                            }
                        });
                        button.getStyleClass().add("button");
                        button.getStyleClass().add("button2");
                        button.getStyleClass().add("button3");
                        Button button2 = new Button("修改");
                        button2.setOnAction(o -> {
//                            new SpglController().update(o, pane_lout, this.getTableView().getItems().get(this.getIndex()));
                        });
                        button2.getStyleClass().add("button");
                        button2.getStyleClass().add("button2");
                        HBox hBox = new HBox();
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
                spgl_table.setItems(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData(0);
    }
}
