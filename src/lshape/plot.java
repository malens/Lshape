package lshape;
import net.ericaro.surfaceplotter.JSurfacePanel;
import net.ericaro.surfaceplotter.surface.ArraySurfaceModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

/**
 * Created by malens on 2017-01-18.
 */
public class plot {

    private JFrame window = new JFrame();
    private JSurfacePanel plotFrame = new JSurfacePanel();
    private JPanel panel = new JPanel();
    private int n = 8;
    private Integer[] possibleDivisions;
    private JComboBox<Integer> divisionList;

    /*(x, y) -> {
        double r = Math.sqrt(x * x + y * y);
        double sin = Math.sin(Math.atan(y / x) + Math.PI / 2);
        return = Math.cbrt(r * r) * Math.cbrt(sin * sin);
    }*/




    public plot() {

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setPreferredSize(new Dimension(800,600));
        this.plotFrame.setTitleText("Rozwiązanie");
        this.plotFrame.setConfigurationVisible(false);
        this.window.getContentPane().add(plotFrame, BorderLayout.CENTER);
        this.window.getContentPane().add(panel, BorderLayout.SOUTH);

        this.possibleDivisions = new Integer[15];
        for (int i = 1; i<=15;i++)
        {
            possibleDivisions[i-1]=i;
        }
        this.divisionList = new JComboBox<>(this.possibleDivisions);
        this.divisionList.setEditable(true);
        this.divisionList.setSelectedItem(n);
        this.divisionList.addActionListener(new eventHandling(this));

        this.panel.add(divisionList, BorderLayout.NORTH);
        this.window.pack();
    }
    public class eventHandling implements ActionListener {
        private plot parent;
        public eventHandling (plot parent)
        {
            this.parent = parent;
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            int newSelection = (int)cb.getSelectedItem();
            this.parent.n = newSelection;
            solve(this.parent.n);
            this.parent.window.pack();
        }
    }


    public void run(){
        solve(n);
        window.setVisible(true);
    }



    void solve(int n) {
        ArraySurfaceModel model = createModel();
        solve solution = new solve();
        solution.solver(n, model);
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
