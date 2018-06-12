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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fx.feign.SpglInterface;
import org.fx.spgl.model.SpglModel;
import org.fx.spgl.view.SpglView;
import org.fx.urils.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
            model.setSxj((sfxj.getValue()).equals("否") ? 0 : 1);
            model.setZt(zt.getId());

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
                String s = formUpload(urlStr, textMap, fileMap, contentType);
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

    /**
     * 上传图片
     *
     * @param urlStr
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     *                    contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap, String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        } else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        } else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
}
