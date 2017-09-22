package com.app.gaolonglong.fragmenttabhost.bean;

/**
 * Created by yanqi on 2017/9/12.
 */

public class LocationInfo {
    private String city;
    private String address;
    private String lat;
    private String lng;

    public LocationInfo(String city,String address,String lat,String lng)
    {
        this.city = city;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
    public String getCity()
    {
        return city;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getAddress()
    {
        return address;
    }
    public void setLat(String lat)
    {
        this.lat = lat;
    }
    public String getLat()
    {
        return lat;
    }
    public void setLng(String lng)
    {
        this.lng = lng;
    }
    public String getLng()
    {
        return lng;
    }
}
