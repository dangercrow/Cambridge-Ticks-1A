package uk.ac.cam.sy321.tick5;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;

public class RefactorLife {
	public static void main(String[] args) { 
		String worldType = new String("");
		String resourceLoc=new String("");
		Integer index=null;
		List<Pattern> patternList = new LinkedList<Pattern>();
		try{
			switch (args.length){
			case 1: resourceLoc = args[0]; break;
			case 2: worldType ="--array"; resourceLoc = args[0]; index = Integer.parseInt(args[1]); break;
			case 3: worldType = args[0]; resourceLoc = args[1]; index = Integer.parseInt(args[2]); break;
			default: throw new Exception("Incorrect number of arguments, expected\n [--array | --long]  (URL | FILE) [INDEX]");
			}
			try{
				patternList=resourceLoc.startsWith("http://")
						?PatternLoader.loadFromURL(resourceLoc)
								:PatternLoader.loadFromDisk(resourceLoc);
						if(index!=null){
							if (index < 0 || patternList.size()<=index){
								throw new NumberFormatException("Pattern number isn't in pattern list");}
							else{
								Pattern p = patternList.get(index);
								World world = null;
								switch (worldType){
								case "--array": {world = new ArrayWorld(p.getWidth(),p.getHeight()); break;}
								case "--long" : {world = new PackedWorld();                          break;}
								case "--aging": {world = new AgingWorld(p.getWidth(),p.getHeight()); break;}
								default:
									System.out.println("Incorrect number of arguments, expected\n [--array | --long | --aging]  (URL | FILE) [INDEX]");
									return; 
								}
								p.initialise(world);
								play(world);
							}
						}
						else{
							for (int i = 0; i<patternList.size();i++){
								Pattern p=patternList.get(i);
								System.out.println(i+") "+p.format());
							}
						}
			} catch (IOException e) {System.out.println("Error in stream input (file or URL):\n"+resourceLoc);}
		} catch (NumberFormatException e){System.out.println("Error"+args.length+": "+e.getMessage());}
		catch (Exception e){System.out.println(e.getMessage());}

	}
	public static void play(World world) throws IOException {
		int userResponse = 10;
		WorldViewer viewer = new WorldViewer();
		Writer writer = new OutputStreamWriter(System.out);
		while (userResponse != 'q') {
			if (userResponse == 10){
				world.print(writer);
				viewer.show(world);
				world=world.nextGeneration(0);
			}
			userResponse = System.in.read();
		}
		viewer.close();
	}
}
