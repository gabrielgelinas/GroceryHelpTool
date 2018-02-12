package com.example.zombietux.groceryhelptool;

/**
 * Created by gggab(Zombietux) on 2018-01-29.
 */

class URLBuilder {
    private int page;
    private String prefix;
    private String suffix;

    URLBuilder(String prefix, int page, String suffix) {
        this.prefix = prefix;
        this.page = page;
        this.suffix = suffix;
    }


    String getURL() {
        return this.prefix + this.page + this.suffix;
    }
}

