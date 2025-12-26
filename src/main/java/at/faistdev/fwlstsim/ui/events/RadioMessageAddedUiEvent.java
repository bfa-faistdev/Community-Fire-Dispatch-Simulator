package at.faistdev.fwlstsim.ui.events;

import at.faistdev.fwlstsim.ui.RadioUi;
import at.faistdev.fwlstsim.ui.UiRegistry;

public class RadioMessageAddedUiEvent extends UiEvent {

    @Override
    public void notifyUi() {
        UiRegistry.get(RadioUi.class).fillRadioRequests();
    }

}
