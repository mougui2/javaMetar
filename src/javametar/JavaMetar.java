/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javametar;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import weatherMetar.Response;

/**
 *
 * @author Morgan
 */
public class JavaMetar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException, MalformedURLException {

        JAXBContext jc = JAXBContext.newInstance("weatherMetar");
        Unmarshaller u = jc.createUnmarshaller();
        Response mimi = (Response) u.unmarshal(new URL("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&startTime=2020-02-12T05:13:17+0700&endTime=2020-02-12T07:13:17+0700&stationString=PHTO"));
        System.out.println("nom du chien :" + mimi.getData().getMETAR().get(0).getLatitude());
    }

}
