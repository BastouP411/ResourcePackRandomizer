package fr.bastoup.resourcepackcreator.util;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleImageHTTP {

    private static String HOST = "google.com";
    private static String PATH = "search";
    private static String SCHEME = "https";

    private static Pattern PATTERN = Pattern.compile("\"(https?://[^\\s\"]*\\.(png)([?]\\S*)?)\"");

    public static List<String> getImages(String search, OkHttpClient client) throws IOException {

        Headers h = new Headers.Builder()
                .add("authority", "www.google.com")
                .add("cache-control", "max-age=0")
                .add("dnt", "1")
                .add("upgrade-insecure-requests", "1")
                .add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
                .add("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .add("sec-fetch-site", "same-origin")
                .add("sec-fetch-mode", "navigate")
                .add("sec-fetch-user", "?1")
                .add("sec-fetch-dest", "document")
                .add("referer", "https,//www.google.com/")
                .add("accept-language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7").build();

        HttpUrl url = new HttpUrl.Builder().scheme(SCHEME).host(HOST).addPathSegment(PATH)
                .addQueryParameter("hl", "en")
                .addQueryParameter("tbm", "isch")
                .addQueryParameter("tbs", "ift:png")
                .addQueryParameter("q", search)
                .addQueryParameter("oq", search)
                .addQueryParameter("sclient", "img")
                .addQueryParameter("safe", "active").build();

        Request req = new Request.Builder().get().url(url).headers(h).build();

        String res = client.newCall(req).execute().body().string();

        Matcher m = PATTERN.matcher(res);

        List<String> allMatches = new ArrayList<>();
        while (m.find()) {
            String s = m.group();
            s = s.substring(1, s.length() - 1);
            if (!s.contains("google.com"))
                allMatches.add(s);
        }

        return allMatches;
    }

    public static byte[] resourceBytes(HttpUrl url, OkHttpClient client) throws IOException {

        Request req = new Request.Builder().get().url(url).build();

        return client.newCall(req).execute().body().bytes();
    }

    public static File dlRandomImage(String search, File dir) throws IOException {
        if(!dir.isDirectory())
            throw new IllegalArgumentException("Argument #2 must be a directory");

        OkHttpClient client = new OkHttpClient();
        Random rdm = new Random();

        List<String> images = getImages(search, client);

        HttpUrl url = HttpUrl.parse(images.get(rdm.nextInt(images.size())));
        System.out.println("Using url: " + url.toString());

        File res = new File(dir, search + ".png");

        if(res.exists())
            throw new IllegalArgumentException("Result file already exists.");

        res.createNewFile();

        byte[] bytes = resourceBytes(url, client);
        OutputStream os = new FileOutputStream(res);
        os.write(bytes);
        os.flush();
        os.close();

        return res;
    }

}
