package hr.ljakovic.cinespring.util;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import org.springframework.stereotype.Component;

@Component
public class TmdbApiUtils {

    public static final String API_KEY = "db1569761ef5a6a4e9c7d9b8e0283951";
    public static final String LANG = "en";

    public static String getConfigurationBaseUrl() {
        return new TmdbApi(API_KEY).getConfiguration().getBaseUrl();
    }

    public static String getConfigurationSecBaseUrl() {
        return new TmdbApi(API_KEY).getConfiguration().getSecureBaseUrl();
    }

    public static String getPosterSizeOriginal() {
        TmdbConfiguration conf = new TmdbApi(API_KEY).getConfiguration();
        return conf.getPosterSizes().get(6);
    }

    public static String getPosterSizeW500() {
        TmdbConfiguration conf = new TmdbApi(API_KEY).getConfiguration();
        return conf.getPosterSizes().get(4);
    }

    public static String getPosterSizeW154() {
        TmdbConfiguration conf = new TmdbApi(API_KEY).getConfiguration();
        return conf.getPosterSizes().get(1);
    }
}
