
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AgileOrWaterfallGUI extends JPanel{

    //Global variables for GUI components
    private JTextField textField;
	
	private JCheckBox Q1CheckBox;
    private JCheckBox Q2CheckBox;
    private JCheckBox Q3CheckBox;
	private JCheckBox Q4CheckBox;
    private JCheckBox Q5CheckBox;
    private JCheckBox Q6CheckBox;
    
	
    private JLabel instructionsLabel;
	private JButton quitButton;
    
 
    private JLabel ResultsLabel;
	
    private JPanel rootPanel;

    private boolean Q1, Q2, Q3, Q4, Q5, Q6;

    //Configure your GUI components in the constructor
    AgileOrWaterfallGUI() {
        
		//Create objects for GUI
		instructionsLabel = new JLabel("Check each box if the corresponding question is true about your project.");
textField = new JTextField(20);
		
        Q1CheckBox = new JCheckBox("Will there be many programmers assigned to this project?");
        Q2CheckBox = new JCheckBox("Are there firm deadlines?");
        Q3CheckBox = new JCheckBox("Do the programmers have experience in requirements, analysis and testing as well as coding?");
Q4CheckBox = new JCheckBox("Are there stringent quality control requirements?");
Q5CheckBox = new JCheckBox("Is early integration desirable?");
Q6CheckBox = new JCheckBox("Will the customer  be requiring working models early in the process?");

        ResultsLabel = new JLabel("Results here");

        quitButton = new JButton("Quit");

        //  add the components to  GUI
        add(instructionsLabel);
		add(textField);
        add(Q1CheckBox);
        add(Q2CheckBox);
        add(Q3CheckBox);
add(Q4CheckBox);
add(Q5CheckBox);
add(Q6CheckBox);

        add(ResultsLabel);
        add(quitButton);
//Add listeners
        Q1CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q1 = Q1CheckBox.isSelected();
                updateResults();
            }
        });

        Q2CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q2 = Q2CheckBox.isSelected();
                updateResults();
            }
        });

        Q3CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q3 = Q3CheckBox.isSelected();
                updateResults();
            }
        });


Q4CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q4 = Q4CheckBox.isSelected();
                updateResults();
            }
        });
        
Q5CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q5 = Q5CheckBox.isSelected();
                updateResults();
            }
        });
        
Q6CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Q6 = Q6CheckBox.isSelected();
                updateResults();
            }
        });
        
		
		

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Show 'are you sure' dialog box
                int quit = JOptionPane.showConfirmDialog(AgileOrWaterfallGUI.this,
                        "Are you sure you want to quit?", "Quit?",
                        JOptionPane.OK_CANCEL_OPTION);

                //Check which option user selected, quit if user clicked ok
                if (quit == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });



    }

    private void updateResults() {

	String projectName = textField.getText();
	String finalResult;
	int waterfall=0;
	int agile=0;
	
	//evaluate each answer and add a point to which project methodology fits better
		
		
		if (Q1 ==  (true))
		{ waterfall+=1;}
		else
		{agile+=1;}
		
if (Q2 == (true))
		{ waterfall+=1;}
		else
		{agile+=1;}
		
		if (Q3 == (false))
		{ waterfall+=1;}
		else
		{agile+=1;}
		
		if (Q4 == (true))
		{ waterfall+=1;}
		else
		{agile+=1;}
			
		if (Q5 == (false))
		{ waterfall+=1;}
		else
		{agile+=1;}
		
		if (Q6 == (false))
		{ waterfall+=1;}
		else
		{agile+=1;}
//finally, print which methodology is better based on the score each integer variable received		
		if (agile > waterfall)
		{finalResult = "Agile is a better method based on your input.";}
		else
		{finalResult = "Waterfall is a better method based on your input.";}
		
	
	
	ResultsLabel.setText(projectName+":  "+finalResult);
	
	
        }

        
    public static void main(String[] args) {

        //Create a frame - the GUI window
        JFrame QuestionFrame = new JFrame("Agile or Waterfall");
        //configure the JFrame / GUI window
        QuestionFrame.setSize(500, 500);
		QuestionFrame.setVisible(true);
        QuestionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		

        
	
        //Create an object from this class - it's a JPanel,  which is a GUI component
        AgileOrWaterfallGUI QuestionGUI = new AgileOrWaterfallGUI();

        //And add it to the JFrame
        QuestionFrame.add(QuestionGUI);

    }
}
