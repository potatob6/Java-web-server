package com.HttpUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    public String route;
    public String method;
    public String lisenceVersion;
    public HashMap<String,String> header = new HashMap<>();
    public String body;
    public boolean parse(String request){
        Pattern pattern = Pattern.compile("^(GET|POST) [^ ]+ HTTP/\\d\\.\\d\\r\\n.*\\r\\n");
        if(pattern.matcher(request).find()==false)
            return false;

        String[] st = request.split("\\r\\n\\r\\n",2);
        body = st[1];
        headerParse(st[0]);

        return true;
    }

    public void headerDownParser(String n){
        String[] st = n.split("\\r\\n");
        if(st.length==0) return;
        for(String line:st){
            String[] lineSp = line.split(": ",2);
            this.header.put(lineSp[0],lineSp[1]);
        }
    }

    public void headerParse(String n){
        String[] st = n.split("\\r\\n",2);
        Pattern pattern = Pattern.compile("(GET|POST) ([^ ]+) HTTP/(\\d\\.\\d)");
        Matcher matcher = pattern.matcher(st[0]);
        matcher.find();
        route = matcher.group(2);
        method = matcher.group(1);
        lisenceVersion = matcher.group(3);
        if(st.length>=2) {
            headerDownParser(st[1]);
        }
    }
}
