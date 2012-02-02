package com.netnumeri.client.javaonly.events;

import java.util.ArrayList;
import java.util.List;

public class RestUrl {
    private String presenterUrl;
    private List<String> params = new ArrayList<String>();

    public RestUrl(String url) {
        parseUrl(url);

    }

    private void parseUrl(String url) {

//        String pageUrl = afterHash(url);
        String[] sl = url.split("/");
        int l = sl.length;
        if (l > 0) {
            presenterUrl = sl[0];
        } else {
            presenterUrl = "";
        }

        params.clear();
        if (l > 1) {
            for (int i = 1; i < l; i++) {
                params.add(sl[i]);
            }
        }

//        CoreUtils.debug("parsed url:" + url + " presenterUrl:" + presenterUrl + " params:" + params);
    }

    public String getPresenterUrl() {
        return presenterUrl;
    }

    @Override
    public String toString() {

        return presenterUrl + concatParams();
    }

    private String concatParams() {
        StringBuffer sb = new StringBuffer();

        for (String param : params) {
            sb.append("/");
            sb.append(param);
        }

        return sb.toString();
    }

    public String getId() {
        if (params.size() > 0) {
            return params.get(params.size() - 1);
        } else {
            return "";
        }
    }
}
