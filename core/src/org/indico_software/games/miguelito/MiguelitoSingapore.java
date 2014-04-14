package org.indico_software.games.miguelito;

import com.badlogic.gdx.Game;

public class MiguelitoSingapore extends Game {

	
	@Override
	public void create () {
        this.setScreen(new SplashScreen());
	}

	@Override
	public void render () {
        super.render();
	}
}
