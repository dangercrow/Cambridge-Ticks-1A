package uk.ac.cam.sy321.tick6star;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PatternPanel extends JPanel {
	private static final long serialVersionUID = 1920992687273792229L;

	private JList<Object> guiList;

	public PatternPanel() {
		super();
		setLayout(new BorderLayout());
		guiList = new JList<Object>();
		add(new JScrollPane(guiList));
	}
	public void setPatterns(List<Pattern> list) {
		ArrayList<String> names = new ArrayList<String>();
		for(Pattern i : list){
			names.add(i.getName()+" ("+i.getAuthor()+")");
		}
		guiList.setListData(names.toArray());
	} 
}

