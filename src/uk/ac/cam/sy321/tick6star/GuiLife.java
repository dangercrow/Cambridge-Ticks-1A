package uk.ac.cam.sy321.tick6star;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import uk.ac.cam.acr31.life.World;

public class GuiLife extends JFrame {
	private static final long serialVersionUID = 7643641300044716002L;

	private PatternPanel patternPanel;
	private ControlPanel controlPanel;
	private GamePanel gamePanel;

	@SuppressWarnings("unused")
	private SourcePanel sourcePanel;

	public GuiLife() {
		super("GuiLife");
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JComponent optionsPanel = createOptionsPanel();
		add(optionsPanel, BorderLayout.WEST);
		JComponent gamePanel = createGamePanel();
		add(gamePanel, BorderLayout.CENTER);
	}
	private JComponent createOptionsPanel() {
		Box result = Box.createVerticalBox();
		result.add(createSourcePanel());
		result.add(createPatternPanel());
		result.add(createControlPanel());
		return result;
	}
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);
	}
	private JComponent createGamePanel() {
		JPanel holder = new JPanel();
		addBorder(holder,Strings.PANEL_GAMEVIEW);
		GamePanel result = new GamePanel();
		gamePanel = result;
		holder.add(result);
		return new JScrollPane(holder);
	}
	private JComponent createSourcePanel() {
		SourcePanel result = new SourcePanel();
		addBorder(result,Strings.PANEL_SOURCE);
		sourcePanel = result;
		return result; 
	}
	private JComponent createPatternPanel() {
		PatternPanel result = new PatternPanel();
		addBorder(result,Strings.PANEL_PATTERN);
		patternPanel = result;
		return result; 
	}
	private JComponent createControlPanel() {
		ControlPanel result = new ControlPanel();
		addBorder(result,Strings.PANEL_CONTROL);
		controlPanel=result;
		return result; 
	}
	public static void main(String[] args) {
		GuiLife gui = new GuiLife();
		try {
			CommandLineOptions c = new CommandLineOptions(args);
			String url=c.getSource();
			List<Pattern> list = PatternLoader.loadFromURL(url);
			gui.patternPanel.setPatterns(list);
			if (c.getIndex()!=null){
				World w = gui.controlPanel.initialiseWorld(list.get(c.getIndex()));//Use list.get(i) to get pattern i
				gui.gamePanel.display(w);
			}
		} 
		catch (IOException ioe) {} 
		catch (PatternFormatException e) {
			System.out.println("Error: "+e.getMessage());
		} catch (CommandLineException e) {
			System.out.println("Error: "+e.getMessage());
		}
		gui.setVisible(true);
	}
}