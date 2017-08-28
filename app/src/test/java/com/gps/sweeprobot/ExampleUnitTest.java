package com.gps.sweeprobot;

import com.gps.sweeprobot.database.GpsMapBean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCacl() throws Exception{
        System.out.println(256 / 1984f);
        System.out.println(1984 * (256 / 1984f));
    }

    @Test
    public void testList() throws Exception{

        GpsMapBean gps1 = new GpsMapBean();
        GpsMapBean gps2 = new GpsMapBean();
        GpsMapBean gps3 = new GpsMapBean();
        GpsMapBean gps4 = new GpsMapBean();
        GpsMapBean gps5 = new GpsMapBean();

        List gpsMapBeanList1 = new ArrayList<>();
        List<GpsMapBean> gpsMapBeanList2 = new ArrayList<>();

        gpsMapBeanList2.add(gps1);
        gpsMapBeanList2.add(gps2);
        gpsMapBeanList2.add(gps3);
        gpsMapBeanList2.add(gps4);
        gpsMapBeanList2.add(gps5);

        gpsMapBeanList1.clear();
        gpsMapBeanList1.addAll(gpsMapBeanList2);

        System.out.println(gpsMapBeanList1);

    }

    @Test
    public void cloneTest() throws Exception{

    }
}