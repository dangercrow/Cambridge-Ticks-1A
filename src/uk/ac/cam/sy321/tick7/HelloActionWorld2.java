package uk.ac.cam.sy321.tick7;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloActionWorld2 extends JFrame {
	private static final long serialVersionUID = -5847941487358040286L;

	HelloActionWorld2() {
		super("Hello Action"); //create window & set title text
		setDefaultCloseOperation(EXIT_ON_CLOSE); //close button on window quits app.
		//configure the layout of the pane associated with this window as a "BoxLayout"
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		final JLabel label;
		label = new JLabel("Button unpressed"); //create graphical text label
		add(label); //associate "label" with window
		JButton button = new JButton("Press me");//create graphical button
		add(button); //associated "button" with window
		//create an instance of an anonymous inner class to hand the event
		button.addActionListener(new ActionListener(){
			private int count = 0;
			public void actionPerformed(ActionEvent e) {
				label.setText("Button has been pressed "+ ++count + " times");
			}
		});
		setSize(320,240); //set size of window
	}
	public static void main(String[] args) {
		HelloActionWorld2 hello = new HelloActionWorld2(); //create instance
		hello.setVisible(true); //display window to user
	}
}
