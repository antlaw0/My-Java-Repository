import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
			
public class SupportTicketGUI extends JPanel{

			private JLabel descriptionLabel;
			private JLabel reporterLabel;
			private JLabel priorityLabel;
			private JTextField descriptionTextField;
			private JTextField reporterTextField;
			private JTextField priorityTextField;
			private JButton addButton;
			
			DefaultListModel<Ticket> ticketListModel;
			
	SupportTicketGUI {
		
		descriptionLabel = new JLabel("Enter description of the issue");
		reporterLabel = new JLabel("Enter name of who reported this issue");
		descriptionTextField = new JTextField();
		reporterTextField = new JTextField();
		addButton = new JButton("ADD");
		
	}//end of SupportTicketGUI constructor

			
}//end of SupportTicket class