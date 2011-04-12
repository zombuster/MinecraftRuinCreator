package data;

import java.util.Map;
import java.util.TreeMap;

public class BlockNames {

	public static String[] names = new String[350];

	public static void initNames() {
		names[0] = "air";
		names[1] = "stone";
		names[2] = "grass";
		names[3] = "dirt";
		names[4] = "cobblestone";
		names[5] = "planks";
		names[6] = "sapling";
		names[7] = "bedrock";
		names[8] = "water";
		names[9] = "water";
		names[10] = "lava";
		names[11] = "lava";
		names[12] = "sand";
		names[13] = "gravel";
		names[14] = "gold ore";
		names[15] = "iron ore";
		names[16] = "coal ore";
		names[17] = "wood";
		names[18] = "leaves";
		names[19] = "sponge";
		names[20] = "glass";
		names[21] = "lapis lazuli ore";
		names[22] = "lapis lazuli block";
		names[23] = "dispenser";
		names[24] = "sandstone";
		names[25] = "note block";
		names[26] = "bed";
		names[35] = "wool";
		names[37] = "yellow flower";
		names[38] = "Red Rose";
		names[39] = "Brown Mushroom";
		names[40] = "Red Mushroom ";
		names[41] = "Gold Block";
		names[42] = "Iron Block";
		names[43] = "Double Slab";
		names[44] = "Slab";
		names[45] = "Brick Block";
		names[46] = "TNT";
		names[47] = "Bookshelf";
		names[48] = "Moss Stone";
		names[49] = "Obsidian";
		names[50] = "Torch";
		names[51] = "Fire";
		names[52] = "Monster Spawner";
		names[53] = "Wooden Stairs";
		names[54] = "Chest";
		names[55] = "Redstone Wire";
		names[56] = "Diamond Ore";
		names[57] = "Diamond Block";
		names[58] = "Crafting Table";
		names[59] = "Crops";
		names[60] = "Farmland";
		names[61] = "Furnace";
		names[62] = "Burning Furnace";
		names[63] = "Sign Post";
		names[64] = "Wooden Door ";
		names[65] = "Ladder ";
		names[66] = "Rails";
		names[67] = "Cobblestone Stairs";
		names[68] = "Wall Sign";
		names[69] = "Lever";
		names[70] = "Stone Pressure Plate";
		names[71] = "Iron Door";
		names[72] = "Wooden Pressure Plate";
		names[73] = "Redstone Ore";
		names[74] = "Glowing Redstone Ore ";
		names[75] = "Redstone Torch";
		names[76] = "Redstone Torch";
		names[77] = "Stone Button ";
		names[78] = "Snow";
		names[79] = "Ice";
		names[80] = "Snow Block";
		names[81] = "Cactus";
		names[82] = "Clay Block";
		names[83] = "Sugar Cane";
		names[84] = "Jukebox";
		names[85] = "Fence";
		names[86] = "Pumpkin";
		names[87] = "Netherrack";
		names[88] = "Soul Sand";
		names[89] = "Glowstone Block";
		names[90] = "Portal";
		names[91] = "Jack-O-Lantern";
		names[92] = "Cake Block";
		names[93] = "Redstone Repeater(off)";
		names[94] = "Redstone Repeater(on)";
	
		names[300] = "original";
		names[301] = "zombie spawner";
		names[302] = "skeleton spawner";
		names[303] = "spider spawner";
		names[304] = "creeper spawner";
		names[305] = "z/s/c spawner";
		names[306] = "z/s spawner";
		names[307] = "z/s/sp spawner";
		names[308] = "all spawner";
		names[309] = "easy chest";
		names[310] = "medium chest";
		names[311] = "hard chest";
		
	}
	
	public static int getIndex(String blockname){
		for(int i = 0;i < names.length;i++){
			if(names[i] != null){
				if(names[i].equals(blockname))
					return i;
			}
		}
		return 0;
	}
	
}
