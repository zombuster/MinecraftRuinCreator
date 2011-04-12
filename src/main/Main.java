package main;

import data.BlockNames;
import data.RuleColors;

public class Main {

	
	public static void main(String[] args) {
		RuleColors.initColors();
		BlockNames.initNames();
		Apl apl = new Apl();
		apl.run();
	}
}
