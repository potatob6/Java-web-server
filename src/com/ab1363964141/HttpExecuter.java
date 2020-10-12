package com.ab1363964141;

import com.HttpUtils.HttpRequestParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;

public class HttpExecuter implements Runnable{
    public Socket socket;
    public HttpExecuter(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        if(this.socket==null){
            throw new NullPointerException("套接字为空值");
        }
        try{
            InputStream inputStream = socket.getInputStream();
            int size = 0;
            while(size==0){
                size = inputStream.available();
            }
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            String httpReq = new String(bytes);
            HttpRequestParser httpRequestParser = new HttpRequestParser();
            httpRequestParser.parse(httpReq);

            String route = httpRequestParser.route;
            route = URLDecoder.decode(route);
            System.out.println("http access:"+route);

            OutputStream outputStream = socket.getOutputStream();
            File file = new File(route.split("/",2)[1]);
            if(!file.exists()){
                outputStream.write("HTTP/1.1 404 OK\r\nContent-Type: text/html;charset=utf-8\r\n\r\n<h2>路径不存在</h2>".getBytes());
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("HTTP/1.1 200 OK\r\n\r\n");
                outputStream.write(stringBuilder.toString().getBytes());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileBytes = new byte[1024];
                int fSize = 0;
                while((fSize=fileInputStream.read(fileBytes,0,1024))!=-1){
                    outputStream.write(fileBytes,0,fSize);
                }
            }

            outputStream.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
