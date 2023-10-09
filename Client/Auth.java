package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Auth extends JFrame implements ActionListener {
    private Socket socketClient = null;
    DataInputStream verification = null;
    DataOutputStream passchk = null;
    String verify = "";
    JButton submit;
    JLabel label,label1;
    JPanel panel;
    String width = "",height = "";
    JTextField text1;
    public Auth(Socket socketClient)
    {
        this.socketClient = socketClient;
        label1= new JLabel("Password");
        text1 = new JTextField(15);
        label = new JLabel("");
        this.setLayout(new BorderLayout());
        submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(2,1));
        panel.add(label);
        panel.add(label1);
        panel.add(submit);
        panel.add(text1);
        this.add(panel,BorderLayout.CENTER);
        submit.addActionListener(this);
        this.setTitle("Login form");
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String value1 = text1.getText();
        System.out.println(value1);
        try{
            passchk= new DataOutputStream(socketClient.getOutputStream());
            verification = new DataInputStream(socketClient.getInputStream());
            passchk.writeUTF(value1);
            verify = verification.readUTF();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();

        }
        if(verify.equals("valid"))
        {
            try{
                width = verification.readUTF();
                height = verification.readUTF();

            }
            catch(IOException excep)
            {
                excep.printStackTrace();
            }
            CreateFrame abc = new CreateFrame(socketClient,width,height);
            dispose();
        }
        else {
            System.out.println("Please enter valid password");
            JOptionPane.showMessageDialog(this,"Password is incorrect","Error",JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}

//    Biến và Đối tượng:
//
//        Socket socketClient: Đây là một đối tượng socket được sử dụng để giao tiếp với máy chủ.
//        DataInputStream verification và DataOutputStream passchk: Đối tượng DataInputStream được sử dụng để đọc dữ liệu từ socket và DataOutputStream được sử dụng để ghi dữ liệu vào socket.
//        String verify: Biến này sẽ chứa kết quả của quá trình xác thực.
//        Giao diện đồ hoạ Swing:
//
//        JFrame là cửa sổ chính của ứng dụng.
//        JButton submit: Nút "Submit" để người dùng nhấn sau khi nhập mật khẩu.
//        JLabel label và JLabel label1: Nhãn để hiển thị thông báo và nhãn "Password".
//        JPanel panel: Một panel để chứa các thành phần giao diện người dùng.
//        JTextField text1: Ô văn bản để người dùng nhập mật khẩu.
//        Constructor Authentication:
//
//        Constructor này được gọi khi một đối tượng Authentication mới được tạo.
//        Nó khởi tạo giao diện đồ hoạ và các thành phần, bao gồm nút "Submit" và ô văn bản để nhập mật khẩu.
//        Nó thiết lập sự kiện lắng nghe cho nút "Submit" để xử lý khi người dùng nhấn vào nút này.
//        Đặt tiêu đề của cửa sổ là "Login form".
//        Phương thức actionPerformed:
//
//        Được gọi khi người dùng nhấn vào nút "Submit".
//        Lấy giá trị mật khẩu mà người dùng đã nhập vào ô văn bản.
//        Tạo DataOutputStream (passchk) để gửi mật khẩu tới máy chủ thông qua socketClient.
//        Đọc kết quả xác thực từ máy chủ thông qua DataInputStream (verification).
//        Nếu kết quả xác thực là "Valid", lớp CreateFrame được tạo để khởi động chức năng remote desktop với kích thước được truyền từ máy chủ và cửa sổ xác thực bị đóng (dispose()).
//        Nếu xác thực không thành công, hiển thị thông báo lỗi và đóng cửa sổ xác thực.
//        Lớp Authentication này chịu trách nhiệm xác thực người dùng và tương tác với máy chủ để kiểm tra mật khẩu. Sau khi xác thực thành công, nó chuyển quyền điều khiển đến lớp CreateFrame để tạo cửa sổ remote desktop.
