import javax.swing.event.*;
 
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Notice that this class extends JPanel
public class SupportTicketGUI extends JPanel{

    //Global variables for GUI components
    private JLabel descriptionLabel;
    private JLabel reporterLabel;

    private JTextField descriptionTextField;
    private JTextField reporterTextField;
    private JButton addTicketToListButton;
    private JScrollPane listScrollPane;
    private JList<Ticket> ticketJList;
    private JButton deleteTicketButton;
    private JButton quitButton;
	private int priority;
	private JSpinner prioritySpinner;
	private JLabel priorityLabel;
	
	SpinnerNumberModel priorityModel = new SpinnerNumberModel(1,1,5,1);
	
    DefaultListModel<Ticket> ticketListModel;

    //Configure your GUI components in the constructor
    SupportTicketGUI() {

        //Set up GUI components here

        addComponents();
        configureListeners();
    }

    private void addComponents() {

        priorityLabel = new JLabel("Choose a priority rating");
		prioritySpinner = new JSpinner(priorityModel);
		descriptionLabel = new JLabel("Enter description of the issue");
        reporterLabel = new JLabel("Enter the name of who reported this issue");

        descriptionTextField = new JTextField();
        reporterTextField = new JTextField();

        
        addTicketToListButton = new JButton("Add");

        //Create a scroll pane, and add the list to it.
        //This keeps the list with	in the size of the scroll pane.
        //otherwise, the list gets larger with every element added, and the
        //GUI window gets larger too. With a scroll pane, if there are more elements
        //than fit in the scroll pane, then scroll bars appear.
        ticketJList = new JList<Ticket>();
        listScrollPane = new JScrollPane(ticketJList);

        deleteTicketButton = new JButton("Delete");

        quitButton = new JButton("Quit");

        ticketListModel = new DefaultListModel<Ticket>();

        ticketJList.setModel(ticketListModel);

        //Configure JList to only allow user to select one item at a time
        //Default is multiple selections.

        ticketJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        //Layout of components can be tricky. By default, components are laid out in a horizontal line, one after the other.
        // If that's not what you want, you'll need to create a layout manager to manage the position and size of your components.
        // This GUI uses a combination of two Layout managers.
        //BorderLayout arranges components in the north, south, east, west, or center.
        //BoxLayout can arrange components in vertical or horizontal stack(s).

        //To get the desired layout,
        //Create a BorderLayout for the whole JPanel.
        //Create a JPanel for the data entry fields - the JTextFields, JLabels, JCheckBox and use BoxLayout to arrange these components
        //The BoxLayout.Y_AXIS parameter specifies a vertical stack. See docs for more on
        //using BoxLayout.
        //And create another JPanel for the Quit and Delete buttons, Use another BoxLayout to arrange these buttons in a horizontal line.


        //The window as a whole uses a BorderLayout.
        BorderLayout layoutManager = new BorderLayout();
        setLayout(layoutManager);

        //Create a new panel for the data entry components. Use a BoxLayout to manage
        //the layout of these components
        JPanel dataEntryPanel = new JPanel();
        dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));

        //Add the components to dataEntryPanel...
        dataEntryPanel.add(descriptionLabel);
        dataEntryPanel.add(descriptionTextField);
        dataEntryPanel.add(reporterLabel);
        dataEntryPanel.add(reporterTextField);
        dataEntryPanel.add(priorityLabel);
		dataEntryPanel.add(prioritySpinner);
		dataEntryPanel.add(addTicketToListButton);

        //And then add dataEntryPanel to the main JPanel window, in the north (at the top)
        add(dataEntryPanel, BorderLayout.NORTH);

        //Add the scroll pane in the middle. By default, center components will take up all of the remaining
        //space in the GUI window, so we don't need to set the size.
        add(listScrollPane, BorderLayout.CENTER);   //Which contains the dogJList

        //And make another panel to contain the quit and delete buttons
        //this uses a horizontal BoxLayout
        //Add components to this JPanel...
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(deleteTicketButton);
        buttonPanel.add(quitButton);
        //And then add the JPanel to the South, or bottom, of the window.
        add(buttonPanel, BorderLayout.SOUTH);


    }

    private void configureListeners() {
        addTicketToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read data from JTextFields 
                String description = descriptionTextField.getText();

                //Validation - make sure user enters something for the name. Trim whitespace and then check length is more than 0
                if (description.trim().length() == 0) {
                    JOptionPane.showMessageDialog(SupportTicketGUI.this, "Please enter a description");
                    return;
                }

String reporter = reporterTextField.getText();

                 if (reporter.trim().length() == 0) {
                    JOptionPane.showMessageDialog(SupportTicketGUI.this, "Please enter a reporter");
                    return;
                }
				
				
				
				
				Date date = new Date();
				// Create Ticket and add to JList's model
                Ticket newTicket = new Ticket(description, priority, reporter, date);
                ticketListModel.addElement(newTicket);

				
                descriptionTextField.setText("");
                reporterTextField.setText("");

            }
        });

		// credit for spinner http://www.tutorialspoint.com/swing/swing_jspinner.htm
		 prioritySpinner.addChangeListener(new ChangeListener() {
			 @Override
				public void stateChanged(ChangeEvent e) {
				priority =(int) prioritySpinner.getValue();
				}
				});
				
		
		
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ask the JLIST what ticket is selected
                // Notice since we've used generic types, setSelectedValue returns a Ticket.
                // Without generic types, it would return an Object, and we'd have to cast it.
                Ticket toDelete = ticketJList.getSelectedValue();
                // Delete this Dog from the MODEL
                ticketListModel.removeElement(toDelete);
            }
        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }


    public static void main(String[] args) {

        //Create a frame - the GUI window
        JFrame ticketFrame = new JFrame("Support Ticket");
        //configure the JFrame / GUI window
        //dogFrame.setSize(350, 200);
        ticketFrame.setVisible(true);
        ticketFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ticketFrame.setPreferredSize(new Dimension(400, 300));

        //Create an object from this class - it's a JPanel,  which is a GUI component
        SupportTicketGUI panel = new SupportTicketGUI();

        //And add it to the JFrame
        ticketFrame.add(panel);

        ticketFrame.pack();

    }
}