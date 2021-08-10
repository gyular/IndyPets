package com.lizin5ths.indypets.config;

import com.lizin5ths.indypets.IndyPets;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = IndyPets.MOD_ID)
public class IndyPetsConfig implements ConfigData {

	@ConfigEntry.Gui.Tooltip(count = 2)
	public boolean selectiveFollowing = true;
	public boolean independentCats = true;
	public boolean independentParrots = true;
	public boolean independentWolves = true;

	@ConfigEntry.Gui.Tooltip
	public boolean silentMode = false;
}
