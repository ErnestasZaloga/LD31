package com.us.ld31.game.ui;

import com.us.ld31.game.ui.SkillBar.SkillButton;
import com.us.ld31.game.ui.Skillbook.SkillIcon;
import com.us.ld31.game.ui.TopBar.Stat;
import com.us.ld31.game.ui.TopBar.StatWidget;

public class Delegate {
	public void onPauseStateChanged() {
		
	}
	
	public void onStatEdited(final StatWidget widget,
							 final Stat stat, 
							 final int change) {
		
	}

	public void onActiveSkillChanged(final SkillButton button) {
	}
	
	public void onLevelUp(final SkillIcon skillIcon) {
	}
	
}
