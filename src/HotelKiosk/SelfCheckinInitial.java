/* SelfCheckinInitial.java
 * 셀프 체크인 초기 화면
 * 예약자명, 예약번호가 Hotel 데이터베이스의 SelfCheckinInfo 테이블에 없다면
 * 경고 팝업창을 띄우며 "예약된 정보가 없습니다. 다시 확인해주세요." 문구 출력
 * 예약자명, 예약번호가 있으면 그 값을 SelfCheckinInfo.java에 전달
 *
 */
package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Flow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 호텔 셀프 체크인 초기 화면

public class SelfCheckinInitial extends JFrame {
    public SelfCheckinInitial() {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH);
        contentPane.add(new CPanel(), BorderLayout.CENTER);
        contentPane.add(new WPanel(), BorderLayout.WEST);
        contentPane.add(new EPanel(), BorderLayout.EAST);
        contentPane.add(new SPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SelfCheckinInitial();
    }

    // 입력 필드 설정
    class RoundedTextField extends JTextField {
        private int arcWidth;
        private int arcHeight;
        private String placeholderText;

        public RoundedTextField(String text, int arcWidth, int arcHeight) {
            super(text);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            this.placeholderText = text;

            setOpaque(false); // 배경을 투명하게 설정
            setBorder(null); // 기본 테두리 제거
            setForeground(Color.GRAY); // 기본 텍스트 색상 설정

            // 포커스 리스너 추가 (텍스트 필드를 클릭하면 텍스트가 사라지고, 포커스를 잃으면 다시 보이게 함)
            addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (getText().equals(placeholderText)) {
                        setText(""); // 텍스트를 비움
                        setForeground(Color.BLACK); // 텍스트 색상을 검정색으로 설정
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(placeholderText); // 텍스트가 없으면 플레이스홀더 텍스트를 다시 설정
                        setForeground(Color.GRAY); // 텍스트 색상을 회색으로 설정
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 배경 색상
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2.dispose();
            super.paintComponent(g);
        }
    }


    // 버튼 둥글게 만드는 클래스
    class RoundedButton extends JButton {
        private int arcWidth;
        private int arcHeight;

        public RoundedButton(String text, int arcWidth, int arcHeight) {
            super(text);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            setOpaque(false); // 배경을 투명하게 설정
            setBorder(null); // 기본 테두리 제거

        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 중앙 패널
    class CPanel extends JPanel {
        public CPanel() {
            setBackground(new Color(104, 90, 90));
            setLayout(new BorderLayout());

            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new GridBagLayout());
            innerPanel.setBackground(new Color(104, 90, 90));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel nameLabel = new JLabel("예약자명");
            nameLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            nameLabel.setForeground(new Color(255, 178, 165));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            innerPanel.add(nameLabel, gbc);

            RoundedTextField nameField = new RoundedTextField(" 예약하신 분의 성함을 입력해 주세요.", 25, 25);
            nameField.setBackground(Color.WHITE);
            nameField.setForeground(Color.GRAY);
            nameField.setPreferredSize(new Dimension(300, 40));
            gbc.gridy = 1;
            innerPanel.add(nameField, gbc);

            JLabel numLabel = new JLabel("예약번호");
            numLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            numLabel.setForeground(new Color(255, 178, 165));
            gbc.gridy = 2;
            innerPanel.add(numLabel, gbc);

            RoundedTextField numField = new RoundedTextField(" 예약번호를 입력해 주세요.", 25, 25);
            numField.setBackground(Color.WHITE);
            numField.setForeground(Color.GRAY);
            numField.setPreferredSize(new Dimension(300, 40));
            gbc.gridy = 3;
            innerPanel.add(numField, gbc);

            RoundedButton searchButt = new RoundedButton("조회하기", 20, 20);
            searchButt.setBackground(new Color(255, 178, 165));
            searchButt.setForeground(new Color(74, 69, 66));
            searchButt.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            searchButt.setPreferredSize(new Dimension(80, 40));
            gbc.gridy = 4;
            innerPanel.add(searchButt, gbc);

            searchButt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText().trim();
                    String reservationNumber = numField.getText().trim();

                    // DatabaseConnection 클래스의 checkReservation 메서드 호출
                    boolean isReservationValid = DatabaseConnection.checkReservation(name, reservationNumber);

                    if (!isReservationValid) {
                        JOptionPane.showMessageDialog(CPanel.this, "예약된 정보가 없습니다. 다시 확인해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 예약 정보가 유효한 경우 다음 화면으로 이동
                    new SelfCheckinInfo(name, reservationNumber);
                    SelfCheckinInitial.this.dispose();
                }
            });


            add(innerPanel, BorderLayout.CENTER);
        }
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
            leftBar.setPreferredSize(new Dimension(250, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(new Color(255, 178, 165));
            rightBar.setPreferredSize(new Dimension(250, 3));

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(74, 69, 66));
            JLabel textLabel = new JLabel("셀프 체크인");
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(new Font("Arial", Font.BOLD, 22)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700,70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }



    class WPanel extends JPanel {
        public WPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class EPanel extends JPanel {
        public EPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class SPanel extends JPanel {
        public SPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(700, 150));
        }
    }

}