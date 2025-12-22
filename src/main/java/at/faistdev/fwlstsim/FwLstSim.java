/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package at.faistdev.fwlstsim;

import at.faistdev.fwlstsim.bl.game.Game;
import at.faistdev.fwlstsim.ui.CallTakerUi;
import at.faistdev.fwlstsim.ui.DispatchUi;
import com.formdev.flatlaf.FlatDarkLaf;

/**
 *
 * @author Ben
 */
public class FwLstSim {

    public static void main(String[] args) {
        Game game = new Game();
        game.init();

        Thread gameThread = new Thread(game);
        gameThread.start();

        createUi();

    }

    private static void createUi() {
        FlatDarkLaf.setup();

        CallTakerUi.create();
        DispatchUi.create();
    }
}
