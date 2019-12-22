import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimplehttpClient1 {
    public static void main(String[] args) throws IOException {
        String request="GET / HTTP/1.0\r\nHost:www.baidu.com\r\n\r\n";
        Socket sockets=new Socket("www.baidu.com",80);
        sockets.getOutputStream().write(request.getBytes("utf-8"));
        byte[]bytes=new byte[4096];
        int len=sockets.getInputStream().read(bytes);
//        String buffer=new String(bytes,0,len,"utf-8");
//        System.out.println(buffer);
        int index=-1;
        for(int i=0;i<len-3;i++){
            if(bytes[i]=='\r'&&bytes[i+1]=='\n'&&bytes[i+2]=='\r'&&bytes[i+3]=='\n');
            index=i;
        }
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes,0,index);
        Scanner in=new Scanner(byteArrayInputStream,"utf-8");
        String statuLine=in.nextLine();
        System.out.println(statuLine);
        String Line;
        while(!(Line=in.nextLine()).isEmpty()){
            String []KV=Line.split(":");
            String K=KV[0].trim();
            String V=KV[1].trim();
            System.out.println(K+" =   " +V);
        }

    }
}
