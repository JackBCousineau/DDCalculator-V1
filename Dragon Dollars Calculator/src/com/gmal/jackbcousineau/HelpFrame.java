package com.gmal.jackbcousineau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class HelpFrame extends JFrame{

	static JButton backButton, forwardButton;
	static HelpPages helpPages;
	static JLabel pageNumber = new JLabel();
	public static JLabel logo;

	public HelpFrame(){
		super("Help");
		setMinimumSize(new Dimension(325, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		getContentPane().add(pane);
		JPanel pagePane = new JPanel();
		pane.add(pagePane);
		pagePane.setBorder(BorderFactory.createLoweredBevelBorder());
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		SpringLayout helpPageLayout = new SpringLayout();
		pagePane.setLayout(helpPageLayout);
		pane.add(logo);

		backButton = new JButton("<");
		backButton.setSize(new Dimension(10, 10));
		pane.add(backButton);
		backButton.addActionListener(new PageTurnButtonActionListener());
		backButton.setFocusable(false);

		forwardButton = new JButton(">");
		forwardButton.setSize(10, 10);
		pane.add(forwardButton);
		forwardButton.addActionListener(new PageTurnButtonActionListener());
		forwardButton.setFocusable(false);

		pane.add(pageNumber);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, logo, 0, SpringLayout.HORIZONTAL_CENTER, pane);

		layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, pane);
		layout.putConstraint(SpringLayout.EAST, backButton, 60, SpringLayout.WEST, pane);

		layout.putConstraint(SpringLayout.EAST, forwardButton, -10, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.SOUTH, forwardButton, -10, SpringLayout.SOUTH, pane);
		layout.putConstraint(SpringLayout.WEST, forwardButton, -60, SpringLayout.EAST, pane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pagePane, 0, SpringLayout.HORIZONTAL_CENTER, pane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, pagePane, 40, SpringLayout.VERTICAL_CENTER, pane);
		layout.putConstraint(SpringLayout.SOUTH, pagePane, -5, SpringLayout.NORTH, forwardButton);
		layout.putConstraint(SpringLayout.WEST, pagePane, 16, SpringLayout.WEST, pane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pageNumber, 0, SpringLayout.HORIZONTAL_CENTER, pane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, pageNumber, -2, SpringLayout.VERTICAL_CENTER, backButton);

		helpPages = new HelpPages(helpPageLayout, pagePane);

		setLocationRelativeTo(null);
		setResizable(false);

	}

	interface HelpPage{
		void show();
		void close();
	}

	class HelpPages{

		HelpPage helpPage1, helpPage2, helpPage3, helpPage4, activePage;

		public HelpPages(SpringLayout layout, JPanel pane){
			helpPage1 = new HelpPage1(layout, pane);
			helpPage2 = new HelpPage2(layout, pane);
			helpPage3 = new HelpPage3(layout, pane);
			helpPage4 = new HelpPage4(layout, pane);
			helpPage1.show();
		}

		public void pageBack(){
			activePage.close();
			if(activePage.equals(helpPage2)){
				helpPage1.show();
			}
			else if(activePage.equals(helpPage3)){
				helpPage2.show();
			}
			else if(activePage.equals(helpPage4)){
				helpPage3.show();
				forwardButton.setEnabled(true);
			}
		}

		public void pageForward(){
			activePage.close();
			if(activePage.equals(helpPage1)){
				helpPage2.show();
				backButton.setEnabled(true);
			}
			else if(activePage.equals(helpPage2)){
				helpPage3.show();
			}
			else if(activePage.equals(helpPage3)){
				helpPage4.show();
				forwardButton.setEnabled(false);
			}
		}

		void close(){
		}

		class HelpPage1 implements HelpPage{
			JLabel title, version, info, info2;

			public HelpPage1(SpringLayout layout, JPanel pane){
				title = new JLabel("Dragon Dollars Calculator");
				version = new JLabel("Version 1.0");
				info = new JLabel("Created by Jack Cousineau");
				info2 = new JLabel("for Ms. Black's 3rd-grade class");

				pane.add(title);
				pane.add(version);
				pane.add(info);
				pane.add(info2);

				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, pane);
				layout.putConstraint(SpringLayout.NORTH, title, 9, SpringLayout.NORTH, pane);

				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, version, 0, SpringLayout.HORIZONTAL_CENTER, pane);
				layout.putConstraint(SpringLayout.NORTH, version, 29, SpringLayout.NORTH, pane);

				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, info, 0, SpringLayout.HORIZONTAL_CENTER, pane);
				layout.putConstraint(SpringLayout.NORTH, info, 59, SpringLayout.NORTH, pane);

				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, info2, 0, SpringLayout.HORIZONTAL_CENTER, pane);
				layout.putConstraint(SpringLayout.NORTH, info2, 79, SpringLayout.NORTH, pane);
			}

			public void show(){
				title.setVisible(true);
				version.setVisible(true);
				info.setVisible(true);
				info2.setVisible(true);
				activePage = this;
				backButton.setEnabled(false);
				HelpFrame.pageNumber.setText("Page 1/4");
			}

			public void close(){
				title.setVisible(false);
				version.setVisible(false);
				info.setVisible(false);
				info2.setVisible(false);
			}
		}

		class HelpPage2 implements HelpPage{

			JLabel info, info2, info3, info4, info5, info6;
			JButton addStudentButton, calculateButton;

			public HelpPage2(SpringLayout layout, JPanel pane){
				addStudentButton = new JButton("Add Student");
				calculateButton = new JButton("Calculate");
				info = new JLabel("Click the");
				info2 = new JLabel("button to add a");
				info3 = new JLabel("new student to the list. Input the values re-");
				info4 = new JLabel("presenting each color (color-key on page 4),");
				info5 = new JLabel("and click the");
				info6 = new JLabel("button.");

				pane.add(addStudentButton);
				addStudentButton.setFocusable(false);
				pane.add(calculateButton);
				calculateButton.setFocusable(false);
				pane.add(info);
				pane.add(info2);
				pane.add(info3);
				pane.add(info4);
				pane.add(info5);
				pane.add(info6);
				close();

				layout.putConstraint(SpringLayout.WEST, info, 7, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, info, 8, SpringLayout.NORTH, pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, addStudentButton, 2, SpringLayout.VERTICAL_CENTER, info);
				layout.putConstraint(SpringLayout.WEST, addStudentButton, 0, SpringLayout.EAST, info);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, info2, 0, SpringLayout.VERTICAL_CENTER, info);
				layout.putConstraint(SpringLayout.WEST, info2, 0, SpringLayout.EAST, addStudentButton);

				layout.putConstraint(SpringLayout.NORTH, info3, 7, SpringLayout.SOUTH, info);
				layout.putConstraint(SpringLayout.WEST, info3, 7, SpringLayout.WEST, pane);

				layout.putConstraint(SpringLayout.NORTH, info4, 7, SpringLayout.SOUTH, info3);
				layout.putConstraint(SpringLayout.WEST, info4, 7, SpringLayout.WEST, pane);

				layout.putConstraint(SpringLayout.NORTH, info5, 7, SpringLayout.SOUTH, info4);
				layout.putConstraint(SpringLayout.WEST, info5, 7, SpringLayout.WEST, pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, calculateButton, 2, SpringLayout.VERTICAL_CENTER, info5);
				layout.putConstraint(SpringLayout.WEST, calculateButton, 0, SpringLayout.EAST, info5);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, info6, -2, SpringLayout.VERTICAL_CENTER, calculateButton);
				layout.putConstraint(SpringLayout.WEST, info6, 0, SpringLayout.EAST, calculateButton);
			}

			public void show(){
				addStudentButton.setVisible(true);
				calculateButton.setVisible(true);
				info.setVisible(true);
				info2.setVisible(true);
				info3.setVisible(true);
				info4.setVisible(true);
				info5.setVisible(true);
				info6.setVisible(true);
				activePage = this;
				HelpFrame.pageNumber.setText("Page 2/4");
			}

			public void close(){
				addStudentButton.setVisible(false);
				calculateButton.setVisible(false);
				info.setVisible(false);
				info2.setVisible(false);
				info3.setVisible(false);
				info4.setVisible(false);
				info5.setVisible(false);
				info6.setVisible(false);
			}
		}

		class HelpPage3 implements HelpPage{

			JPanel helpPage3Pane = new JPanel();
			JLabel info, info2, info3, info4;

			public HelpPage3(SpringLayout parentLayout, JPanel pane){
				pane.add(helpPage3Pane);
				SpringLayout layout = new SpringLayout();
				helpPage3Pane.setLayout(layout);
				//info = new JLabel("This is a lot of text, I know, that's why I'm");
				info = new JLabel("When entering data, nothing is required ex-");
				info2 = new JLabel("cept for the characters themselves, and");
				info3 = new JLabel("case does not matter -- i.e. \"Y+G+B+P\"");
				info4 = new JLabel("will return the same result as \"ygbp\".");
				helpPage3Pane.add(info);
				helpPage3Pane.add(info2);
				helpPage3Pane.add(info3);
				helpPage3Pane.add(info4);
				close();

				parentLayout.putConstraint(SpringLayout.NORTH, helpPage3Pane, 0, SpringLayout.NORTH, pane);
				parentLayout.putConstraint(SpringLayout.WEST, helpPage3Pane, 0, SpringLayout.WEST, pane);
				parentLayout.putConstraint(SpringLayout.SOUTH, helpPage3Pane, 0, SpringLayout.SOUTH, pane);
				parentLayout.putConstraint(SpringLayout.EAST, helpPage3Pane, 0, SpringLayout.EAST, pane);

				layout.putConstraint(SpringLayout.WEST, info, 7, SpringLayout.WEST, helpPage3Pane);
				layout.putConstraint(SpringLayout.NORTH, info, 8, SpringLayout.NORTH, helpPage3Pane);
				
				layout.putConstraint(SpringLayout.WEST, info2, 7, SpringLayout.WEST, helpPage3Pane);
				layout.putConstraint(SpringLayout.NORTH, info2, 8, SpringLayout.SOUTH, info);
				
				layout.putConstraint(SpringLayout.WEST, info3, 7, SpringLayout.WEST, helpPage3Pane);
				layout.putConstraint(SpringLayout.NORTH, info3, 8, SpringLayout.SOUTH, info2);
				
				layout.putConstraint(SpringLayout.WEST, info4, 7, SpringLayout.WEST, helpPage3Pane);
				layout.putConstraint(SpringLayout.NORTH, info4, 8, SpringLayout.SOUTH, info3);
			}

			public void show(){
				helpPage3Pane.setVisible(true);
				activePage = this;
				HelpFrame.pageNumber.setText("Page 3/4");
			}

			public void close(){
				helpPage3Pane.setVisible(false);
			}
		}

		class HelpPage4 implements HelpPage{

			class HelpPage4Pane extends JPanel{
				@Override
				protected void paintComponent(Graphics g){
					super.paintComponent(g);
					g.drawLine(144, 10, 144, 95);
				}
			}

			HelpPage4Pane helpPage4Pane = new HelpPage4Pane();
			JPanel purpleColorBox, blueColorBox, greenColorBox, yellowColorBox, orangeColorBox, redColorBox;
			JLabel info, purpleInfo, blueInfo, greenInfo, yellowInfo, orangeEqualsSign, orangeInfo, redInfo;

			public HelpPage4(SpringLayout parentLayout, JPanel pane){
				pane.add(helpPage4Pane);
				SpringLayout layout = new SpringLayout();
				helpPage4Pane.setLayout(layout);
				purpleColorBox = new JPanel();
				purpleInfo = new JLabel("= P = $2");
				blueColorBox = new JPanel();
				blueInfo = new JLabel("= B = $1");
				greenColorBox = new JPanel();
				greenInfo = new JLabel("= G = $0");
				yellowColorBox = new JPanel();
				yellowInfo = new JLabel("= Y = $-1");
				orangeColorBox = new JPanel();
				orangeEqualsSign = new JLabel("=");
				orangeInfo = new JLabel("O = $-2");
				redColorBox = new JPanel();
				redInfo = new JLabel("= R = $-3");

				purpleColorBox.setBackground(new Color(144, 0, 196));
				blueColorBox.setBackground(new Color(47, 134, 247));
				greenColorBox.setBackground(new Color(105, 255, 117));
				yellowColorBox.setBackground(new Color(255, 251, 0));
				orangeColorBox.setBackground(new Color(255, 127, 0));
				redColorBox.setBackground(new Color(255, 0, 0));

				helpPage4Pane.add(purpleColorBox);
				helpPage4Pane.add(purpleInfo);
				helpPage4Pane.add(blueColorBox);
				helpPage4Pane.add(blueInfo);
				helpPage4Pane.add(greenColorBox);
				helpPage4Pane.add(greenInfo);
				helpPage4Pane.add(yellowColorBox);
				helpPage4Pane.add(yellowInfo);
				helpPage4Pane.add(orangeColorBox);
				helpPage4Pane.add(orangeEqualsSign);
				helpPage4Pane.add(orangeInfo);
				helpPage4Pane.add(redColorBox);
				helpPage4Pane.add(redInfo);

				parentLayout.putConstraint(SpringLayout.NORTH, helpPage4Pane, 0, SpringLayout.NORTH, pane);
				parentLayout.putConstraint(SpringLayout.WEST, helpPage4Pane, 0, SpringLayout.WEST, pane);
				parentLayout.putConstraint(SpringLayout.SOUTH, helpPage4Pane, 0, SpringLayout.SOUTH, pane);
				parentLayout.putConstraint(SpringLayout.EAST, helpPage4Pane, 0, SpringLayout.EAST, pane);

				layout.putConstraint(SpringLayout.SOUTH, purpleColorBox, -15, SpringLayout.NORTH, blueColorBox);
				layout.putConstraint(SpringLayout.WEST, purpleColorBox, 25, SpringLayout.WEST, helpPage4Pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, purpleInfo, -1, SpringLayout.VERTICAL_CENTER, purpleColorBox);
				layout.putConstraint(SpringLayout.WEST, purpleInfo, 5, SpringLayout.EAST, purpleColorBox);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, blueColorBox, 1, SpringLayout.VERTICAL_CENTER, helpPage4Pane);
				layout.putConstraint(SpringLayout.WEST, blueColorBox, 25, SpringLayout.WEST, helpPage4Pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, blueInfo, -1, SpringLayout.VERTICAL_CENTER, blueColorBox);
				layout.putConstraint(SpringLayout.WEST, blueInfo, 5, SpringLayout.EAST, blueColorBox);

				layout.putConstraint(SpringLayout.NORTH, greenColorBox, 15, SpringLayout.SOUTH, blueColorBox);
				layout.putConstraint(SpringLayout.WEST, greenColorBox, 25, SpringLayout.WEST, helpPage4Pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, greenInfo, -1, SpringLayout.VERTICAL_CENTER, greenColorBox);
				layout.putConstraint(SpringLayout.WEST, greenInfo, 5, SpringLayout.EAST, greenColorBox);

				layout.putConstraint(SpringLayout.SOUTH, yellowColorBox, -15, SpringLayout.NORTH, orangeColorBox);
				layout.putConstraint(SpringLayout.EAST, yellowColorBox, -5, SpringLayout.WEST, yellowInfo);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, yellowInfo, -1, SpringLayout.VERTICAL_CENTER, yellowColorBox);
				layout.putConstraint(SpringLayout.EAST, yellowInfo, -25, SpringLayout.EAST, helpPage4Pane);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, orangeColorBox, 1, SpringLayout.VERTICAL_CENTER, helpPage4Pane);
				layout.putConstraint(SpringLayout.EAST, orangeColorBox, -5, SpringLayout.WEST, orangeEqualsSign);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, orangeEqualsSign, 0, SpringLayout.VERTICAL_CENTER, orangeInfo);
				layout.putConstraint(SpringLayout.EAST, orangeEqualsSign, -2, SpringLayout.WEST, orangeInfo);
				layout.putConstraint(SpringLayout.VERTICAL_CENTER, orangeInfo, -1, SpringLayout.VERTICAL_CENTER, blueColorBox);
				layout.putConstraint(SpringLayout.EAST, orangeInfo, -25, SpringLayout.EAST, helpPage4Pane);

				layout.putConstraint(SpringLayout.NORTH, redColorBox, 15, SpringLayout.SOUTH, orangeColorBox);
				layout.putConstraint(SpringLayout.EAST, redColorBox, -5, SpringLayout.WEST, redInfo);

				layout.putConstraint(SpringLayout.VERTICAL_CENTER, redInfo, -1, SpringLayout.VERTICAL_CENTER, redColorBox);
				layout.putConstraint(SpringLayout.EAST, redInfo, -25, SpringLayout.EAST, helpPage4Pane);

				close();
			}

			public void show(){
				helpPage4Pane.setVisible(true);
				activePage = this;
				HelpFrame.pageNumber.setText("Page 4/4");
			}

			public void close(){
				helpPage4Pane.setVisible(false);
			}
		}
	}

	static class PageTurnButtonActionListener implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent event){
			Object source = event.getSource();
			if(source == HelpFrame.backButton){
				HelpFrame.helpPages.pageBack();
			}
			else if(source == HelpFrame.forwardButton){
				HelpFrame.helpPages.pageForward();
			}
		}
	}

}
