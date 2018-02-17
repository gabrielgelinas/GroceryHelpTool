package com.example.zombietux.groceryhelptool;

/**
 * Created by gggab(Zombietux) on 2018-01-29.
 */

class URLBuilder {
    private int page;
    private String prefix;
    private String suffix;

    URLBuilder(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    String getURL(int p_page) {
        return this.prefix + p_page + this.suffix;
    }
}

