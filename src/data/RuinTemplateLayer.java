package data;
import java.util.ArrayList;
import java.util.Iterator;

public class RuinTemplateLayer {
    int[][] layer;

    public RuinTemplateLayer(int[][] layerdata ){
    	layer = layerdata;
    }
    
    public RuinTemplateLayer( ArrayList<String> layerdata, int width, int length ) throws Exception {
        layer = new int[width][length];
        Iterator<String> i = layerdata.iterator();
        String row;
        String[] rowdata;
        for( int x = 0; x < width; x++ ) {
            row = i.next();
            rowdata = row.split( "," );
            for( int z = 0; z < length; z++ ) {
				// ruins are flipped when read in, hence this crazy hack.
                layer[x][z] = Integer.parseInt( rowdata[length - z - 1] );
            }
        }
    }

    public void setRuleAt( int x, int z ,int rule ) {
        try {
            layer[x][z] = rule;
        } catch( Exception e ) {
            System.err.println( e.getMessage() );
            System.err.println( "Attempting to set rule at " + x + ", " + z + "." );
        }
    }
    
    public int getRuleAt( int x, int z ) {
        try {
            return layer[x][z];
        } catch( Exception e ) {
            System.err.println( e.getMessage() );
            System.err.println( "Attempting to get rule at " + x + ", " + z + "." );
            return 0;
        }
    }
}