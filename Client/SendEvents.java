package Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendEvents implements KeyListener, MouseListener, MouseMotionListener {
    private Socket socketClient = null; // Biến socketClient để thực hiện kết nối với máy chủ
    private JPanel cPanel = null; // JPanel để theo dõi sự kiện từ giao diện
    private PrintWriter writer = null; // PrintWriter để gửi dữ liệu đến máy chủ
    String width = "", height = ""; // Kích thước màn hình
    double w; // Biến lưu trữ chiều rộng màn hình
    double h; // Biến lưu trữ chiều cao màn hình

    public SendEvents(Socket s, JPanel p, String width, String height) {
        this.socketClient = s; // Gán socketClient
        this.cPanel = p; // Gán cPanel
        this.width = width;
        this.height = height;
        this.w = Double.valueOf(width.trim()).doubleValue(); // Chuyển đổi và gán kích thước màn hình vào biến w
        this.h = Double.valueOf(height.trim()).doubleValue(); // Chuyển đổi và gán kích thước màn hình vào biến h

        // Thêm các lệnh dưới đây để cPanel theo dõi sự kiện từ bàn phím và chuột
        cPanel.addKeyListener(this); // Theo dõi sự kiện từ bàn phím
        cPanel.addMouseListener(this); // Theo dõi sự kiện từ chuột
        cPanel.addMouseMotionListener(this); // Theo dõi sự kiện di chuyển chuột

        try {
            writer = new PrintWriter(socketClient.getOutputStream()); // Khởi tạo đối tượng PrintWriter để gửi dữ liệu đến máy chủ
        } catch (IOException ex) {
            ex.printStackTrace(); // In ra lỗi nếu có lỗi khi tạo PrintWriter
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Xử lý sự kiện khi có phím được gõ
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Xử lý sự kiện khi một phím được nhấn xuống
        writer.println(Commands.PRESS_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Xử lý sự kiện khi một phím được nhả ra
        writer.println(Commands.RELEASE_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Xử lý sự kiện khi chuột được nhấn và nhả ra nhanh chóng
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Xử lý sự kiện khi nút chuột được nhấn

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        writer.println(Commands.RELEASE_MOUSE.getAbbrev()); // Gửi lệnh nhấn chuột đến máy chủ
        int button = e.getButton(); // Lấy loại nút chuột được nhấn
        int xButton = 6; // Xác định giá trị mặc định cho nút chuột

        if (button == 3) {
            xButton = 4; // Nếu là nút phải chuột, gán giá trị xButton là 4
        }

        writer.println(xButton); // Gửi giá trị xButton đến máy chủ
        writer.flush(); // Gửi dữ liệu đi
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Xử lý sự kiện khi chuột vào vùng cPanel
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Xử lý sự kiện khi chuột ra khỏi vùng cPanel
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Xử lý sự kiện khi chuột được kéo (drag)
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Xử lý sự kiện khi chuột di chuyển
        double xScale = (double) w / cPanel.getWidth(); // Tính tỷ lệ xScale
        double yScale = (double) h / cPanel.getHeight(); // Tính tỷ lệ yScale

        writer.println(Commands.MOVE_MOUSE.getAbbrev()); // Gửi lệnh di chuyển chuột đến máy chủ
        writer.println((int) (e.getX() * xScale)); // Gửi tọa độ X được điều chỉnh theo tỷ lệ
        writer.println((int) (e.getY() * yScale)); // Gửi tọa độ Y được điều chỉnh theo tỷ lệ
        writer.flush(); // Gửi dữ liệu đi
    }
}
