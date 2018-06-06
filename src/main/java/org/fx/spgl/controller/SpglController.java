package org.fx.spgl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
import org.fx.urils.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SpglController {

    private static Logger logger = Logger.getLogger(SpglController.class.toString());
    private SpglInterface spglInterface = FeignUtil.feign()
            .target(SpglInterface.class, new FeignRequest().URL());
    private ObjectMapper objectMapper = new ObjectMapper();
    private AlertUtil alert = new AlertUtil();

    @FXML
    private AnchorPane spzl_index;

    public ResponseResult<List<SpglModel>> index(int page) {
        ResponseResult<String> result = spglInterface.page(page, 15, StaticToken.getToken());
        if (result.isSuccess()) {
            String json = result.getData().substring(0, result.getData().lastIndexOf("]") + 1);
            try {
                List<SpglModel> beanList = objectMapper.readValue(json, new TypeReference<List<SpglModel>>() {
                });
                String s = result.getData().substring(result.getData().lastIndexOf("]") + 1, result.getData().length());
                StaticToken.setToken(s);
                return new ResponseResult<>(true, "成功", beanList);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseResult<>(false, "数据转换错误", null);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(false, "获取数据失败", null);
            }
        } else {
            return new ResponseResult<>(false, result.getMessage(), null);
        }
    }
}
