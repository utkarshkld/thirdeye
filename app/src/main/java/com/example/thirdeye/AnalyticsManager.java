package com.example.thirdeye;

import static androidx.core.content.ContextCompat.getSystemService;

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

    public static void trackAppInstallation(Context context ) {
        JSONObject eventData = new JSONObject();
        try {
//            eventData.put("event", eventName);
//            eventData.put("user_id", getDeviceId(context)); // Using device ID as user ID
            eventData.put("device_id", getDeviceId(context));
            eventData.put("phone_numbers", getPhoneNumbers(context));
            eventData.put("ip_address", getIpAddress(context));
            Location location = getLocation(context);
            if (location != null) {
                eventData.put("location", getAddress(context,location.getLatitude(), location.getLongitude()));
                eventData.put("city", getCity(context, location.getLatitude(), location.getLongitude()));
//                eventData.put("location", location.);
//                eventData.put("city", "india");
            }else{
                eventData.put("location", "N/A");
                eventData.put("city", "N/A");
            }
            eventData.put("phone_type", getPhoneType());
            eventData.put("model", getModel());
            eventData.put("os_version", "Android"+getAndroidVersion());
            eventData.put("network_type", getNetworkType(context));
            eventData.put("locale", getLocale());
            Log.d("checking json format",""+eventData);
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
    public static String getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                StringBuilder addressString = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressString.append(address.getAddressLine(i)).append("\n");
                }

                return addressString.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Address";
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
                sendDataToServer(jsonData,context);
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    public static void sendDataToServer(JSONObject eventData,Context context) throws IOException, JSONException {
        Log.d("Json object",""+eventData);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://zoblik.com/api/save_user_data.php");
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
        JSONObject jsonResponse = new JSONObject(sb.toString());
        if (jsonResponse.has("user_id")) {
            String userId = jsonResponse.getString("user_id");
            Log.d("User ID", userId);
            // Save user ID in SharedPreferences
            UserPreferences userPreferences = new UserPreferences(context);
            userPreferences.saveUserId(userId);
        } else {
            Log.d("User ID", "user_id not found in response");
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
    public static String getCountry(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getCountryName();  // Retrieve the country name
            } else {
                Log.e("LocationUtils", "No address found for the provided latitude and longitude.");
            }
        } catch (IOException e) {
            Log.e("LocationUtils", "Geocoder failed", e);
        }
        return null;
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

    public static Location getLocation(Context context) {
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
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "Not Granted";
        }
        String phoneNumber = telephonyManager.getLine1Number();
        Log.d("Phone Number checking",""+phoneNumber);
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Use the phone number
            Log.d("Phone Number checking", phoneNumber);
            return phoneNumber;
        } else {
            // Phone number is not available
            Log.d("Phone Number checking", "Phone number not available");
        }

        return "N.A";
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
//        try {
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
//                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                    return "Wifi";
//                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                    return "Mobile";
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If not connected, return "-"
        if (networkInfo == null || !networkInfo.isConnected()) {
            return "-";
        }

        // If connected to WiFi
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return "WIFI";
        }

        // If connected to Mobile
        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (networkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                case TelephonyManager.NETWORK_TYPE_GSM:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                case 19: // TelephonyManager.NETWORK_TYPE_LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:
                    return "5G";
                default:
                    return "?";
            }
        }
        return "?";
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

