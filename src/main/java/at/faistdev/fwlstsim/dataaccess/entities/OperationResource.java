package at.faistdev.fwlstsim.dataaccess.entities;

public enum OperationResource {

    LIGHTING("Beleuchtung"),
    DOOR_OPENING_KIT("Türöffnungskit"),
    OIL_SPILL("Ölschadensbekämpfung"),
    SPINE_BOARD("Spine-Board"),
    SMALL_WATER_TANK_LESS_THAN_1_000_L("Kleiner Wassertank (<1000L)"),
    BIG_WATER_TANK_MORE_THAN_1_000_L("Großer Wassertank (>1000L)"),
    FIRE_PUMP("Tragkraftspritze/Einbaupumpe"),
    WATER_DAMAGE_KIT("Wasserschadensbekämpfung"),
    LADDER("Leiter"),
    HYDRAULIC_SPREADER("Hydraulische Spreizer/Schere"),
    GAS_EXWARN("Gas-Exwarn"),
    VENTILATION("Ventilator"),
    CHAINSAW("Kettensäge"),
    WINCH("Seilwinde"),
    INSECT_KIT("Insektenbekämpfung");

    private final String text;

    private OperationResource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
