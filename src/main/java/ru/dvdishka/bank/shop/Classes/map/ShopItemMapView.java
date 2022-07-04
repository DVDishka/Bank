package ru.dvdishka.bank.shop.Classes.map;

import java.io.Serializable;

public class ShopItemMapView implements Serializable {

    int centerX;
    int centerZ;
    int id;
    String worldName;
    String scale;
    boolean locked;
    boolean trackingPosition;
    boolean unlimitedTracking;

    public ShopItemMapView(int centerX, int centerZ, int id, String worldName, String scale, boolean locked,
                           boolean trackingPosition, boolean unlimitedTracking) {
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.id = id;
        this.worldName = worldName;
        this.scale = scale;
        this.locked = locked;
        this.trackingPosition = trackingPosition;
        this.unlimitedTracking = unlimitedTracking;
    }

    public int getCenterX() {
        return this.centerX;
    }

    public int getCenterZ() {
        return this.centerZ;
    }

    public int getId() {
        return this.id;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public String getScale() {
        return this.scale;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isTrackingPosition() {
        return this.trackingPosition;
    }

    public boolean isUnlimitedTracking() {
        return this.unlimitedTracking;
    }
}
