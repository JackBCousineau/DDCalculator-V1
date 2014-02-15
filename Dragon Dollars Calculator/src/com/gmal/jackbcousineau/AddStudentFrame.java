package com.gmal.jackbcousineau;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class AddStudentFrame extends JFrame{

	static JTextField studentNameTextField;
	static JButton addStudentConfirmButton;

	public AddStudentFrame(){
		super("Add a student");
		setMinimumSize(new Dimension(225, 140));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		getContentPane().add(pane);
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		JLabel textFieldLabel = new JLabel("Enter the name of the student");
		pane.add(textFieldLabel);
		studentNameTextField = new JTextField("");
		pane.add(studentNameTextField);
		addStudentConfirmButton = new JButton("Add Student");
		addStudentConfirmButton.addActionListener(DDCalculator.buttonActionListener);
		pane.add(addStudentConfirmButton);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, textFieldLabel, 0, SpringLayout.HORIZONTAL_CENTER, pane);
		layout.putConstraint(SpringLayout.NORTH, textFieldLabel, 10, SpringLayout.NORTH, pane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, studentNameTextField, 0, SpringLayout.HORIZONTAL_CENTER, pane);
		layout.putConstraint(SpringLayout.NORTH, studentNameTextField, 10, SpringLayout.SOUTH, textFieldLabel);
		layout.putConstraint(SpringLayout.WEST, studentNameTextField, 10, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.EAST, studentNameTextField, -10, SpringLayout.EAST, pane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addStudentConfirmButton, 0, SpringLayout.HORIZONTAL_CENTER, pane);
		layout.putConstraint(SpringLayout.NORTH, addStudentConfirmButton, 10, SpringLayout.SOUTH, studentNameTextField);

		setLocationRelativeTo(null);
		setResizable(false);
	}

}
