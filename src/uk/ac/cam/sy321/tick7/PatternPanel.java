package uk.ac.cam.sy321.tick7;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class PatternPanel extends JPanel {
	private static final long serialVersionUID = 1920992687273792229L;

	private JList<Object> guiList;
	private Pattern currentPattern;
	private List<Pattern> patternList;

	public Pattern getCurrentPattern() {
		return currentPattern;
	}

	public PatternPanel() {
		super();
		currentPattern=null;
		setLayout(new BorderLayout());
		guiList = new JList<Object>();
		guiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && (patternList != null)) {
					int sel = guiList.getSelectedIndex();
					if (sel != -1) {
						currentPattern = patternList.get(sel);
						onPatternChange();
					}
				}
			}
		});
		add(new JScrollPane(guiList));

	}
	abstract void onPatternChange();

	public void setPatterns(List<Pattern> list) {
		patternList = list;
		if (list == null) {
			currentPattern = null; //if list is null, then no valid pattern
			guiList.setListData(new String[]{}); //no list item to select
			return;
		}
		ArrayList<String> names = new ArrayList<String>();
		for(Pattern i : list){
			names.add(i.getName()+" ("+i.getAuthor()+")");
		}
		guiList.setListData(names.toArray());
		if (names.size()==0){currentPattern=null; return;}
		currentPattern = list.get(0); //select first element in list
		guiList.setSelectedIndex(0); //select first element in guiList
	} 
}

