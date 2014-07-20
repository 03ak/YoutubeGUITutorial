/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtubeguitutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
/**
 *
 * @author tr266
 */
public class YoutubeGUITutorial extends JFrame{
    
    private JLabel timeLeftLabel;
    private JLabel label;
    private JButton button;
    private JTextField textfield;
    private JTextField infoTextField;
    private JMenuBar menubar;
    private JMenu file,help;
    private JMenuItem exit,save,about;
    private Timer timer;
    private int tfFlag=0;//this flag communicate between textfield and timer tick
    private int inputIndexInfoTextField=0;
    int timepermath = 15,timeForPrtcSessionPerMath=120;//Specify Total time allowance per math
    int IDnum;
    int levelofmath;
    String location;
    int indexQuestion=0,indexResponse=0, indexTimeTaken=0;
    int[] randPermIndex;
    int practSesIndex = 0;
    
    int totalNumOfMath = 20;//Specify Total Number of Math per session
    String[] mathQuestion = new String[totalNumOfMath];
    String[] response = new String[totalNumOfMath];
    String[] timeTaken = new String[totalNumOfMath];
    
    String[] mathLevel1 = new String[totalNumOfMath];
    String[] mathLevel2 = new String[totalNumOfMath];
    String[] mathLevel3 = new String[totalNumOfMath];
    String[] mathLevel4 = new String[totalNumOfMath];
    
    public YoutubeGUITutorial(){
        //setLayout(new FlowLayout());
        //setLayout(new GridBagLayout());
        setLayout(new GridLayout(3,1));
        
        //menubar
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        save = new JMenuItem("Save");
        file.add(save);
        exit = new JMenuItem("Exit");
        file.add(exit);
        
        help = new JMenu("Help");
        menubar.add(help);
        about = new JMenuItem("About");
        help.add(about);
        
        //getcontainerpane
        Container pane = this.getContentPane();
        
        //top panel setup
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1,1));
        
        timeLeftLabel = new JLabel ("");
        Font font = new Font("Serif", Font.BOLD, 80);
        timeLeftLabel.setFont(font);
        timeLeftLabel.setHorizontalAlignment(timeLeftLabel.CENTER);
        top.add(timeLeftLabel);
        
        //middle panel setup
        JPanel middle = new JPanel();
        middle.setLayout(new GridLayout(1,2));
        
        label = new JLabel("",SwingConstants.CENTER);
        Font fontlabel = new Font("Serif", Font.BOLD, 16);
        label.setFont(fontlabel);
        middle.add(label);
        
        textfield = new JTextField(5);
        middle.add(textfield);
        
        //bottom panel setup
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2));
        
        button = new JButton("Start");
        bottom.add(button);
        
        infoTextField = new JTextField("level");
        bottom.add(infoTextField);
        
        pane.add(top);
        pane.add(middle);
        pane.add(bottom);
        
        textfield.setEnabled(false);
        button.setEnabled(false);
        
        randPermIndex = getRandomPermutation(totalNumOfMath);
        
        //adding one event
        event e = new event();
        textfield.addActionListener(e);
        
        //adding another event
        event2 e2 = new event2();
        exit.addActionListener(e2);
        
        //yet another event
        event3 e3 = new event3();
        about.addActionListener(e3);
        
        event4 e4 = new event4();
        button.addActionListener(e4);
        
        event5 e5 = new event5();
        infoTextField.addActionListener(e5);
        
        event6 e6 = new event6();
        save.addActionListener(e6);
    }
    
    public class event implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tfFlag=1;
            textfield.setEnabled(false);
        }
    }
    
    public class event2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    public class event6 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter outFile = new FileWriter(""+IDnum+"_"+location.toUpperCase()+"_"+levelofmath+".txt");
                PrintWriter out = new PrintWriter(outFile);
                out.println("mathQuestion,response,timeTaken");
                // Write text to file
                double avgTimeTaken=0;
                double numAnswered=0;
                double numCorrectlyAnswered=0;
                
                for(int i=0;i<totalNumOfMath;i++){  
                  out.println(mathQuestion[i]+","+response[i]+","+timeTaken[i]);
                  if(timeTaken[i].equals("N") || response[i].equals("N")){
                      avgTimeTaken=avgTimeTaken+timepermath;
                  }else{
                      numAnswered++;
                      avgTimeTaken=avgTimeTaken+Double.parseDouble(timeTaken[i]);
                      if(response[i].equals(mathQuestion[i].substring(mathQuestion[i].length()-1))){
                          numCorrectlyAnswered++;
                      }
                  }
                }
                out.println("TotalMathQuestionAppeared = "+totalNumOfMath);
                out.println("numAnswered = "+numAnswered);
                out.println("numCorrectlyAnswered = "+numCorrectlyAnswered);
                out.println("Accuracy = "+numCorrectlyAnswered/totalNumOfMath*100 + " Percent");
                out.println("AverageResponseTime = " + avgTimeTaken/totalNumOfMath + " Second");
                out.close();
            }catch(IOException ex){
                System.exit(0);
            }
        }
    }
    
    public class event3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            SettingsWindow gui = new SettingsWindow (YoutubeGUITutorial.this);
            gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            gui.setSize(500,50);
            gui.setLocation(300,300);
            gui.setVisible(true);
            
        }
    }
    
    public class event4 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            
            //Disable button
            button.setEnabled(false);
            textfield.setEnabled(true);
            textfield.grabFocus();

            //load another math in the label
            int randindex = randPermIndex[indexQuestion];
            
            String temp = "";
            if(levelofmath==1){
                temp = mathLevel1[randindex];
            }else if(levelofmath==2){
                temp = mathLevel2[randindex];
            }else if(levelofmath==3){
                temp = mathLevel3[randindex];
            }else if(levelofmath==4){
                temp = mathLevel4[practSesIndex];
                practSesIndex++;
            }
            label.setText(temp.substring(0, temp.length()-1));
            
            //saving the question
            mathQuestion[indexQuestion] = temp;
            indexQuestion++;
            
            int count;
            //Setting up the timer
            if(levelofmath==4){
                count = timeForPrtcSessionPerMath;
                timeLeftLabel.setText("");
            }else{
                count = timepermath;//10 seconds for each round
                timeLeftLabel.setText("Time left " + count);
            }

            TimeClass tc = new TimeClass(count);
            timer = new Timer(1000,tc);
            timer.start();
        }
    }
    
    public class TimeClass implements ActionListener {
        int counter;
        
        public TimeClass(int counter){
            this.counter = counter;
        }
        
        @Override
        public void actionPerformed(ActionEvent tc){
            counter--;
            
            if(counter>=1) {
                //whatever we want to perform at each second change
                    
                //textfield is already entered or not. if yes
                if(tfFlag==1){
                    //stop the timer
                    timer.stop();
                    //record the value of counter************************
                    if(levelofmath==4){
                        timeTaken[indexTimeTaken]=""+(timeForPrtcSessionPerMath-counter);
                    }else{
                        timeTaken[indexTimeTaken]=""+(timepermath-counter);
                    }
                    indexTimeTaken++;
                    //save the response in textfield**********************
                    response[indexResponse]=textfield.getText();
                    indexResponse++;
                    //clear textfield
                    textfield.setText("");
                    
                    if(indexQuestion==totalNumOfMath){
                        timeLeftLabel.setText("Thanks for participation");
                        label.setText("");
                        textfield.setEnabled(false);
                    }else{
                        //load another math in the label
                        int randindex = randPermIndex[indexQuestion];
                        String temp = "";
                        if(levelofmath==1){
                            temp = mathLevel1[randindex];
                        }else if(levelofmath==2){
                            temp = mathLevel2[randindex];
                        }else if(levelofmath==3){
                            temp = mathLevel3[randindex];
                        }else if(levelofmath==4){
                            temp = mathLevel4[practSesIndex];
                            practSesIndex++;
                        }
                        label.setText(temp.substring(0, temp.length()-1));
                        //saving the question
                        mathQuestion[indexQuestion] = temp;
                        indexQuestion++;
                        //restart the timer
                        int restrtCount;
                        if(levelofmath==4){
                            restrtCount=timeForPrtcSessionPerMath;
                        }else{
                            restrtCount=timepermath;
                        }
                        TimeClass tc1 = new TimeClass(restrtCount);
                        timer = new Timer(1000,tc1);
                        timer.start();
                        tfFlag=0;

                        //Enabling the input textfield
                        textfield.setEnabled(true);

                        // showing time left
                        timeLeftLabel.setForeground(Color.black);
                        if(levelofmath==4){
                            timeLeftLabel.setText("");
                        }else{
                            timeLeftLabel.setText("Time left " + timepermath);
                        }
                        
                    }
                    
                }else{
                    // showing time left
                    timeLeftLabel.setForeground(Color.black);
                    if(levelofmath==4){
                        timeLeftLabel.setText("");
                    }else{
                        timeLeftLabel.setText("Time left " + counter);
                    }
                    
                }
            }else{
                //whatever we want to perform when timer finishes counting.
                timer.stop();
                if(levelofmath==4){
                    
                }else{
                    timeLeftLabel.setText("TIME OUT!");
                    timeLeftLabel.setForeground(Color.red);
                    Toolkit.getDefaultToolkit().beep();
                }
                
                //saving response
                if(tfFlag==1){
                    response[indexResponse]=textfield.getText();
                    indexResponse++;
                    if(levelofmath==4){
                        timeTaken[indexTimeTaken]=""+(timeForPrtcSessionPerMath-counter);
                    }else{
                        timeTaken[indexTimeTaken]=""+(timepermath-counter);
                    }
                    indexTimeTaken++;
                    tfFlag=0;
                }else{
                    response[indexResponse]="N";
                    indexResponse++;
                    timeTaken[indexTimeTaken]="N";
                    indexTimeTaken++;
                }
                
                //Clear and Disable all the textfield
                textfield.setText("");
                textfield.setEnabled(false);
                
                //wait sometime for making it visible that time is out
                
                
                if(indexQuestion==totalNumOfMath){
                    timeLeftLabel.setText("Thanks for participation");
                    label.setText("");
                    textfield.setEnabled(false);
                }else{
                    //load another math in the label
                    int randindex = randPermIndex[indexQuestion];
                    String temp = "";
                    if(levelofmath==1){
                        temp = mathLevel1[randindex];
                    }else if(levelofmath==2){
                        temp = mathLevel2[randindex];
                    }else if(levelofmath==3){
                        temp = mathLevel3[randindex];
                    }else if(levelofmath==4){
                        temp = mathLevel4[practSesIndex];
                        practSesIndex++;
                    }
                    label.setText(temp.substring(0, temp.length()-1));
                    //saving the question
                    mathQuestion[indexQuestion] = temp;
                    indexQuestion++;
                    
                    //restart the timer
                    int restrtCount;
                    if(levelofmath==4){
                        restrtCount=timeForPrtcSessionPerMath;
                    }else{
                        restrtCount=timepermath;
                    }
                    TimeClass tc1 = new TimeClass(restrtCount);
                    timer = new Timer(1000,tc1);
                    timer.start();
                    //Enable the textfield for input
                    textfield.setEnabled(true);
                }
            }
        }
    }
    
    public class event5 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(inputIndexInfoTextField==0){
                levelofmath = (int)Double.parseDouble(infoTextField.getText());
                infoTextField.setText("Location");
                inputIndexInfoTextField=1;
            }else if (inputIndexInfoTextField==1){
                location = infoTextField.getText();
                infoTextField.setText("IDnum");
                inputIndexInfoTextField=2;
            }else if(inputIndexInfoTextField==2){
                IDnum = (int)Double.parseDouble(infoTextField.getText());
                infoTextField.setText(" ID "+IDnum);
                infoTextField.setEnabled(false);
                button.setEnabled(true);
                label.setText("Press start to begin");
            }
        }
    }
    
    public static int[] getRandomPermutation (int length){
        
        // initialize array and fill it with {0,1,2...}
        int[] array = new int[length];
        for(int i = 0; i < array.length; i++)
            array[i] = i;

        for(int i = 0; i < length; i++){

            // randomly chosen position in array whose element
            // will be swapped with the element in position i
            // note that when i = 0, any position can chosen (0 thru length-1)
            // when i = 1, only positions 1 through length -1
                        // NOTE: r is an instance of java.util.Random
            int ran = i + (int) Math.random()*(length-i);

            // perform swap
            int temp = array[i];
            array[i] = array[ran];
            array[ran] = temp;
        }                       
        return array;
    }
    
    public static void main(String[] args) throws IOException {
        
        YoutubeGUITutorial gui = new YoutubeGUITutorial();
        //Reading math problems of Level src/youtubeguitutorial/
        Scanner s1 = new Scanner(new BufferedReader(new FileReader("Level1.txt")));
        for(int i=0; i<gui.totalNumOfMath; i++){
          gui.mathLevel1[i]=s1.nextLine();
        }
        s1.close();
        
        Scanner s2 = new Scanner(new BufferedReader(new FileReader("Level2.txt")));
        for(int i=0; i<gui.totalNumOfMath; i++){
          gui.mathLevel2[i]=s2.nextLine();
        }
        s2.close();
        
        Scanner s3 = new Scanner(new BufferedReader(new FileReader("Level3.txt")));
        for(int i=0; i<gui.totalNumOfMath; i++){
          gui.mathLevel3[i]=s3.nextLine();
        }
        s3.close();
        
        Scanner s4 = new Scanner(new BufferedReader(new FileReader("Level4.txt")));
        for(int i=0; i<gui.totalNumOfMath; i++){
          gui.mathLevel4[i]=s4.nextLine();
        }
        s4.close();
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(1200,800);
        gui.setTitle("MSPG");
        gui.setVisible(true);
        
    }
}
