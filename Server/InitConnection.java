package Server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
    ServerSocket socketServer = null;
    DataInputStream password=null;
    DataOutputStream verify = null;
    String width = "";
    String height = "";
    InitConnection(int port, String value1)
    {
        Robot robot = null;
        Rectangle rectangle = null;
        try
        {
            System.out.println("Waiting for client to get connected");
            System.out.println(value1);
            socketServer = new ServerSocket(port);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = "" + dim.getWidth();
            width = width.substring(0, width.lastIndexOf("."));


              height = "" + dim.getHeight();
            height = height.substring(0, height.lastIndexOf("."));

             //test



            rectangle= new Rectangle(dim);
            robot=new Robot(gDev);
            drawGUI();
            while(true)
            {
                Socket sc = socketServer.accept();
                password = new DataInputStream(sc.getInputStream());

                verify = new DataOutputStream(sc.getOutputStream());
                String clientPassword = password.readUTF();
                if(clientPassword.equals(value1))
                {
                    verify.writeUTF("valid");
                    verify.writeUTF(width);verify.writeUTF(height);
                    new SendScreen(sc,robot,rectangle);
                    new ReceiveEvents(sc,robot);


                }
                else {
                    verify.writeUTF("Invalid");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    private void drawGUI() {
        // Thêm mã để vẽ giao diện ở đây (nếu cần)
    }


}
