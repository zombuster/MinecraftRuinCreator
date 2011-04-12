package data;
import java.util.Random;

public class RuinTemplateRule {
    private int[] blockIDs, blockMDs;
    private int chance = 100;
    private int condition = 0;

    public RuinTemplateRule(){
    	blockIDs = new int[0];
    	blockMDs = new int[0];
    }
    
	public RuinTemplateRule( String rule ) throws Exception {
        String[] items = rule.split( "," );
        int numblocks = items.length - 2;
        if( numblocks < 1 ) { throw new Exception( "No blockIDs specified for rule!" ); }
        condition = Integer.parseInt( items[0] );
        chance = Integer.parseInt( items[1] );
        blockIDs = new int[numblocks];
        blockMDs = new int[numblocks];
		String[] data;
        for( int i = 0; i < numblocks; i++ ) {
			data = items[i + 2].split( "-" );
			if( data.length > 1 ) {
				blockIDs[i] = Integer.parseInt( data[0] );
				blockMDs[i] = Integer.parseInt( data[1] );
			} else {
				blockIDs[i] = Integer.parseInt( items[i + 2] );
				blockMDs[i] = 0;
			}
        }
    }
    
    public int[] getBlockIDs() {
		return blockIDs;
	}

	public void setBlockIDs(int[] blockIDs) {
		this.blockIDs = blockIDs;
	}

	public int[] getBlockMDs() {
		return blockMDs;
	}

	public void setBlockMDs(int[] blockMDs) {
		this.blockMDs = blockMDs;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
		
	}

	public String toString(){
    	String r =  condition + "," + chance + ",";
    	for(int i = 0; i < blockIDs.length;i++){
    		r += blockIDs[i];
    		if(blockMDs[i] != 0)
    			r += "-" + blockMDs[i];
    		if(i < blockIDs.length-1)
    		r+= ",";
    	}
    	
    	return r;
    }
	
	public String getNames(){
    	String r =  condition + "," + chance + ",";
    	for(int i = 0; i < blockIDs.length;i++){
    		r += BlockNames.names[blockIDs[i]];
    		if(blockMDs[i] != 0)
    			r += ":" + blockMDs[i];
    		if(i < blockIDs.length-1)
    		r+= ",";
    	}
    	
    	return r;
    }
    
}