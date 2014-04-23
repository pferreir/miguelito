package org.indico_software.games.miguelito;

import com.badlogic.gdx.Game;

public class MiguelitoSingapore extends Game {

    public SplashScreen splashScreen;
    public GameCredits gameCredits;

    public MiguelitoSingapore() {
    }

    @Override
	public void create () {
        splashScreen = new SplashScreen(this);
        gameCredits = new GameCredits(this);

        this.setScreen(splashScreen);
	}

	@Override
	public void render () {
        super.render();
	}
}
