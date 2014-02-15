package com.gmal.jackbcousineau;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileSystemView;

public class DDCalculator {

	public static JFrame mainMenuFrame, addStudentFrame, helpFrame, lawOfSinesFrame;
	public static ButtonActionListener buttonActionListener;
	public static File file = null;
	public static ImageIcon dollarSignIcon, logo;

	public static void main(String args[]) throws Exception{
		new DDCalculator();
	}

	DDCalculator() throws IOException{
		file = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Library/Application Support/Dragon Dollars Calculator");
		if(!file.exists()) file.mkdirs();
		if(!new File(file.getPath() + "/dollar sign.png").exists()) downloadImage("http://i.imgur.com/ixNgLhX.png", file.getPath() + "/dollar sign.png");
		if(!new File(file.getPath() + "/logo.png").exists()) downloadImage("http://i.imgur.com/LNLJ7ce.png", file.getPath() + "/logo.png");
		dollarSignIcon = new ImageIcon(file.getPath() + "/dollar sign.png");
		HelpFrame.logo = new JLabel(new ImageIcon(file.getPath() + "/logo.png"));
		buttonActionListener = new ButtonActionListener();
		mainMenuFrame = new MainMenuFrame();
		addStudentFrame = new AddStudentFrame();
		helpFrame = new HelpFrame();
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(file.getPath() + "/Save Data.txt", "UTF-8");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				for(StudentEntry studentEntry : MainMenuFrame.entries){
					writer.println(studentEntry.studentNameLabel.getText() + ",:," + studentEntry.inputField.getText().replaceAll(",:,", ""));
				}
				writer.close();
			}
		});
	}

	void downloadImage(String URL, String saveLocation) throws MalformedURLException, IOException{
		InputStream in = new BufferedInputStream(new URL(URL).openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		FileOutputStream fos = new FileOutputStream(saveLocation);
		fos.write(out.toByteArray());
		fos.close();
	}

	@SuppressWarnings("serial")
	static class MainMenuFrame extends JFrame{

		static JButton addStudentButton, helpButton, calculateButton;
		static JPanel pane = new JPanel();
		static JScrollPane entryPane = new JScrollPane();
		static SpringLayout layout = new SpringLayout();
		static ArrayList<StudentEntry> entries = new ArrayList<StudentEntry>();
		static StudentEntry lowestEntry(){
			if(!entries.isEmpty()) return entries.get(entries.size()-1);
			return null;
		}

		public MainMenuFrame() throws IOException{
			super("Dragon Dollars Calculator");
			getContentPane().add(pane);
			setMinimumSize(new Dimension(340, 403));
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pane.setLayout(layout);

			addStudentButton = new JButton("Add Student");
			pane.add(addStudentButton);
			addStudentButton.addActionListener(buttonActionListener);
			addStudentButton.setFocusable(false);
			helpButton = new JButton("Help");
			pane.add(helpButton);
			helpButton.addActionListener(buttonActionListener);
			helpButton.setFocusable(false);
			calculateButton = new JButton("Calculate");
			pane.add(calculateButton);
			calculateButton.addActionListener(buttonActionListener);
			calculateButton.setFocusable(false);

			layout.putConstraint(SpringLayout.EAST, addStudentButton, 10, SpringLayout.HORIZONTAL_CENTER, pane);
			layout.putConstraint(SpringLayout.NORTH, addStudentButton, 10, SpringLayout.NORTH, pane);

			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, helpButton, 0, SpringLayout.HORIZONTAL_CENTER, pane);
			layout.putConstraint(SpringLayout.SOUTH, helpButton, -10, SpringLayout.SOUTH, pane);

			layout.putConstraint(SpringLayout.VERTICAL_CENTER, calculateButton, 0, SpringLayout.VERTICAL_CENTER, addStudentButton);
			layout.putConstraint(SpringLayout.WEST, calculateButton, 0, SpringLayout.EAST, addStudentButton);

			String saveData = file.getPath() + "/Save Data.txt";
			if(!new File(saveData).exists()){
				new File(saveData);
			}
			else{
				List<String> lines = new ArrayList<String>();
				BufferedReader br = new BufferedReader(new FileReader(saveData));
				String line = br.readLine();
				while (line != null)
				{
					lines.add(line);
					line = br.readLine();
				}
				br.close();
				for(String string : lines){
					String[] entryString = string.split(",:,");
					if(entryString.length == 1) MainMenuFrame.addStudent(entryString[0], "", true);
					else MainMenuFrame.addStudent(entryString[0], entryString[1], true);
				}
				if(entries.size() > 9) setMinimumSize(new Dimension(340, 403 + ((entries.size() - 9)*34)));
			}
			setVisible(true);


		}

		public static void setHeight(){
			int height = mainMenuFrame.getHeight();
			int width = mainMenuFrame.getWidth();
			int newHeight = 403;
			if(entries.size() > 9) newHeight = 403 + ((entries.size() - 9)*34);
			mainMenuFrame.setMinimumSize(new Dimension(340, newHeight));
			if((height - 403)%34 == 0){
				mainMenuFrame.setSize(width, newHeight);
			}
		}

		public static void addStudent(String studentName, String textFieldInput, boolean isFirstLoad){
			StudentEntry studentEntry = new StudentEntry(studentName, textFieldInput);
			drawStudentEntry(studentEntry, false);
			entries.add(studentEntry);
			if(!isFirstLoad) setHeight();
			pane.updateUI();
		}

		public static void drawStudentEntry(StudentEntry studentEntry, boolean redraw){
			pane.add(studentEntry.studentNameLabel);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, studentEntry.studentNameLabel, 0, SpringLayout.VERTICAL_CENTER, studentEntry.inputField);
			layout.putConstraint(SpringLayout.EAST, studentEntry.studentNameLabel, -5, SpringLayout.WEST, studentEntry.inputField);
			layout.putConstraint(SpringLayout.WEST, studentEntry.studentNameLabel, 10, SpringLayout.WEST, pane);
			pane.add(studentEntry.inputField);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, studentEntry.inputField, 0, SpringLayout.HORIZONTAL_CENTER, pane);
			if(entries.size() == 0||studentEntry.equals(entries.get(0))) layout.putConstraint(SpringLayout.NORTH, studentEntry.inputField, 5, SpringLayout.SOUTH, addStudentButton);
			else if(!redraw) layout.putConstraint(SpringLayout.NORTH, studentEntry.inputField, 5, SpringLayout.SOUTH, entries.get(entries.size()-1).inputField);
			else layout.putConstraint(SpringLayout.NORTH, studentEntry.inputField, 5, SpringLayout.SOUTH, entries.get(entries.indexOf(studentEntry)-1).inputField);
			layout.putConstraint(SpringLayout.EAST, studentEntry.inputField, -60, SpringLayout.EAST, pane);
			pane.add(studentEntry.removeStudentButton);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, studentEntry.removeStudentButton, 1, SpringLayout.VERTICAL_CENTER, studentEntry.inputField);
			layout.putConstraint(SpringLayout.WEST, studentEntry.removeStudentButton, 10, SpringLayout.EAST, studentEntry.inputField);
			layout.putConstraint(SpringLayout.EAST, studentEntry.removeStudentButton, -10, SpringLayout.EAST, pane);
		}

		public static void removeStudentEntry(StudentEntry studentEntry){
			pane.remove(studentEntry.studentNameLabel);
			pane.remove(studentEntry.inputField);
			pane.remove(studentEntry.removeStudentButton);
		}


		public static void redrawStudentEntries(boolean addActionListeners){
			for(StudentEntry studentEntry : entries){
				removeStudentEntry(studentEntry);
			}
			for(StudentEntry studentEntry : entries){
				drawStudentEntry(studentEntry, true);
			}
			if(addActionListeners){
				for(StudentEntry studentEntry : entries){
					studentEntry.addActionListener();
				}
			}
		}

		static void calculate(){
			if(entries.size() == 0) JOptionPane.showMessageDialog(mainMenuFrame, "Add a student first!", "Uh-oh...", JOptionPane.PLAIN_MESSAGE);
			else{
				StringBuilder stringBuilder = new StringBuilder();
				for(StudentEntry studentEntry : entries){
					String toParse = studentEntry.inputField.getText().toLowerCase();
					if(toParse != ""){
						int counter = 0;
						for( int i=0; i<toParse.length(); i++ ){
							if(toParse.charAt(i) == 'r'){
								counter -= 3;
							}
							else if(toParse.charAt(i) == 'o'){
								counter -= 2;
							}
							else if(toParse.charAt(i) == 'y'){
								counter -= 1;
							}
							else if(toParse.charAt(i) == 'b'){
								counter += 1;
							}
							else if(toParse.charAt(i) == 'p'){
								counter += 2;
							}
						}
						stringBuilder.append(studentEntry.studentNameLabel.getText() + ": $" + counter + "\n");
					}
				}
				JOptionPane.showMessageDialog(mainMenuFrame, stringBuilder, "Results", JOptionPane.INFORMATION_MESSAGE, dollarSignIcon);
			}
		}
	}

	static class ButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event){
			Object source = event.getSource();
			if(source == MainMenuFrame.addStudentButton){
				addStudentFrame.setVisible(true);
				addStudentFrame.getRootPane().setDefaultButton(AddStudentFrame.addStudentConfirmButton);
			}
			else if(source == AddStudentFrame.addStudentConfirmButton){
				String studentName = AddStudentFrame.studentNameTextField.getText();
				if(studentName.equals("")) JOptionPane.showMessageDialog(addStudentFrame, "You need to input a name!", "Uh-oh...", JOptionPane.PLAIN_MESSAGE);
				else if(studentName.contains(",:,")) JOptionPane.showMessageDialog(addStudentFrame, "Names must not contain the sequence \",:,\"", "Uh-oh...", JOptionPane.PLAIN_MESSAGE);
				else{
					MainMenuFrame.addStudent(studentName, "", false);
					addStudentFrame.setVisible(false);
					AddStudentFrame.studentNameTextField.setText("");
				}
			}
			else if(source == MainMenuFrame.calculateButton){
				MainMenuFrame.calculate();
			}
			else if(source == MainMenuFrame.helpButton){
				helpFrame.setVisible(true);
			}
		}
	}

}
