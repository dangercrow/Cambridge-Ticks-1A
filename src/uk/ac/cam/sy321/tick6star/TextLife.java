package uk.ac.cam.sy321.tick6star;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import uk.ac.cam.acr31.life.World;

public class TextLife {
	public static void main(String[] args) {
		try {
			CommandLineOptions c = new CommandLineOptions(args);
			List<Pattern> list;
			if (c.getSource().startsWith("http://")){
				list = PatternLoader.loadFromURL(c.getSource());
			}
			else{
				list = PatternLoader.loadFromDisk(c.getSource());
			}
			if (c.getIndex() == null) {
				int i = 0;
				for (Pattern p : list)
					System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
			} else {
				Pattern p = list.get(c.getIndex());
				World w = null;
				if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_AGING)) {
					w = new AgingWorld(p.getWidth(), p.getHeight());
				} else if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_ARRAY)) {
					w = new ArrayWorld(p.getWidth(), p.getHeight());
				} else {
					w = new PackedWorld();
				}
				p.initialise(w);
				int userResponse = 10;
				OutputStreamWriter writer = new OutputStreamWriter(System.out);
				while (userResponse != 'q') {
					if (userResponse == 10){
						w.print(writer);
						w = w.nextGeneration(0);
					}
					try {
						userResponse = System.in.read();
					}
					catch (IOException e) {
						System.out.println(e.getMessage());
					}

				}
			}
		} 
		catch (IndexOutOfBoundsException e){
			System.out.println("Error: Index out of bounds");
		}
		catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
}

