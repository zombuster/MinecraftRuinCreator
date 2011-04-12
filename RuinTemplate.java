package net.minecraft.src;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;

public class RuinTemplate {
    private int[] targets;
    protected int height = 0, width = 0, length = 0, overhang = 0, weight = 0, embed = 1;
    private int w_off = 0, l_off = 0;
    protected boolean loaded = false;
    private ArrayList<RuinTemplateRule> rules = new ArrayList<RuinTemplateRule>();
    private ArrayList<RuinTemplateLayer> layers = new ArrayList<RuinTemplateLayer>();

    public RuinTemplate( String filename ) throws Exception {
        // load in the given file as a template
        try {
            ArrayList<String> lines = new ArrayList<String>();
            BufferedReader br = new BufferedReader( new FileReader( filename ) );
            String read = br.readLine();
            while( read != null ) {
                lines.add( read );
                read = br.readLine();
            }
            parseFile( lines );
            loaded = true;
        } catch ( Exception e ) {
            System.err.println( "Failed loading template: " + filename );
            e.printStackTrace();
            loaded = false;
        }
    }

    public boolean isAcceptable( World world, int x, int y, int z ) {
        // checks if the square is acceptable for a ruin to be built upon
        for( int i = 0; i < targets.length; i++ ) {
            if( world.getBlockId( x, y, z ) == targets[i] ) { return true; }
        }
        return false;
    }

    public void placeRuin( World world, Random random, int xBase, int y, int zBase ) {
        // we need to shift the base coordinates before we can begin creating
        // the layers.
        int x = xBase + w_off;
        int z = zBase + l_off;
        int y_off = 1 - embed;
        int rulenum = 0;
        Iterator<RuinTemplateLayer> i = layers.iterator();
        RuinTemplateLayer curlayer;
		RuinTemplateRule curRule;
        while( i.hasNext() ) {
            curlayer = i.next();
			ArrayList<RuleProcess> laterun = new ArrayList<RuleProcess>();
            for( int x1 = 0; x1 < width; x1++ ) {
                for( int z1 = 0; z1 < length; z1++ ) {
                    rulenum = curlayer.getRuleAt( x1, z1 );
					curRule = rules.get( rulenum );
					if( curRule.runLater() ) {
						laterun.add( new RuleProcess( curRule, x + x1, y + y_off, z + z1 ) );
					} else {
						curRule.doBlock( world, random, x + x1, y + y_off, z + z1 );
					}
                }
            }
			// now do the late runs in this layer.
			Iterator<RuleProcess> rp = laterun.iterator();
			while( rp.hasNext() ) {
				rp.next().doBlock( world, random );
			}
			// we're done with this layer
            y_off++;
        }
		world.markBlocksDirty( xBase, y + 1 - embed, zBase, xBase + width, y + ( 1 - embed ) + height, zBase + length );
    }

    private void parseFile( ArrayList<String> lines ) throws Exception {
        // first get the variables.
        parseVariables( lines );

        // the first rule added will always be the air block rule.
        rules.add( new RuinTemplateRule( "0,100,0" ) );

        // now get the rest of the data
        Iterator<String> i = lines.iterator();
        String line;
        while( i.hasNext() ) {
            line = i.next();
            if( ! line.startsWith( "#" ) ) {
                if( line.startsWith( "layer" ) ) {
                    // add in data until we reach the end of the layer
                    ArrayList<String> layerlines = new ArrayList<String>();
                    line = i.next();
                    while( ! line.startsWith( "endlayer" ) ) {
                        if( line.charAt( 0 ) != '#' ) {
                            layerlines.add( line );
                        }
                        line = i.next();
                    }
                    layers.add( new RuinTemplateLayer( layerlines, width, length ) );
                } else if( line.startsWith( "rule" ) ) {
                    String[] parts = line.split( "=" );
                    rules.add( new RuinTemplateRule( parts[1] ) );
                }
            }
        }
    }

    private void parseVariables( ArrayList<String> variables ) throws Exception {
        Iterator<String> i = variables.iterator();
        String line;
        while( i.hasNext() ) {
            line = i.next();
            if( ! line.startsWith( "#" ) ) {
                if( line.startsWith( "acceptable_target_blocks" ) ) {
                    String[] check = line.split( "=" );
                    check = check[1].split( "," );
                    if( check.length < 1 ) {
                        throw new Exception( "No targets specified!" );
                    }
                    targets = new int[check.length];
                    for( int x = 0; x < check.length; x++ ) {
                        targets[x] = Integer.parseInt( check[x] );
                    }
                }
                if( line.startsWith( "dimensions" ) ) {
                    String[] check = line.split( "=" );
                    check = check[1].split( "," );
                    height = Integer.parseInt( check[0] );
                    width = Integer.parseInt( check[1] );
                    length = Integer.parseInt( check[2] );
                }
                if( line.startsWith( "weight" ) ) {
                    String[] check = line.split( "=" );
                    weight = Integer.parseInt( check[1] );
                }
                if( line.startsWith( "embed_into_distance" ) ) {
                    String[] check = line.split( "=" );
                    embed = Integer.parseInt( check[1] );
                }
                if( line.startsWith( "allowable_overhang" ) ) {
                    String[] check = line.split( "=" );
                    overhang = Integer.parseInt( check[1] );
                }
            }
        }
        if( width % 2 == 1 ) {
            w_off = 0 - ( width - 1 ) / 2;
        } else {
            w_off = 0 - width / 2;
        }
        if( length % 2 == 1 ) {
            l_off = 0 - ( length - 1 ) / 2;
        } else {
            l_off = 0 - length / 2;
        }
    }
}