package com.lab.android.comsys.rwth.lostapp.context;

import com.lab.android.comsys.rwth.lostapp.json.AbstractJSONHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by evgenijavstein on 08/07/15.
 */
//GLOBAL
public class UserTime extends AbstractJSONHandler {


    public static final String SEASON = "season";
    public static final String DAY_OF_WEEK = "dayOfWeek";
    public static final String USER_TIME = "Time";
    public static final String TIME_OF_DAY = "timeOfDay";
    String season;
    String dayOfWeek;
    String timeOfDay;


    public String getSeason() {
        return season;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public static String[] getSeasons() {
        return seasons;
    }

    public static String[] getDaysOfWeek() {
        return daysOfWeek;
    }

    private static final String seasons[] = {
            "Winter", "Winter", "Spring", "Spring", "Spring", "Summer",
            "Summer", "Summer", "Fall", "Fall", "Fall", "Winter"
    };

    private static final String daysOfWeek[]={
            "","SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY","THURSDAY", "FRIDAY", "SATURDAY"
    };
    public void setTime( Date date ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.season= seasons[ cal.get(Calendar.MONTH) ];

        this.dayOfWeek=daysOfWeek[cal.get(Calendar.DAY_OF_WEEK)];
        this.timeOfDay=new SimpleDateFormat("HH").format(date);
    }


    @Override
    protected JSONObject prepareHeader() throws JSONException {
        JSONObject header=new JSONObject();


        JSONObject attributeSeason=new JSONObject();
        attributeSeason.put(NAME_PROPERTY, SEASON);
        attributeSeason.put(TYPE_PROPERTY, NOMINAL_VALUE_ATTRIBUTE);
        attributeSeason.put(CLASS_PROPERTY, IS_ATTRIBUTE_CLASS);
        attributeSeason.put(WEIGHT_PROPERTY, WEIGHT_VALUE);
        JSONArray seasonNames=new JSONArray();
        seasonNames=seasonNames.put(UserTime.seasons[0]).put(UserTime.seasons[2]).put(UserTime.seasons[5]).put(UserTime.seasons[8]);
        attributeSeason.put(LABEL_ATTRIBUTE, seasonNames);



        JSONObject attributeDayOfWeek=new JSONObject();
        attributeDayOfWeek.put(NAME_PROPERTY, DAY_OF_WEEK);
        attributeDayOfWeek.put(TYPE_PROPERTY, NOMINAL_VALUE_ATTRIBUTE);
        attributeDayOfWeek.put(CLASS_PROPERTY, IS_ATTRIBUTE_CLASS);
        attributeDayOfWeek.put(WEIGHT_PROPERTY, WEIGHT_VALUE);
        JSONArray dayNames=new JSONArray();
        for(String day:daysOfWeek){
            dayNames.put(day);
        }
        attributeDayOfWeek.put(LABEL_ATTRIBUTE, dayNames);


        JSONObject attributeTimeOfDay=new JSONObject();
        attributeTimeOfDay.put(NAME_PROPERTY, TIME_OF_DAY);
        attributeTimeOfDay.put(TYPE_PROPERTY, NUMERIC_VALUE_ATTRIBUTE);
        attributeTimeOfDay.put(CLASS_PROPERTY, IS_ATTRIBUTE_CLASS);
        attributeTimeOfDay.put(WEIGHT_PROPERTY, WEIGHT_VALUE);


        JSONArray attributes=new JSONArray();
        attributes.put(attributeSeason);
        attributes.put(attributeDayOfWeek);
        attributes.put(attributeTimeOfDay);

        header.put(RELATION_PROPERTY, USER_TIME);
        header.put(ATTRIBUTES_PROPERTY,attributes);
        return header;
    }

    @Override
    protected JSONArray prepareData() throws JSONException {
        JSONArray data=new JSONArray();
        JSONObject obj=new JSONObject();
        obj.put(SPARSE_ATTRIBUTE_NAME,false);
        obj.put(WEIGHT_PROPERTY,WEIGHT_VALUE);

        JSONArray values=new JSONArray();
        values.put(season);
        values.put(dayOfWeek);
        values.put(Double.parseDouble(timeOfDay));


        obj.put(VALUES_PROPERTY,values );
        data.put(obj);
        return data;
    }
}
