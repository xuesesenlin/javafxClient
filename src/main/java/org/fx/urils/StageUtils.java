package org.fx.urils;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

//关闭子页面
public class StageUtils {

    public static void close() {
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        for (int i = stages.size() - 1; i > 0; i--) {
            stages.get(i).close();
        }
    }
}
