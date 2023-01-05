package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class XUrlImpl implements XUrl {

    protected Map<String, String> longToShort = new HashMap<>();
    protected Map<String, String> shortToLong = new HashMap<>();
    protected Map<String, Integer> longCount = new HashMap<>();

    //For generating id
    private String generateId(String alphaNumeric) {
        int length = 9;
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for(int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            //get character specified by index
            //from the string
            char randomChar = alphaNumeric.charAt(index);

            //append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }

        String capAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowAlphabets = "abcdefghijklmnopqrstuvwxyz";
        String numeric = "0123456789";
        
        String shortUrl = "http://short.url/";

        String uniqueId = generateId(capAlphabets + lowAlphabets + numeric);
        
        //check unique
        while(shortToLong.containsKey(shortUrl+uniqueId)){
            uniqueId = generateId(capAlphabets + lowAlphabets + numeric);
        }

        shortUrl += uniqueId;

        //storing the data
        longToShort.put(longUrl,shortUrl);
        shortToLong.put(shortUrl,longUrl);
        longCount.put(longUrl,0);

        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortToLong.containsKey(shortUrl)) {
            return null;
        }

        // storing the data
        longToShort.put(longUrl, shortUrl);
        shortToLong.put(shortUrl, longUrl);
        longCount.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if(shortToLong.containsKey(shortUrl)) {
            String longUrl = shortToLong.get(shortUrl);
            int count = longCount.get(longUrl);

            longCount.put(longUrl, count+1);

            return longUrl;
        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        if(longCount.containsKey(longUrl)){
            return longCount.get(longUrl);
        }
        return 0;
    }

    @Override
    public String delete(String longUrl) {
        if(longToShort.containsKey(longUrl)) {
            String shortUrl = longToShort.get(longUrl);
            longToShort.remove(longUrl);
            shortToLong.remove(shortUrl);
        }
        return null;
    }
}
