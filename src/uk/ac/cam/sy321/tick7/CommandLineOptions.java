package uk.ac.cam.sy321.tick7;

public class CommandLineOptions {
	public static String WORLD_TYPE_LONG = "--long";
	public static String WORLD_TYPE_AGING = "--aging";
	public static String WORLD_TYPE_ARRAY = "--array";
	private String worldType = null;
	private Integer index = null;
	private String source = null;

	public CommandLineOptions(String[] args) throws CommandLineException {
		switch(args.length){
		case 1: 
			source=args[0];
			break;
		case 2:
			if (args[0].equals(WORLD_TYPE_LONG) || args[0].equals(WORLD_TYPE_AGING) || args[0].equals(WORLD_TYPE_ARRAY)){
				source = args[1];
			}
			else{
				worldType = WORLD_TYPE_ARRAY;
				source = args[0];
				index=Integer.parseInt(args[1]);
			}
			break;
		case 3:
			worldType = args[0];
			source = args[1];
			index = Integer.parseInt(args[2]);
			break;
		default:
			throw new CommandLineException("No arguments found");
		}
	}
	public String getWorldType() {return worldType;}
	public Integer getIndex() {return index;}
	public String getSource() {return source;}
}
