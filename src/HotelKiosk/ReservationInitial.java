package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationInitial extends JFrame {
    public static String selectedRoomType = "";
    public ReservationInitial() {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레 임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH);
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new WestPanel(), BorderLayout.WEST); // 왼쪽 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST); // 왼쪽 패널 추가

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new ReservationInitial();
    }

    class NorthPanel extends JPanel {
        public NorthPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new GridBagLayout()); // GridBagLayout 사용

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0; // 가로 방향으로 확대

            // 빈 레이블 추가
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 0.1; // 빈 레이블의 세로 비율
            JLabel emptyLabel = new JLabel();
            emptyLabel.setOpaque(true);
            emptyLabel.setBackground(new Color(74, 69, 66));
            emptyLabel.setPreferredSize(new Dimension(700, 40)); // 세로 크기 조절
            add(emptyLabel, gbc); // 첫 번째 셀에 빈 레이블 추가

            // 로고 패널
            gbc.gridy = 1; // 두 번째 행
            gbc.weighty = 0.0; // 로고 패널의 세로 비율
            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            logoPanel.setBackground(new Color(74, 69, 66));
            JLabel logo = new JLabel("");
            ImageIcon icon = new ImageIcon("images/logo.png");
            logo.setIcon(icon);
            logoPanel.add(logo);
            logoPanel.setPreferredSize(new Dimension(700,100));
            add(logoPanel, gbc); // 두 번째 셀에 로고 패널 추가

            logoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new HotelKioskMain();
                    SwingUtilities.getWindowAncestor(NorthPanel.this).setVisible(false);
                }
            });

            JLabel leftBar = new JLabel();
            leftBar.setOpaque(true);
            leftBar.setBackground(new Color(255, 178, 165));
            leftBar.setPreferredSize(new Dimension(270, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(new Color(255, 178, 165));
            rightBar.setPreferredSize(new Dimension(270, 3));

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(74, 69, 66));
            JLabel textLabel = new JLabel("현장 예약");
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(new Font("Arial", Font.BOLD, 22)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700,70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }


    class CenterPanel extends JPanel {
        public CenterPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 배치

            add(createRoomPanel("스탠다드 싱글 ", "120,000원", "images/room1.png"));
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(createRoomPanel("스탠다드 트윈 ", "150,000원", "images/room2.jpg"));
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(createRoomPanel("디럭스 더블 ", "210,000원", "images/room3.jpeg"));
        }

        private JPanel createRoomPanel(String roomType, String price, String imagePath) {
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // 이미지 배경 그리기
                    ImageIcon roomImage = new ImageIcon(imagePath);
                    g.drawImage(roomImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            panel.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(680, 150));

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ReservationInitial.selectedRoomType = roomType; // 선택된 방 정보를 저장
                    new ReservationInfo(); // ReservationInfo 창으로 이동
                    SwingUtilities.getWindowAncestor(panel).setVisible(false); // 현재 창 닫기
                }
            });

            // 반투명 배경 패널 추가
            JPanel overlay = new JPanel();
            overlay.setBackground(new Color(126, 99, 99)); // 반투명 배경
            overlay.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 오른쪽 정렬 설정

            // 방 이름과 가격을 담을 패널
            JLabel roomTypeLabel = new JLabel(roomType);
            roomTypeLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 22));
            roomTypeLabel.setForeground(new Color(254,190,152));
            overlay.add(roomTypeLabel);

            JLabel priceLabel = new JLabel(price);
            priceLabel.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 18));
            priceLabel.setForeground(new Color(254,190,152));
            overlay.add(priceLabel);

            panel.add(overlay, BorderLayout.SOUTH);
            return panel;
        }

    }
    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(75, 300));
        }
    }

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(75, 300));
        }
    }
    class SouthPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public SouthPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(80, 35));
        }
    }
}