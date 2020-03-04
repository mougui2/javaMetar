/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javametar;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.*;
import weatherMetar.METAR;
import weatherMetar.Response;

/**
 *
 * @author Morgan
 */
public class Tools {

    private final String URL_METAR_TIMERANGE = "https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&startTime={0}&endTime={1}&stationString={2}";
    private final String URL_METAR_HOURSBEFORE = "https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString={0}&hoursBeforeNow={1}";

    private Response getAnswer(String url) {
        Response result = new Response();

        try {
            JAXBContext jc = JAXBContext.newInstance("weatherMetar");
            Unmarshaller u = jc.createUnmarshaller();
            result = (Response) u.unmarshal(new URL(url));
        } catch (Exception ex) {
        }
        return result;
    }

    private String getUrl(String name,int hoursBefore){
        if(hoursBefore == -1){
            return String.format(URL_METAR_HOURSBEFORE, name, 1);
        }
        else{
            return String.format(URL_METAR_HOURSBEFORE, name, hoursBefore);
        }
    }
    
    private String getUrl(String name,Date startDate,Date endDate){
        //date doit etre sous forme : YYYY-02-11T22:53:00Z
        DateFormat datef = new SimpleDateFormat("yyyy-mm-ddThh:mm:ss");
        String debutStr = datef.format(startDate);
        String finStr = datef.format(endDate);
        
        return String.format(URL_METAR_TIMERANGE, debutStr,finStr,name);
    }
    
    public float[] getStationPosition(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return new float[]{station.getLongitude(), station.getLatitude()};
    }

    public List<Float[]> getStationTemperature(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Float[]> result = new ArrayList<Float[]>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            METAR station = response.getData().getMETAR().get(i);
            result.add( new Float[]{station.getLongitude(), station.getLatitude()});
        }
        return result;
    }
    
    public float getStationTemperature(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return station.getTempC();
    }

    public List<Float> getAllStationTemperature(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Float> result = new ArrayList<Float>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            result.add( response.getData().getMETAR().get(i).getTempC());
        }
        return result;
    }
    
    public int getStationWindDirection(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return station.getWindDirDegrees();
    }

   public List<Integer> getStationWindDirection(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            result.add( response.getData().getMETAR().get(i).getWindDirDegrees());
        }
        return result;
    }
    
    public int getStationWindSpeed(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return station.getWindSpeedKt();
    }

    public List<Integer> getStationWindSpeed(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            result.add( response.getData().getMETAR().get(i).getWindSpeedKt());
        }
        return result;
    }
    
    public float getStationElevation(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return station.getElevationM();
    }

    public List<Float> getStationElevation(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Float> result = new ArrayList<Float>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            result.add( response.getData().getMETAR().get(i).getElevationM());
        }
        return result;
    }
    
    public float getStationPressure(String name) {
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        METAR station = response.getData().getMETAR().get(0);

        return station.getSeaLevelPressureMb();
    }
    
    public List<Float> getStationPressure(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        ArrayList<Float> result = new ArrayList<Float>();
        
        for (int i = 0; i < response.getData().getMETAR().size(); i++) {
            result.add( response.getData().getMETAR().get(i).getSeaLevelPressureMb());
        }
        return result;
    }
    
    public METAR getStationMetar(String name){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        return response.getData().getMETAR().get(0);
    }
    
    public List<METAR> getStationMetar(String name,Date startDate,Date endDate){
        String newUrl = getUrl(name,-1);
        Response response = getAnswer(newUrl);
        return response.getData().getMETAR();
    }
    
    
}
