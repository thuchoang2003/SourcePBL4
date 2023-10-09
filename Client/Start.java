package Client;

import javax.swing.*;
import java.net.Socket;

public class Start {
    static String port = "6996";

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("please enter server ip");
        new Start().initialize(ip,Integer.parseInt(port));
    }
    public void initialize(String ip, int port)
    {
        try{
            Socket socketClient = new Socket(ip,port);
            System.out.println("Connecting to the server");
            Auth frame1 = new Auth(socketClient);
            frame1.setSize(300,80);
            frame1.setLocation(500,300);
            frame1.setVisible(true);
            frame1.setDefaultCloseOperation(3);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
//    Khởi tạo biến port:
//
//        Dòng static String port = "6996"; khai báo một biến static là port và gán giá trị mặc định là chuỗi "6996". Biến này lưu trữ số cổng mà client sẽ sử dụng để kết nối với máy chủ.
//        Phương thức main:
//
//        Phương thức main là điểm khởi đầu của chương trình. Nó sẽ được gọi khi bạn chạy ứng dụng.
//        Dòng String ip = JOptionPane.showInputDialog("please enter server ip"); sử dụng giao diện đồ hoạ Swing để hiển thị hộp thoại yêu cầu người dùng nhập địa chỉ IP của máy chủ. Giá trị được người dùng nhập sẽ được lưu vào biến ip.
//        Phương thức initialize:
//
//        Phương thức initialize nhận vào địa chỉ IP của máy chủ và số cổng cần kết nối.
//        Dòng Socket socketClient = new Socket(ip, port); tạo một kết nối socket tới máy chủ với địa chỉ IP và số cổng đã được nhập. Điều này tạo ra một đối tượng Socket để thiết lập kết nối với máy chủ.
//        Dòng Authentication frame1 = new Authentication(socketClient); tạo một đối tượng của lớp Authentication. Lớp này có thể giả định là đại diện cho giao diện xác thực của ứng dụng remote desktop.
//        Dòng frame1.setSize(300, 80); và frame1.setLocation(500, 300); đặt kích thước và vị trí của cửa sổ Authentication.
//        Dòng frame1.setVisible(true); hiển thị cửa sổ Authentication lên màn hình.
//        Dòng frame1.setDefaultCloseOperation(3); đặt cách xử lý khi cửa sổ được đóng (3 ứng với JFrame.EXIT_ON_CLOSE).
//        Xử lý ngoại lệ:
//
//        Trong trường hợp xảy ra lỗi, phần mã trong khối catch sẽ được thực thi và thông tin về lỗi sẽ được in ra màn hình dưới dạng stack trace để debug.
//        Lớp Start này chứa logic cơ bản để tạo một kết nối socket tới máy chủ và hiển thị giao diện xác thực. Tùy thuộc vào ứng dụng của bạn, bạn có thể cần thay đổi lớp Authentication và thêm nhiều tính năng khác để hoàn thiện ứng dụng remote desktop của mình.
