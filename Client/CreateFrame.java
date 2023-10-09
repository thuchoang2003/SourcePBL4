package Client;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;

public class CreateFrame extends Thread{
    String width = "";
    String height = "";
    private JFrame frame = new JFrame();
    private JDesktopPane desktop = new JDesktopPane();
    private Socket socketClient = null;
    private JInternalFrame internalFrame = new JInternalFrame("Server Screen",true,true,true);
    private JPanel cPanel = new JPanel();
    public CreateFrame(Socket socketClient,String width,String height)
    {
        this.width = width;
        this.height= height;
        this.socketClient = socketClient;
        start();
    }
    public void drawGUI()
    {
        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());

        internalFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        internalFrame.setSize(Integer.parseInt(width), Integer.parseInt(height));
        desktop.add(internalFrame);
        try{
            internalFrame.setMaximum(true);

        }
        catch(PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        cPanel.setFocusable(true);
        internalFrame.setVisible(true);

    }

    public void run()
    {
        InputStream in = null;
        drawGUI();
        try{
            in=socketClient.getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        new ReceivingScreen(in,cPanel);
        new SendEvents(socketClient,cPanel,width,height);
    }

}
//    Lớp CreateFrame trong ứng dụng remote desktop của bạn có nhiệm vụ tạo và quản lý cửa sổ hiển thị màn hình máy chủ.
//
//        Giải thích từng phần của lớp sau khi sửa lỗi:
//
//private String width = ""; và private String height = "";: Đây là biến để lưu trữ chiều rộng và chiều cao của cửa sổ màn hình máy chủ.
//
//private JFrame frame = new JFrame();: Tạo một cửa sổ chính (JFrame) để chứa cửa sổ hiển thị màn hình máy chủ.
//
//private JDesktopPane desktop = new JDesktopPane();: Tạo một JDesktopPane để quản lý các cửa sổ hiển thị màn hình máy chủ trên cửa sổ chính.
//
//private Socket socketClient = null;: Biến này lưu trữ kết nối socket với máy chủ.
//
//private JInternalFrame internalFrame = new JInternalFrame("Server Screen", true, true, true);: Tạo một cửa sổ nội bộ (JInternalFrame) để hiển thị màn hình máy chủ. Cửa sổ này có tiêu đề là "Server Screen" và có các nút điều khiển (true, true, true) để tối thiểu hóa, tối đa hóa và đóng cửa sổ.
//
//private JPanel cPanel = new JPanel();: Tạo một JPanel để chứa nội dung cần hiển thị trong cửa sổ nội bộ.
//
//        Constructor CreateFrame(Socket socketClient, String width, String height): Constructor này nhận đối số là kết nối socket với máy chủ (socketClient) và kích thước màn hình máy chủ (chiều rộng và chiều cao) để cấu hình cửa sổ màn hình máy chủ.
//
//public void drawGUI(): Phương thức này được sử dụng để tạo và cấu hình giao diện người dùng. Nó thêm desktop vào cửa sổ chính, đặt cài đặt cửa sổ chính và cài đặt nội dung cho cửa sổ nội bộ (internalFrame), sau đó thêm internalFrame vào desktop.
//
//        Khi bạn gọi drawGUI(), nó sẽ tạo một cửa sổ chính cho ứng dụng remote desktop, trong đó sẽ hiển thị màn hình máy chủ. Cửa sổ này có thể được tối đa hóa để hiển thị màn hình máy chủ với kích thước được chỉ định (chiều rộng và chiều cao), và bạn có thể tương tác với nó thông qua các nút điều khiển