package com.gmal.jackbcousineau;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.gmal.jackbcousineau.DDCalculator.MainMenuFrame;

public class StudentEntry{


	public JLabel studentNameLabel;
	public JTextField inputField;
	public JButton removeStudentButton = new JButton("X");

	public StudentEntry(String studentName, String textFieldContents){
		studentNameLabel = new JLabel(studentName, SwingConstants.RIGHT);
		inputField = new JTextField(textFieldContents);
		addActionListener();
	}

	public void addActionListener(){
		removeStudentButton.addActionListener(new CloseButtonActionListener(this));
		removeStudentButton.setFocusable(false);
	}

	static class CloseButtonActionListener implements ActionListener{

		StudentEntry studentEntry;

		public CloseButtonActionListener(StudentEntry studentEntry){
			this.studentEntry = studentEntry;
		}
		@Override
		public void actionPerformed(ActionEvent event){
			Object source = event.getSource();
			if(source == studentEntry.removeStudentButton){
				boolean wasLowestEntry = MainMenuFrame.lowestEntry() == studentEntry;
				MainMenuFrame.removeStudentEntry(studentEntry);
				MainMenuFrame.entries.remove(studentEntry);
				if(!wasLowestEntry) MainMenuFrame.redrawStudentEntries(false);
				else MainMenuFrame.setHeight();
				MainMenuFrame.pane.updateUI();
			}
		}
	}

}
