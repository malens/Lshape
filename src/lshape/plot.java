package lshape;
import net.ericaro.surfaceplotter.JSurfacePanel;
import net.ericaro.surfaceplotter.surface.ArraySurfaceModel;
import javax.swing.*;
import java.awt.*;

import static javafx.application.Application.launch;

/**
 * Created by malens on 2017-01-18.
 */
public class plot {

    private JFrame window = new JFrame();
    private JSurfacePanel plotFrame = new JSurfacePanel();
    private JPanel panel = new JPanel();




    public plot() {

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setPreferredSize(new Dimension(800,600));
        this.plotFrame.setTitleText("Rozwiązanie");
        this.plotFrame.setConfigurationVisible(false);
        this.window.getContentPane().add(plotFrame, BorderLayout.CENTER);
        this.window.getContentPane().add(panel, BorderLayout.SOUTH);
        this.window.pack();


    }

    public void run(){
        solve(9);
        window.setVisible(true);
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
