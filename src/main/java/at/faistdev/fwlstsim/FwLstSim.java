/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package at.faistdev.fwlstsim;

import at.faistdev.fwlstsim.bl.game.Game;
import at.faistdev.fwlstsim.ui.CallTakerUi;
import at.faistdev.fwlstsim.ui.DispatchUi;

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

        CallTakerUi.create();
        DispatchUi.create();
    }
}
