package com.example.thirdeye;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.Locale;

public class AnalyticsManager {

    public static void trackAppInstallation(Context context, String eventName) {
        JSONObject eventData = new JSONObject();
        try {
            eventData.put("event", eventName);
            eventData.put("user_id", getDeviceId(context)); // Using device ID as user ID
            eventData.put("device_id", getDeviceId(context));
            eventData.put("phone_numbers", getPhoneNumbers(context));
            eventData.put("ip_address", getIpAddress(context));
            Location location = getLocation(context);
            if (location != null) {
//                eventData.put("location", location.getLatitude() + "," + location.getLongitude());
//                eventData.put("city", getCity(context, location.getLatitude(), location.getLongitude()));
                eventData.put("location", "india");
                eventData.put("city", "india");
            }
            eventData.put("phone_type", getPhoneType());
            eventData.put("model", getModel());
            eventData.put("android_version", getAndroidVersion());
            eventData.put("network_type", getNetworkType(context));
            eventData.put("locale", getLocale());
//            sendDataToServer(eventData);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new InsertFeedbackAsyncTask(context,eventData).execute();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class InsertFeedbackAsyncTask extends AsyncTask<Void, Void, Void> {
        JSONObject jsonData;
        Context context;

        public InsertFeedbackAsyncTask(Context context,JSONObject jsonData) {
            this.jsonData = jsonData;
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sendDataToServer(jsonData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    public static void sendDataToServer(JSONObject eventData) throws IOException {
        Log.d("Json object",""+eventData);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://zoblik.com/api/save_analytics.php");
        StringEntity se = new StringEntity(eventData.toString());
        httppost.setEntity(se);
                    Log.d("feedback rating", eventData.toString());
        // Set content type of the request body
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));                    // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity httpEntity = response.getEntity();
        InputStream is = httpEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        Log.d("feedback hello",""+sb);
    }

    public static void trackUserEngagement(Context context, String action) {
        JSONObject eventData = new JSONObject();
        try {
            eventData.put("event", "user_engagement");
            eventData.put("user_id", getDeviceId(context)); // Using device ID as user ID
            eventData.put("action", action);
            Location location = getLocation(context);
            if (location != null) {
                eventData.put("location", location.getLatitude() + "," + location.getLongitude());
                eventData.put("city", getCity(context, location.getLatitude(), location.getLongitude()));
            }
            sendDataToServer(eventData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Other tracking methods...

    @SuppressLint("HardwareIds")
    private static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId != null ? androidId : Build.SERIAL;
    }

    private static class GetIpAddressTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                return inetAddress.getHostAddress();
            } catch (Exception e) {
                Log.d("Checking Error", "" + e.toString());
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String getIpAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wm.getConnectionInfo().getIpAddress();
        int temp = ipAddress;
        String binarystring = "";
        while(temp!=0){
            binarystring +=  Integer.toString(temp%2); ;
            temp /= 2;
        }


        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        Log.d("ip adress",""+formatedIpAddress+" "+ipAddress+" "+binarystring);
        return formatedIpAddress;

//        return ip;
    }

    private static Location getLocation(Context context) {
        Location location = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }
    private static String getPhoneNumbers(Context context) {
        StringBuilder phoneNumbers = new StringBuilder();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // API level 26 and above
                    if (telephonyManager.getPhoneCount() > 1) {
                        for (int slot = 0; slot < telephonyManager.getPhoneCount(); slot++) {
                            String phoneNumber = telephonyManager.getLine1Number();
                            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                                phoneNumbers.append(phoneNumber).append(",");
                            }
                        }
                    } else {
                        String phoneNumber = telephonyManager.getLine1Number();
                        if (phoneNumber != null && !phoneNumber.isEmpty()) {
                            phoneNumbers.append(phoneNumber);
                        }
                    }
                } else {
                    // API level below 26
                    String phoneNumber = telephonyManager.getLine1Number();
                    if (phoneNumber != null && !phoneNumber.isEmpty()) {
                        phoneNumbers.append(phoneNumber);
                    }
                }
            }
        } else {
            // Handle lack of permission
            Log.e("Phone Numbers", "Permission READ_PHONE_STATE not granted.");
        }

        return phoneNumbers.toString();
    }


    private static String getCity(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPhoneType() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    private static String getModel() {
        return Build.MODEL;
    }

    private static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private static String getNetworkType(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "Wifi";
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return "Mobile";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getLocale() {
        return Locale.getDefault().toString();
    }

    private static Location getLocationFromGps(Context context) {
        Location location = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }
}

