package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map.Entry;

public class XUrlImpl implements XUrl {

    HashMap<String, String> longShortMapping = new HashMap<>(); // Key --> LongUrl Value --> ShortUrl
    HashMap <String, Integer> shortUrlCountMapping = new HashMap<>(); // Key --> shortUrl Value --> No. of Hits

    String allowedStringChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

    // If longUrl already has a corresponding shortUrl, return that shortUrl
    // If longUrl is new, create a new shortUrl for the longUrl and return it
    public String registerNewUrl(String longUrl) {

        // Check if there is any shortUrl exist for passed longUrl
        if (longShortMapping.containsKey(longUrl)) {
            return longShortMapping.get(longUrl);
        }

        StringBuffer shortUrl = new StringBuffer();
        // Setting initial URL
        shortUrl.append("http://short.url/");
        // Random selector = new Random();
        for (int i = 0; i < 9; i++) {
            /* Generate a random index number */
            /* Selecting character using generated random index */
            /* Concatinating the random character http://short.url/ */
            shortUrl.append(allowedStringChars.charAt((int) (allowedStringChars.length() * Math.random())));
        }

        longShortMapping.put(longUrl, shortUrl.toString());
        return shortUrl.toString();

    }

    // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present,
    // assume it is always new i.e. it hasn't been seen before
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (longShortMapping.containsValue(shortUrl)) {
            return null;
        }
        longShortMapping.put(longUrl, shortUrl);
        return shortUrl;
    }

    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    public String getUrl(String shortUrl) {
        if (!longShortMapping.containsValue(shortUrl)) {
            return null;
        }
        String returnValue = "";
        for (Entry<String, String> entry : longShortMapping.entrySet()) {
            if (entry.getValue().equals(shortUrl)) {
                int count = (shortUrlCountMapping.get(shortUrl) == null) ? 0 : shortUrlCountMapping.get(shortUrl);
                shortUrlCountMapping.put(shortUrl, count + 1);
                returnValue = entry.getKey();
                break;
            }
        }
        return returnValue;
  }

  // Return the number of times the longUrl has been looked up using getUrl()
  public Integer getHitCount(String longUrl){
      return shortUrlCountMapping.getOrDefault(longShortMapping.get(longUrl), 0);
  }

  // Delete the mapping between this longUrl and its corresponding shortUrl
  // Do not zero the Hit Count for this longUrl
  public String delete(String longUrl){
      return longShortMapping.remove(longUrl);
  }
}