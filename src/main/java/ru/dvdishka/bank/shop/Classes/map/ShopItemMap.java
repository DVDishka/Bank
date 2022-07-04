package ru.dvdishka.bank.shop.Classes.map;

import java.io.Serializable;

public class ShopItemMap implements Serializable {

    ShopItemMapView mapView;
    int color;
    String locationName;
    boolean scaling;

    public ShopItemMap(ShopItemMapView mapView, int color, String locationName, boolean scaling) {
        this.mapView = mapView;
        this.color = color;
        this.locationName = locationName;
        this.scaling = scaling;
    }

    public ShopItemMapView getMapView() {
        return this.mapView;
    }

    public int getColor() {
        return this.color;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public boolean isScaling() {
        return this.scaling;
    }
}
