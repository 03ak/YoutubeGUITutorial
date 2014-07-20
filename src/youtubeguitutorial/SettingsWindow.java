/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtubeguitutorial;

/**
 *
 * @author tr266
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SettingsWindow extends JDialog{
    private JLabel label;
    private JTextField level;
    private int lev;
    
    public SettingsWindow(JFrame frame){
        super(frame,"Help Window", true);
        setLayout(new FlowLayout());
        label = new JLabel("For further information please contact, tr266@cornell.edu");
        add(label);
        
    }
    
}
