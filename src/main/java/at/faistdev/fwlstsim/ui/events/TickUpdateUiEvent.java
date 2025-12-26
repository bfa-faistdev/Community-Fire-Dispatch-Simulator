package at.faistdev.fwlstsim.ui.events;

import at.faistdev.fwlstsim.ui.GameUi;
import at.faistdev.fwlstsim.ui.UiRegistry;

public class TickUpdateUiEvent extends UiEvent {

    private final long currentTick;

    public TickUpdateUiEvent(long currentTick) {
        this.currentTick = currentTick;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    @Override
    public void notifyUi() {
        GameUi gameUi = UiRegistry.get(GameUi.class);
        if (gameUi != null) {
            gameUi.setTime(currentTick);
        }
    }

}
