package HotelKiosk;
import javax.swing.*;
import java.awt.*;

public class HotelKioskMain extends JFrame {
    public HotelKioskMain() {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new SouthPanel(),BorderLayout.SOUTH);
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new WestPanel(), BorderLayout.WEST); // 왼쪽 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST); // 왼쪽 패널 추가

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new HotelKioskMain();
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

            JLabel leftBar = new JLabel();
            leftBar.setOpaque(true);
            leftBar.setBackground(new Color(255, 178, 165));
            leftBar.setPreferredSize(new Dimension(190, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(new Color(255, 178, 165));
            rightBar.setPreferredSize(new Dimension(190, 3));

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(74, 69, 66));
            JLabel textLabel = new JLabel("원하시는 서비스를 선택해 주세요.");
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

            // 각 예약 항목 패널 추가
            add(createReservationPanel("객실 예약", "객실 예약이 필요하신 손님", new Color(202, 132, 138), new Color(245, 203, 195)));
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(createReservationPanel("체크인", "온라인 예약을 하고오신 손님", new Color(244, 170, 149), new Color(211, 168, 156)));
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(createReservationPanel("체크아웃", "체크아웃을 하시려는 손님", new Color(255, 178, 165), new Color(230, 208, 189)));
        }

        private JPanel createReservationPanel(String title, String description, Color bgColor, Color triangleColor) {
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // 삼각형 그리기
                    g.setColor(triangleColor);
                    int[] xPoints = {getWidth(), getWidth(), getWidth()-55};
                    int[] yPoints = {0, getHeight(), getHeight()};
                    g.fillPolygon(xPoints, yPoints, 3);
                }
            };
            panel.setLayout(new BorderLayout());
            panel.setBackground(bgColor);
            panel.setPreferredSize(new Dimension(680, 100)); // 패널 크기 조절

            JButton titleButton = new JButton(title);
            titleButton.setForeground(Color.WHITE);
            titleButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 26));
            titleButton.setBackground(bgColor);
            titleButton.setBorderPainted(false);
            titleButton.setFocusPainted(false);
            titleButton.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬

            titleButton.addActionListener(e -> {
                switch (title) {
                    case "객실 예약" -> {
                        new ReservationInitial().setVisible(true); // Reservation_1 창 열기

                        SwingUtilities.getWindowAncestor(panel).setVisible(false); // 현재 창 숨기기
                    }
                    case "체크인" -> {
                        new SelfCheckinInitial().setVisible(true);

                        SwingUtilities.getWindowAncestor(panel).setVisible(false); // 현재 창 숨기기
                    }
                    case "체크아웃" -> {
                        new CheckoutInitial().setVisible(true);
                        SwingUtilities.getWindowAncestor(panel).setVisible(false); // 현재 창 숨기기
                    }
                }
            });

            panel.add(titleButton, BorderLayout.CENTER);

            JLabel descriptionLabel = new JLabel(description);
            descriptionLabel.setForeground(new Color(95,70,70));
            descriptionLabel.setFont(new Font("KoPubDotum Medium",Font.ITALIC, 16));
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
            panel.add(descriptionLabel, BorderLayout.SOUTH);

            return panel;
        }
    }
    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
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