package com.lab.android.comsys.rwth.lostapp.context;

/**
 * Created by evgenijavstein on 04/07/15.
 */
public class ContextFeatures  {



    public ContextFeatures( ){

    }


    protected MotionFeatures[] accelerationFeatures;
    protected MotionFeatures [] gyroscopeFeatures;
    protected Weather weather;
    protected SimpleLocation location;
    protected UserTime userTime;

    public MotionFeatures[] getAccelerationFeatures() {
        return accelerationFeatures;
    }

    public MotionFeatures[] getGyroscopeFeatures() {
        return gyroscopeFeatures;
    }

    public Weather getWeather() {
        return weather;
    }

    public SimpleLocation getLocation() {
        return location;
    }

    public UserTime getUserTime() {
        return userTime;
    }

    public void setAccelerationFeatures(MotionFeatures[] accelerationFeatures) {

        this.accelerationFeatures = accelerationFeatures;

    }

    public void setGyroscopeFeatures(MotionFeatures[] gyroscopeFeatures) {

        this.gyroscopeFeatures = gyroscopeFeatures;

    }

    public void setWeather(Weather weather) {

        this.weather = weather;

    }

    public void setLocation(SimpleLocation simpleLocation) {

        this.location = simpleLocation;

    }

    public void setUserTime(UserTime userTime) {

        this.userTime = userTime;

    }




}
