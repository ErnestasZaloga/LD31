package com.us.ld31.game.ui;

import com.us.ld31.game.ui.SkillBar.SkillButton;
import com.us.ld31.game.ui.TopBar.Stat;
import com.us.ld31.game.ui.TopBar.StatWidget;

public class Delegate {
	// Should return true if stat editing needs to be disabled
	public void onStatEdited(final StatWidget widget,
							 final Stat stat, 
							 final int change) {
		
	}

	public void onActiveSkillChanged(final SkillButton button,
									 final int mappingCode) {
	}
	
}
