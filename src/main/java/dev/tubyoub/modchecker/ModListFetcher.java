package dev.tubyoub.modchecker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ModListFetcher {
    private static final String MOD_LIST_URL = "https://raw.githubusercontent.com/TubYoub/ModChecker/master/allowed_mods.json";
    private static final Duration CACHE_DURATION = Duration.ofHours(24);

    private List<String> cachedModList;
    private long lastFetchTime;

    public List<String> getAllowedMods() {
        if (cachedModList == null || System.currentTimeMillis() - lastFetchTime > CACHE_DURATION.toMillis()) {
            fetchModList();
        }
        return cachedModList;
    }

    private void fetchModList() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MOD_LIST_URL))
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                cachedModList = gson.fromJson(response.body(), new TypeToken<ArrayList<String>>(){}.getType());
                lastFetchTime = System.currentTimeMillis();
            } else {
                throw new RuntimeException("Failed to fetch mod list: HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch mod list", e);
        }
    }
}
