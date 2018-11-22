package com.apps.indiclass.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dell_Cleva on 04/01/2017.
 */

public class Method {

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        Log.w("M", "getAge: " + today.get(Calendar.DAY_OF_YEAR) + " " + dob.get(Calendar.DAY_OF_YEAR));
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = age;

        return ageInt.toString();
    }

    public static String getFormatIDR(String r) {
        Double a = Double.parseDouble(r);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String currency = formatRupiah.format(a).replace("Rp", "Rp. ");
        return currency.replace(",00", "");
    }

    public static String FormatIndo(String date) {
        Date dates = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
        try {
            dates = sdfdate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fmtOut.format(dates);
    }

    public static int convert(String time) {
        // Based end Time
        String[] unitse = time.split(":"); //will break the string up into an array
        int hourse = Integer.parseInt(unitse[0]) * 60 * 60; //first element
        int minutese = Integer.parseInt(unitse[1]) * 60; //second element
        long durationend = 1000 * hourse + minutese; //add up our values

        return hourse + minutese;
    }

    public static String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes);
    }

    public static String dbZero(int a) {
        return String.format((Locale) null, "%02d", a);
    }

    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public static String FormatDate(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdftimehour = new SimpleDateFormat("HH:mm");
        SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = fmtOut.format(date) + " " + sdftimehour.format(date);
        Log.e("F", kirim);
        return kirim;
    }

    public static String FormatDateOnly(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = fmtOut.format(date);
        Log.e("F", kirim);
        return kirim;
    }

    public static String FormatDateOne(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyyy");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = fmtOut.format(date);
        Log.e("F", kirim);
        return kirim;
    }

    public static String FormatHour(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdftimehour = new SimpleDateFormat("HH:mm");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = sdftimehour.format(date);
        Log.e("F", kirim);
        return kirim;
    }

    public static String FormatHourChat(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdftimehour = new SimpleDateFormat("HH:mm");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = sdftimehour.format(date);
        Log.e("F", kirim);
        return kirim;
    }

    public static String FormatSQL(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        SimpleDateFormat sdftimehour = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = sdftimehour.format(date);
        Log.e("FDOB", kirim);
        return kirim;
    }

    public static String FormatDOB(String a) {
        Date date = null;
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat sdftimehour = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdfdate.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String kirim = sdftimehour.format(date);
        Log.e("FDOB", kirim);
        return kirim;
    }

    public static String getEndTime(String timeStart, long duration) {
        String[] tokens = timeStart.split(":");
        int minutesToMs = Integer.parseInt(tokens[1]) * 60;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600;
        long millis = minutesToMs + hoursToMs;
        millis = duration + millis;
        String hms = String.format("%02d:%02d", TimeUnit.SECONDS.toHours(millis),
                TimeUnit.SECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(millis)));

        Log.e("T", "getEndTime: " + hms);
        return hms;
    }

    public static String getUTCTime() {
        String sUTC;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        sUTC = date.format(currentLocalTime);
//        sUTC = sUTC.replaceAll("\\+","pls");
//        sUTC = sUTC.replaceAll("\\-","mns");
        return sUTC;
    }

    public static String checkVersion(Context context) {
        String version;
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert info != null;
        return version = info.versionName;
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
