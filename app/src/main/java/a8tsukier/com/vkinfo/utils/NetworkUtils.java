package a8tsukier.com.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
* @author Otlev Illia
*/

public class NetworkUtils {
    private static final String VK_API_BASE_URL = "https://api.vk.com/";
    private static final String VK_USERS_GET = "method/users.get";
    private static final String VK_USER_ID_PARAM = "user_ids";
    private static final String VK_VERSION_PARAM = "v";
    private static final String VK_ACCESS_TOKEN = "access_token";
    private static final String VK_ACCESS_KEY = "56d72f6756d72f6756d72f671056b1ed73556d756d72f670d028fc65b9888674915bdaf";

    public static URL generateURL(String userId)
    {
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(VK_USER_ID_PARAM, userId)
                .appendQueryParameter(VK_VERSION_PARAM, "5.8")
                .appendQueryParameter(VK_ACCESS_TOKEN, VK_ACCESS_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return  url;
    }

    public static String getResponceFromURL (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try
    {
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();

        if (hasInput) {
            return scanner.next();
            } else {
                return null;
                    }
    } finally {
            urlConnection.disconnect();
        }

    }
}
