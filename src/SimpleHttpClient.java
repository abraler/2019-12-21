import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleHttpClient {
    public static void main(String[] args) throws IOException {
        String request = "GET / HTTP/1.0\r\nHost: www.baidu.com\r\n\r\n";

        Socket socket = new Socket("www.baidu.com", 80);
        socket.getOutputStream().write(request.getBytes("UTF-8"));
        // 版本   状态码     状态描述
        // 响应头打印
        // 把响应正文保存下来
        byte[] bytes = new byte[4096];
        int len = socket.getInputStream().read(bytes);
        // 假设 4096 字节已经包含 响应行 + 所有响应头 + 一部分正文
        int index = -1;
        for (int i = 0; i < len - 3; i++) {
            if (bytes[i] == '\r' && bytes[i+1] == '\n' && bytes[i+2] == '\r' && bytes[i+3] == '\n') {
                index = i;
                break;
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, 0, index + 4);
        Scanner scanner = new Scanner(byteArrayInputStream, "UTF-8");
        String statusLine = scanner.nextLine();
        System.out.println("状态行: " + statusLine);
        String line;
        int length=-1;

        while (!(line = scanner.nextLine()).isEmpty()) {
            String[] kv = line.split(":");
            String key = kv[0].trim();
            String value = kv[1].trim();
            System.out.println("响应头: " + key + " = " + value);
            for (int i = 0; i <kv.length ; i++) {
                if(kv[i].equalsIgnoreCase("Content-Length")){
                    length=Integer.valueOf(value);
                }
            }
        }
        int wan=len-index-4;
        int huanwan=length-wan;
        byte[]body=new byte[length];
        System.arraycopy(bytes,index+4,body,0,wan);
        int 实际读了=socket.getInputStream().read(body,wan,huanwan);
        FileOutputStream fileOutputStream=new FileOutputStream("百度.html");
        fileOutputStream.write(body);

    }

}
