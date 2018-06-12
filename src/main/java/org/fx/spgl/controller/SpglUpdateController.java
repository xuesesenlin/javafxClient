package org.fx.spgl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
import org.fx.spgl.view.SpglView;
import org.fx.urils.*;
import org.fx.urils.uploadImg.UpLoadImg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SpglUpdateController implements Initializable {

    private SpglInterface spglInterface = FeignUtil.feign()
            .target(SpglInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private AlertUtil alert = new AlertUtil();

    @FXML
    private Label uuid;
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
        getData();
    }

    private void getData() {
        //        是否下架
        sfxj.setItems(FXCollections.observableArrayList(
                "否", "是"));
        sfxj.setValue("否");
//        商品类目
        lm.setItems(FXCollections.observableArrayList(
                new SpglController().spfl_data().split(",")));
    }

    @FXML
    private void save(MouseEvent event) {
        try {
            SpglModel model = new SpglModel();
            model.setUuid(uuid.getText());
            model.setCname(cname.getText());
            model.setJg(Double.parseDouble(jg.getText()));
            model.setDw(dw.getText());
            model.setGe(ge.getText());
            model.setPp(pp.getText());
            model.setXq(xq.getText());
            model.setSl(Integer.parseInt(sl.getText()));
            model.setLm((String) lm.getValue());
            model.setSxj((sfxj.getValue()).equals("否") ? 0 : 1);
            model.setZt(zt.getId());

            String json = objectMapper.writeValueAsString(model);
            ResponseResult<String> result = spglInterface.update(json + StaticToken.getToken());
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

    @FXML
    private void addImg(MouseEvent event) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择文件");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null)
                alert.f_alert_informationDialog("提示", "请选择文件");
            else {
                String urlStr = new FeignRequest().URL() + "/api/upload/upload";
                Map<String, String> fileMap = new HashMap<>();
                fileMap.put("file", file.getPath());
                String contentType = "";//image/png
                String token = StaticToken.getToken();
                Map<String, String> textMap = new HashMap<>();
                //可以设置多个input的name，value
                textMap.put("token", token);
                String s = UpLoadImg.formUpload(urlStr, textMap, fileMap, contentType);
                ObjectMapper mapper = new ObjectMapper();
                ResponseResult result = mapper.readValue(s, ResponseResult.class);
                if (result.isSuccess()) {
                    zt.setImage(new Image(new FeignRequest().URL() + "/commodity/IoReadImage/" + result.getData().toString()));
                    zt.setId(result.getData().toString());
                } else
                    alert.f_alert_informationDialog("警告", "文件上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert.f_alert_informationDialog("警告", "文件上传失败");
        }
    }
}
