package com.viamatica.tiendaaplication.metodoConexion;

import android.content.Context;

import com.viamatica.tiendaaplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class MetodosConexion {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String INVALID_METHOD = "Invalid connection method ";
    boolean OK = false;
    int StatusCode = 0;
    public static final String charset = "UTF-8";
    private HttpURLConnection httpURLConnection;
    private HttpsURLConnection httpsURLConnection;
    private DataOutputStream dataOutputStream;
    private StringBuilder result;
    private URL url;
    private JSONArray jsonArray = null;
    private JSONObject jsonObject = null;
    private StringBuilder paramsStringBuilder;
    private String paramsString;

    public JSONObject getJSONObject(String url, String method, String token_type, String accses_token, String json, String fragment) throws IOException, JSONException {
        try {
            JSONObject JSONObject = null;
            this.paramsStringBuilder = new StringBuilder();

//            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    HostnameVerifier hv =
//                            HttpsURLConnection.getDefaultHostnameVerifier();
//                    return hv.verify(ConstantesUrl.getBaseURL(), session);
//                }
//            };

            if (method.equals(POST)) {
                try {
                    this.url = new URL(url.replaceAll(" ", "%20"));

                    this.httpsURLConnection = (HttpsURLConnection) this.url.openConnection();
//                    SSLContext sc;
//                    sc = SSLContext.getInstance("TLS");
//                    sc.init(null, null, new java.security.SecureRandom());
//                    this.httpsURLConnection.setSSLSocketFactory(sc.getSocketFactory());

                    // Load CAs from an InputStream
                    // (could be from a resource or ByteArrayInputStream or ...)
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    // From https://www.washington.edu/itconnect/security/ca/load-der.crt
                    InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
                    Certificate ca;
                    try {
                        ca = cf.generateCertificate(caInput);
                        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                    } finally {
                        caInput.close();
                    }

                    // Create a KeyStore containing our trusted CAs
                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);

                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);

                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);


                    this.httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
                    this.httpsURLConnection.setDoOutput(true);
                    this.httpsURLConnection.setRequestMethod(POST);
                    this.httpsURLConnection.setRequestProperty("token", token_type + " " + accses_token);
                    this.httpsURLConnection.setRequestProperty("Content-Type", "application/json");
                    this.httpsURLConnection.setRequestProperty(ACCEPT_CHARSET, charset);
                    this.httpsURLConnection.setReadTimeout(12 * 100000);
                    this.httpsURLConnection.setConnectTimeout(12 * 100000);
                    this.httpsURLConnection.connect();

                    this.paramsString = this.paramsStringBuilder.toString();
                    OutputStream outputStream = this.httpsURLConnection.getOutputStream();
                    outputStream.write(json.getBytes("UTF-8"));
                    this.dataOutputStream = new DataOutputStream(outputStream);
                    this.dataOutputStream.writeBytes(this.paramsString);
                    this.dataOutputStream.flush();
                    //this.dataOutputStream.close();

                    if (fragment.equals("Consult_CalifFragment")) {
                        //Consult_CalifFragment.StatusCode = httpsURLConnection.getResponseCode();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (method.equals(GET)) {
                try {
                    this.url = new URL(url.replaceAll(" ", "%20"));
                    this.httpURLConnection = (HttpURLConnection) this.url.openConnection();
                    this.httpURLConnection.setDoOutput(false);
                    this.httpURLConnection.setRequestMethod(GET);
                    this.httpURLConnection.setRequestProperty("token", token_type + " " + accses_token);
                    this.httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    this.httpURLConnection.setRequestProperty(ACCEPT_CHARSET, charset);
                    this.httpURLConnection.setReadTimeout(12 * 100000);
                    this.httpURLConnection.setConnectTimeout(12 * 100000);
                    this.httpURLConnection.connect();
                    this.paramsString = this.paramsStringBuilder.toString();


                    //this.dataOutputStream.close();

                    if (fragment.equals("Consult_CalifFragment")) {
                        //Consult_CalifFragment.StatusCode = httpURLConnection.getResponseCode();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream inputStream = this.httpsURLConnection.getInputStream();
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            this.result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            String resultToString = this.result.toString();


            try {
                JSONObject = new JSONObject(resultToString);
            } catch (JSONException e) {
                e.getStackTrace();
            }
            return JSONObject;
        }catch (Exception e){
            e.printStackTrace();

        }
        return new JSONObject();
    }



//    public JSONObject getJSObject(String url, String method, String token_type, String accses_token, String json, String fragment) throws IOException, JSONException {
//        JSONObject jsonObject = null;
//        this.paramsStringBuilder = new StringBuilder();
//        if (method.equals(POST)) {
//            try {
//                this.url = new URL(url.replaceAll(" ", "%20"));
//                this.httpURLConnection = (HttpURLConnection) this.url.openConnection();
//                this.httpURLConnection.setDoOutput(true);
//                this.httpURLConnection.setRequestMethod(POST);
//                this.httpURLConnection.setRequestProperty("token", token_type + " " + accses_token);
//                this.httpURLConnection.setRequestProperty("Content-Type", "application/json");
//                this.httpURLConnection.setRequestProperty(ACCEPT_CHARSET, charset);
//                this.httpURLConnection.setReadTimeout(12 * 100000);
//                this.httpURLConnection.setConnectTimeout(12 * 100000);
//                this.httpURLConnection.connect();
//
//                this.paramsString = this.paramsStringBuilder.toString();
//                OutputStream outputStream = this.httpURLConnection.getOutputStream();
//                outputStream.write(json.getBytes("UTF-8"));
//                this.dataOutputStream = new DataOutputStream(outputStream);
//                this.dataOutputStream.writeBytes(this.paramsString);
//                this.dataOutputStream.flush();
//                //this.dataOutputStream.close();
//
//                if (fragment.equals("Consult_CalifFragment")) {
//                    //Consult_CalifFragment.StatusCode = httpURLConnection.getResponseCode();
//                }
//
//            } catch (Exception e) {
//            }
//        }
//
//        if (method.equals(GET)) {
//            try {
//                this.url = new URL(url.replaceAll(" ", "%20"));
//                this.httpURLConnection = (HttpURLConnection) this.url.openConnection();
//                this.httpURLConnection.setDoOutput(false);
//                this.httpURLConnection.setRequestMethod(GET);
//                this.httpURLConnection.setRequestProperty("token", token_type + " " + accses_token);
//                this.httpURLConnection.setRequestProperty("Content-Type", "application/json");
//                this.httpURLConnection.setRequestProperty(ACCEPT_CHARSET, charset);
//                this.httpURLConnection.setReadTimeout(12 * 100000);
//                this.httpURLConnection.setConnectTimeout(12 * 100000);
//                this.httpURLConnection.connect();
//                this.paramsString = this.paramsStringBuilder.toString();
//
//
//                //this.dataOutputStream.close();
//
//                if (fragment.equals("Consult_CalifFragment")) {
//                    //Consult_CalifFragment.StatusCode = httpURLConnection.getResponseCode();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        InputStream inputStream = this.httpURLConnection.getInputStream();
//        InputStream bufferedInputStream = new BufferedInputStream(inputStream);
//        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
//        BufferedReader reader = new BufferedReader(inputStreamReader);
//        this.result = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            result.append(line);
//        }
//        reader.close();
//        String resultToString = this.result.toString();
//
//
//        try {
//            jsonObject = new JSONObject(resultToString);
//        } catch (JSONException e) {
//            e.getStackTrace();
//        }
//        return jsonObject;
//    }


    // este utilizar para login




}
