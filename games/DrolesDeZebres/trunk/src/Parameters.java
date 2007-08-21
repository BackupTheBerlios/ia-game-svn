/*
 * Created on Mar 7, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import jargs.gnu.CmdLineParser;

/**
 * Using 'Jargs', this class reads commandLine and gather parameters for
 * the AutoZebre program.
 * TODO : make the definition of Options and usage automatic ?
 * @author dutech
 */
public class Parameters {
    
    /// FileName of actual game
    public String gameFileName;
    
    /// Verbose Option
    public boolean verbose;
    private CmdLineParser.Option verboseOpt;
    
    /// Used to automatically display option value and exit
    private boolean param;
    private CmdLineParser.Option paramOpt;
    
    
    private CmdLineParser zeParser;
    
    /**
     * Create Parser and default value for options.
     */
    public Parameters()
    {
        zeParser = new CmdLineParser();
        init();
    }
    
    /**
     * Specify which options are read by CmdLineParser;
     */
    private void init()
    {
        verbose = false;
        verboseOpt = zeParser.addBooleanOption( 'v', "verbose");
        
        gameFileName = null;
        
        param = false;
        paramOpt = zeParser.addBooleanOption( "param");
    }
    /**
     * Print usage msg on 'err'.
     */
    private void printUsage() 
    {
        System.err.println("usage: prog [{-v,--verbose}] " + 
                "[--param]" + " [fileName.mvt]");
    }
    /**
     * Value of the parameters.
     */
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append( "Parameters\n");
        strbuf.append( "  verbose = " + verbose + "\n");
        strbuf.append( "  gameFileName = " + gameFileName + "\n");
        return strbuf.toString();
    }
    
    /**
     * Try to infer parameters from command line. Writes Parameters value and exit
     * if option '--param' is given.
     * @param args
     */
    public void check( String[] args )
    {
        try {
            zeParser.parse(args);
        }
        catch ( CmdLineParser.OptionException e ) {
            System.err.println(e.getMessage());
            printUsage();
            System.exit(2);
        }
        // Extract the values entered for the various options -- if the
        // options were not specified, the corresponding values will be
        // null.
        // TODO : default value ???
        Boolean verboseVal = (Boolean) zeParser.getOptionValue(verboseOpt);
        if( verboseVal != null ) verbose = verboseVal.booleanValue();
        Boolean paramVal = (Boolean) zeParser.getOptionValue(paramOpt);
        if( paramVal != null ) param = paramVal.booleanValue();
        
        // Remaining args
        String[] otherArgs = zeParser.getRemainingArgs();
        if (otherArgs.length > 0 ) {
           gameFileName = otherArgs[0];
        }
        
        if( param ) {
            System.out.println( toString());
            System.exit(0);
        }
    }
}
