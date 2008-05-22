import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TestEnum {

	// definition des couleurs
	enum CouleurS {ROUGE, VERT, BLEU};
	
	// couleur plus plus
	enum CouleurP
	{
		NONE(-1,"-"),
		JAUNE(1, "j"),
		VIOLET(9, "v");
		
		private static final Map<Integer,CouleurP> lookup = new HashMap<Integer,CouleurP>();

		static {	
			for(CouleurP c : EnumSet.allOf(CouleurP.class))
				lookup.put(c.getCode(), c);
		}	
		
		private int code;
		private String display;
		
		private CouleurP(int code, String disp) 
		{
			this.code = code;
			this.display = disp;
		}

		public int getCode() { return code; }
		public String displayStr() { return display; }

		public static CouleurP get(int code) 
		{ 
			return lookup.get(code); 
		}
		public static CouleurP aliasOf(String str)
		{
			if (str == null)
				throw new NullPointerException("Name is null");
			for(CouleurP c : CouleurP.values() ) {
				if( c.display.equals(str)) return c;
			}
			throw new IllegalArgumentException("No enum const CouleurP aliased " + str);
		}
   }
	
	public void listCouleurP()
	{
		System.out.println( "-- listCouleurP()");
		for (CouleurP coul : CouleurP.values()) {
		    System.out.println(coul + " => " + coul.displayStr());
		}
	}
	
	public void scanCouleurP()
	{
		//... Initialization
        String name;               // Declare a variable to hold the name.
        Scanner in = new Scanner(System.in);
        
        name = "s";
        
        while( name.equals("") == false ) {
        	//... Prompt and read input.
        	System.out.print("Quelle CouleurP ? ");
        	name = in.nextLine();      // Read one line from the console.
        	if( name.equals("") == false ) {
        		try {
        			CouleurP truc = CouleurP.valueOf(name);
        			System.out.println( "enum = "+ truc.displayStr());
        		}
        		catch(IllegalArgumentException e) {
        			System.err.println(name + " n'est pas une CouleurP\n");
        		}
        	}
        }
        in.close();
        System.out.println("scanCouleurP est fini");
	}
	public void aliasCouleurP()
	{
		//... Initialization
        String name;               // Declare a variable to hold the name.
        Scanner in = new Scanner(System.in);
        
        name = "s";
        
        while( name.equals("stop") == false ) {
        	//... Prompt and read input.
        	System.out.print("Quelle CouleurP ? ");
        		name = in.nextLine();      // Read one line from the console.
        		if( name.equals("") == false ) {
        			try {
        				CouleurP truc = CouleurP.aliasOf(name);
        				System.out.println( "enum = "+ truc.displayStr());
        			}
        			catch(IllegalArgumentException e) {
        				System.err.println(name + " n'est pas une CouleurP\n");
        			}
        		}	
        	
        }
        in.close();
	}
	
	public void listCouleurS()
	{
		System.out.println( "-- listCouleurS()");
		for (CouleurS coul : CouleurS.values()) {
		    System.out.println(coul);
		}
	}
	
	public void scanCouleurS()
	{
		//... Initialization
        String name;               // Declare a variable to hold the name.
        Scanner in = new Scanner(System.in);
        
        name = "s";
        
        while( name.equals("") == false ) {
        	//... Prompt and read input.
        	System.out.print("Quelle CouleurS ? ");
        	name = in.nextLine();      // Read one line from the console.
        	if( name.equals("") == false ) {
        		try {
        			CouleurS truc = CouleurS.valueOf(name);
        			System.out.println( "enum = "+ truc);
        		}
        		catch(IllegalArgumentException e) {
        			System.err.println(name + " n'est pas une CouleurS\n");
        		}
        	}
        }
        in.close();
	}
	
	public void run()
	{
		//listCouleurS();
		//scanCouleurS();
		listCouleurP();
		//scanCouleurP();
		aliasCouleurP();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TestEnum test = new TestEnum();
		test.run();

	}

}
