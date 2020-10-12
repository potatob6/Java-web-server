package com.ab1363964141;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpGet {
    public static void main(String[] args) throws IOException {
        int port = 5555;
        if(args.length<1){
            System.out.println("需要的参数:端口");
            return;
        }
        try{
            port = Integer.parseInt(args[0]);
        }catch (Exception e){
            System.out.println("参数错误"+e.getMessage());
        }
        ServerSocket serverSocket = new ServerSocket(5555);
        while(true){
            Socket socket = serverSocket.accept();
            HttpExecuter httpExecuter = new HttpExecuter(socket);
            Thread thread = new Thread(httpExecuter);
            thread.start();
        }
    }
}
