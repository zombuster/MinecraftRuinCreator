package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class RuinTemplate extends Observable {

	protected int height = 0, width = 0, length = 0, overhang = 0, weight = 0, embed = 1;
	protected boolean loaded = false;
	private ArrayList<RuinTemplateRule> rules = new ArrayList<RuinTemplateRule>();
	private ArrayList<RuinTemplateLayer> layers = new ArrayList<RuinTemplateLayer>();
	private int w_off = 0, l_off = 0;
	private int[] targets;
	String output = "";

	// create empty new
	public RuinTemplate(int width, int length, int height) {
		clear(width, length, height);

	}

	public void clear(int width, int length, int height) {
		overhang = 0;
		weight = 0;
		embed = 0;
		targets = new int[0];
		this.height = height;
		this.width = width;
		this.length = length;
		layers.clear();
		rules.clear();
		try {
			rules.add(new RuinTemplateRule("0,100,0"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int y = 0; y < height; y++) {
			int[][] layer = new int[width][length];
			for (int x = 0; x < width; x++) {
				for (int z = 0; z < length; z++) {
					layer[x][z] = 0;
				}
			}
			layers.add(new RuinTemplateLayer(layer));
		}
	}

	public int getOverhang() {
		return overhang;
	}

	public void setOverhang(int overhang) {
		this.overhang = overhang;
	}

	// load from file
	public RuinTemplate(String filename) throws Exception {

		load(filename);
	}

	public void load(String filename) throws Exception {
		rules.clear();
		layers.clear();

		// load in the given file as a template
		try {
			ArrayList<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String read = br.readLine();
			while (read != null) {
				lines.add(read);
				read = br.readLine();
			}
			parseFile(lines);
			loaded = true;
		} catch (Exception e) {
			System.err.println("Failed loading template: " + filename);
			e.printStackTrace();
			loaded = false;
		}
	}

	public int[] getBlockIDs(int rule) {
		return rules.get(rule).getBlockIDs();
	}

	public void setBlockIDs(int[] blockIDs, int rule) {
		rules.get(rule).setBlockIDs(blockIDs);
		// setChanged();
		// notifyObservers();
	}

	public int[] getBlockMDs(int rule) {
		return rules.get(rule).getBlockMDs();
	}

	public void setBlockMDs(int rule, int[] blockMDs) {
		rules.get(rule).setBlockMDs(blockMDs);
		// setChanged();
		// notifyObservers();
	}

	public int getChance(int rule) {
		return rules.get(rule).getChance();
	}

	public void setChance(int rule, int chance) {
		rules.get(rule).setChance(chance);
		setChanged();
		notifyObservers();
	}

	public int getCondition(int rule) {
		return rules.get(rule).getCondition();
	}

	public void setCondition(int rule, int condition) {
		rules.get(rule).setCondition(condition);
		setChanged();
		notifyObservers();
	}

	public void setRules(ArrayList<RuinTemplateRule> rules) {
		this.rules = rules;
		setChanged();
		notifyObservers();
	}

	public int getData(int x, int y, int z) {
		if (x < 0 || x > width)
			return 0;
		if (y < 0 || y > height)
			return 0;
		if (z < 0 || z > length)
			return 0;
		return layers.get(y).getRuleAt(x, z);
	}

	public void setData(int x, int y, int z, int d) {
		if (x < 0 || x > width)
			return;
		if (y < 0 || y > height)
			return;
		if (z < 0 || z > length)
			return;
		layers.get(y).setRuleAt(x, z, d);
		setChanged();
		notifyObservers();
	}

	public ArrayList<RuinTemplateRule> getRules() {
		return rules;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		setChanged();
		notifyObservers();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		setChanged();
		notifyObservers();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
		setChanged();
		notifyObservers();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
		setChanged();
		notifyObservers();
	}

	public int getEmbed() {
		return embed;
	}

	public void setEmbed(int embed) {
		this.embed = embed;
		setChanged();
		notifyObservers();
	}

	public int[] getTargets() {
		return targets;
	}

	public void setTargets(int[] targets) {
		this.targets = targets;
		setChanged();
		notifyObservers();
	}

	public void saveToFile(String filename) {
		try {
			System.out.println("saved to " + filename);
			// Create file
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("#Made with Ruin Editor by ZomBuster");
			out.newLine();
			out.newLine();
			out.write("weight=" + weight);
			out.newLine();
			out.write("embed_into_distance=" + embed);
			out.newLine();
			out.write("acceptable_target_blocks=");
			for (int i = 0; i < targets.length; i++) {
				out.write(Integer.toString(targets[i]));
				if (i < targets.length - 1)
					out.write(",");
			}
			out.newLine();
			out.write("dimensions=" + height + "," + width + "," + length);
			out.newLine();
			out.write("allowable_overhang=" + overhang);
			out.newLine();
			out.newLine();
			for (int i = 1; i < rules.size(); i++) {
				out.write("rule" + i + "=" + rules.get(i));
				out.newLine();
			}
			out.newLine();
			for (int y = 0; y < height; y++) {
				out.newLine();
				out.newLine();
				out.write("layer");
				for (int x = 0; x < length; x++) {
					out.newLine();
					for (int z = 0; z < width; z++) {
						out.write(Integer.toString(getData(x, y, z)));
						if (z < width - 1)
							out.write(",");
					}
				}
				out.newLine();
				out.write("endlayer");
			}
			out.flush();
			out.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();
			System.out.println("error error");
		}

	}

	private void parseFile(ArrayList<String> lines) throws Exception {
		// first get the variables.
		parseVariables(lines);

		// the first rule added will always be the air block rule.
		rules.add(new RuinTemplateRule("0,100,0"));

		// now get the rest of the data
		Iterator<String> i = lines.iterator();
		String line;
		while (i.hasNext()) {
			line = i.next();
			if (!line.startsWith("#")) {
				if (line.startsWith("layer")) {
					// add in data until we reach the end of the layer
					ArrayList<String> layerlines = new ArrayList<String>();
					line = i.next();
					while (!line.startsWith("endlayer")) {
						if (line.charAt(0) != '#') {
							layerlines.add(line);
						}
						line = i.next();
					}
					layers.add(new RuinTemplateLayer(layerlines, width, length));
				} else if (line.startsWith("rule")) {
					String[] parts = line.split("=");
					rules.add(new RuinTemplateRule(parts[1]));
				}
			}
		}
	}

	private void parseVariables(ArrayList<String> variables) throws Exception {
		Iterator<String> i = variables.iterator();
		String line;
		while (i.hasNext()) {
			line = i.next();
			if (!line.startsWith("#")) {
				if (line.startsWith("acceptable_target_blocks")) {
					String[] check = line.split("=");
					check = check[1].split(",");
					if (check.length < 1) {
						throw new Exception("No targets specified!");
					}
					targets = new int[check.length];
					for (int x = 0; x < check.length; x++) {
						targets[x] = Integer.parseInt(check[x]);
					}
				}
				if (line.startsWith("dimensions")) {
					String[] check = line.split("=");
					check = check[1].split(",");
					height = Integer.parseInt(check[0]);
					width = Integer.parseInt(check[1]);
					length = Integer.parseInt(check[2]);
				}
				if (line.startsWith("weight")) {
					String[] check = line.split("=");
					weight = Integer.parseInt(check[1]);
				}
				if (line.startsWith("embed_into_distance")) {
					String[] check = line.split("=");
					embed = Integer.parseInt(check[1]);
				}
				if (line.startsWith("allowable_overhang")) {
					String[] check = line.split("=");
					overhang = Integer.parseInt(check[1]);
				}
			}
		}
		if (width % 2 == 1) {
			w_off = 0 - (width - 1) / 2;
		} else {
			w_off = 0 - width / 2;
		}
		if (length % 2 == 1) {
			l_off = 0 - (length - 1) / 2;
		} else {
			l_off = 0 - length / 2;
		}
	}
}
