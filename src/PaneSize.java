import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PaneSize extends JFrame {

    public PaneSize(){       setSize(640,480);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().addComponentListener(new ComponentAdapter() {
        public void componentResized(ComponentEvent e){
            Component c = (Component)e.getSource();

        }});
    }
}
