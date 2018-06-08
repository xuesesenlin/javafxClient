package org.fx.spgl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
import org.fx.spgl.view.SpglView;
import org.fx.urils.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SpglAddController implements Initializable {

    private SpglInterface spglInterface = FeignUtil.feign()
            .target(SpglInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private AlertUtil alert = new AlertUtil();

    @FXML
    private ChoiceBox sfxj;
    @FXML
    private ChoiceBox lm;
    @FXML
    private TextField cname;
    @FXML
    private TextField jg;
    @FXML
    private TextField dw;
    @FXML
    private TextField ge;
    @FXML
    private TextField pp;
    @FXML
    private TextField xq;
    @FXML
    private TextField sl;
    @FXML
    private ImageView zt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        是否下架
        sfxj.setItems(FXCollections.observableArrayList(
                "否", "是"));
        sfxj.setValue("否");
//        商品类目
        lm.setItems(FXCollections.observableArrayList(
                "其它"));
        lm.setValue("其它");
    }

    @FXML
    private void save(MouseEvent event) {
        try {
            SpglModel model = new SpglModel();
            model.setCname(cname.getText());
            model.setJg(Double.parseDouble(jg.getText()));
            model.setDw(dw.getText());
            model.setGe(ge.getText());
            model.setPp(pp.getText());
            model.setXq(xq.getText());
            model.setSl(Integer.parseInt(sl.getText()));
            model.setLm((String) lm.getValue());
            model.setSxj(((String) sfxj.getValue()).equals("否") ? 0 : 1);
            Image image = zt.getImage();
//        model.setZt(zt.);

            String json = objectMapper.writeValueAsString(model);
            ResponseResult<String> result = spglInterface.add(json + StaticToken.getToken());
            if (result.isSuccess()) {
                String s = result.getData().substring(result.getData().lastIndexOf("}") + 1, result.getData().length());
                StaticToken.setToken(s);
                alert.f_alert_informationDialog("提示", "成功");
                new SpglView().init();
            } else {
                StaticToken.setToken(result.getData());
                alert.f_alert_informationDialog("警告", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert.f_alert_informationDialog("警告", "失败");
        }
    }
}
