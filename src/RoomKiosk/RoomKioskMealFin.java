package RoomKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomKioskMealFin extends JFrame {
    public RoomKioskMealFin() {
        super("Room Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 하단 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST);
        contentPane.add(new WestPanel(), BorderLayout.WEST);

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new RoomKioskMealFin();
    }

    class NorthPanel extends JPanel {
        public NorthPanel() {
            setBackground(new Color(255, 220, 200));
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
            emptyLabel.setBackground(new Color(255, 220, 200));
            emptyLabel.setPreferredSize(new Dimension(700, 40)); // 세로 크기 조절
            add(emptyLabel, gbc); // 첫 번째 셀에 빈 레이블 추가

            // 로고 패널
            gbc.gridy = 1; // 두 번째 행
            gbc.weighty = 0.0; // 로고 패널의 세로 비율
            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            logoPanel.setBackground(new Color(255, 220, 200));
            JLabel logo = new JLabel("");
            ImageIcon icon = new ImageIcon("images/brownLogo.png");
            logo.setIcon(icon);
            logoPanel.add(logo);
            logoPanel.setPreferredSize(new Dimension(700, 100));
            add(logoPanel, gbc); // 두 번째 셀에 로고 패널 추가

            logoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new RoomKioskMain();
                    SwingUtilities.getWindowAncestor(NorthPanel.this).setVisible(false);
                }
            });

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(255, 220, 200));

            JLabel leftBar = new JLabel();
            leftBar.setOpaque(true);
            leftBar.setBackground(Color.WHITE);
            leftBar.setPreferredSize(new Dimension(230, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(Color.WHITE);
            rightBar.setPreferredSize(new Dimension(230, 3));

            JLabel textLabel = new JLabel("   조식권 구매   ");
            textLabel.setForeground(new Color(95, 70, 70));
            textLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 24)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700, 70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }


    class CenterPanel extends JPanel {
        public CenterPanel() {
            setBackground(new Color(255, 236, 236));
            setLayout(new GridBagLayout()); // GridBagLayout 사용

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 간 간격
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.CENTER;

            JPanel titlePanel = new JPanel();
            titlePanel.setPreferredSize(new Dimension(80, 40)); // 직사각형 크기 설정
            titlePanel.setBackground(new Color(229, 158, 138)); // 직사각형 배경색
            titlePanel.setLayout(new GridBagLayout()); // 중앙 정렬

            JLabel roomLabel = new JLabel("구매 완료");
            roomLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            roomLabel.setForeground(Color.WHITE); // 텍스트 색상
            titlePanel.add(roomLabel); // 텍스트를 패널에 추가
            gbc.gridy = 0; // 첫 번째 행
            add(titlePanel, gbc);

            ImageIcon icon = new ImageIcon("images/ticket.png");
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            JLabel ticketLabel = new JLabel(scaledIcon);
            gbc.gridy = 1;
            add(ticketLabel, gbc);


            JLabel completeMessage = new JLabel("구매가 완료되었습니다.");
            completeMessage.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            completeMessage.setForeground(new Color(132, 107, 100));
            gbc.gridy = 2; // 세 번째 행
            add(completeMessage, gbc);

            JLabel subMessage = new JLabel("구매하신 조식권은 체크아웃 전까지 사용 가능합니다.");
            subMessage.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 16));
            subMessage.setForeground(new Color(212, 159, 159));
            gbc.gridy = 3; // 네 번째 행
            add(subMessage, gbc);

            // 중앙에 패널 배치 정렬
            setPreferredSize(new Dimension(700, 500));
        }
    }
    class WestPanel extends JPanel {
        public WestPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class EastPanel extends JPanel {
        public EastPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }
    class SouthPanel extends JPanel {
        private int remainingTime = 10; // 초기 카운트다운 시간

        public SouthPanel() {
            setBackground(new Color(255, 220, 200));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 수직 정렬
            setPreferredSize(new Dimension(700, 300));

            // 상단 여백
            add(Box.createVerticalGlue());

            // 중앙에 배치할 JLabel
            JLabel infoLabel = new JLabel("10초 뒤 메인 화면으로 돌아갑니다.");
            infoLabel.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 16));
            infoLabel.setForeground(new Color(132, 107, 100));
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            add(infoLabel);

            add(Box.createVerticalGlue());

            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (remainingTime > 0) {
                        remainingTime--;
                        infoLabel.setText(remainingTime + "초 뒤 메인 화면으로 돌아갑니다.");
                    } else {
                        ((Timer) e.getSource()).stop();
                        new RoomKioskMain();
                        ((JFrame) SwingUtilities.getWindowAncestor(SouthPanel.this)).dispose();

                    }
                }
            });
            timer.start();
        }
    }

    // 모시러 10만큼 깎인 버튼
    class RoundedButtonTen extends JButton {
        public RoundedButtonTen(String label) {
            super(label);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // 모서리를 둥글게
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 40); // 기본 크기 설정
        }
    }

}