package lshape;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.ericaro.surfaceplotter.JSurfacePanel;
import net.ericaro.surfaceplotter.surface.ArraySurfaceModel;

import javax.swing.*;

import java.awt.*;

import static javafx.application.Application.launch;

/**
 * Created by malens on 2017-01-18.
 */
public class plot extends Application {

    private JFrame window = new JFrame();
    private JSurfacePanel plotFrame = new JSurfacePanel();
    private JPanel panel = new JPanel();


    public void start(Stage primaryStage) throws Exception {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(800,600));
        plotFrame.setTitleText("Rozwiązanie");
        plotFrame.setConfigurationVisible(false);
        window.getContentPane().add(plotFrame, BorderLayout.CENTER);
        window.getContentPane().add(panel, BorderLayout.SOUTH);

        
        window.pack();
        solve(9);
        window.setVisible(true);

    }

    public static void main(String[] args){
        launch(args);
    }

    void solve(int n) {
        ArraySurfaceModel model = createModel();
        solve.solver(n, model);
        plotFrame.setModel(model);
        plotFrame.repaint();

    }



    private ArraySurfaceModel createModel(){
        ArraySurfaceModel model = new ArraySurfaceModel();
        model.setDisplayXY(true);
        model.setDisplayZ(true);
        model.setDisplayGrids(true);
        model.setBoxed(true);
        return model;
    }





}
