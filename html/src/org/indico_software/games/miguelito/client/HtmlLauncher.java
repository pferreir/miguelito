package org.indico_software.games.miguelito.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import org.indico_software.games.miguelito.MiguelitoSingapore;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(640, 480);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MiguelitoSingapore();
        }
}