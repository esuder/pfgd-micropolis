// This file is part of MicropolisJ.
// Copyright (C) 2013 Jason Long
// Portions Copyright (C) 1989-2007 Electronic Arts Inc.
//
// MicropolisJ is free software; you can redistribute it and/or modify
// it under the terms of the GNU GPLv3, with additional terms.
// See the README file, included in this distribution, for details.

package micropolisj.engine;

import static micropolisj.engine.TileConstants.*;

class Airtanker extends ToolStroke
{
	Airtanker(Micropolis city, int xpos, int ypos)
	{
		//not sure why but my code breaks if I try to change the name
		//so I have to leave it as NEW_BUILDING
		super(city, MicropolisTool.NEW_BUILDING, xpos, ypos);
	}

	@Override
	protected void applyArea(ToolEffectIfc eff)
	{
		CityRect b = getBounds();
		
		for (int y = 0; y < b.height; y++) {
			for (int x = 0; x < b.width; x++) {

				ToolEffectIfc subEff = new TranslatedToolEffect(eff, b.x+x, b.y+y);
				int myTile = subEff.getTile(0, 0);
				
				//select only fire tiles
				if (myTile >= FIRE && myTile <= FIRE+9) {
					System.out.println("putting out "+myTile);
					dozeField(subEff);
					//make airplane noise when putting out fire
					eff.makeSound(0, 0, Sound.FLYBY);
				}
				else {
					System.out.println("Not putting out "+myTile);
				}
			}
		}
	}
	
	void dozeField(ToolEffectIfc eff)
	{
		int tile = eff.getTile(0, 0);

		//replace tiles on fire with rubble
		eff.setTile(0, 0, RUBBLE);
		
		fixZone(eff);
		eff.spend(3);
		return;
	}
}