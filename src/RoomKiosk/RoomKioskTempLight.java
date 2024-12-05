package RoomKiosk;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomKioskTempLight extends JFrame {
    private static Integer nowTemp = 22; // 기본 온도 22
    private static Boolean nowLight1 = false;
    private static Boolean nowLight2 = false;
    private static Boolean nowLight3 = false;
    public RoomKioskTempLight() {
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
        new RoomKioskTempLight();
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
            leftBar.setPreferredSize(new Dimension(200, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(Color.WHITE);
            rightBar.setPreferredSize(new Dimension(200, 3));

            JLabel textLabel = new JLabel("   온도 / 조명 설정   ");
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
        //int temperature = 0;
        public CenterPanel() {
            setBackground(new Color(255, 220, 200));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 배치

            // 온도/조명 Panel 추가
            add(createTempPanel(nowTemp, new Color(255,236,236)));
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(createLightPanel(true, new Color(255,236,236)));
            add(Box.createRigidArea(new Dimension(0, 30)));
        }

        // 온도 전용 Panel
        private JPanel createTempPanel(int temp, Color bgColor) {
            nowTemp = temp;
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout()); // GridBagLayout 사용
            panel.setBackground(bgColor);

            LineBorder outerBorder = new LineBorder(new Color(203,134,136), 5); // 외부 테두리
            EmptyBorder innerPadding = new EmptyBorder(5, 5, 5, 5); // 내부 여백
            LineBorder innerBorder = new LineBorder(new Color(203,134,136), 2); // 내부 테두리

            // CompoundBorder를 사용하여 두 테두리 결합
            panel.setBorder(new CompoundBorder(outerBorder, new CompoundBorder(innerPadding, innerBorder)));
            panel.setPreferredSize(new Dimension(680, 120)); // 패널 크기 조절

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // 여백 설정
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // '현재 온도' 버튼
            RoundedButtonTen paymentButton = new RoundedButtonTen("현재 온도");
            paymentButton.setPreferredSize(new Dimension(200, 25)); // 버튼 크기 조정
            paymentButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            paymentButton.setBackground(new Color(229,158,138)); // 버튼 배경색
            paymentButton.setForeground(Color.WHITE);
            panel.add(paymentButton);

            // 현재 온도가 적힌 레이블
            JLabel now_temp = createLabel(nowTemp+"℃");
            now_temp.setForeground(new Color(203,134,136));
            now_temp.setFont(new Font("KoPubDotum Bold", Font.BOLD, 100));
            now_temp.setPreferredSize(new Dimension(280, 100)); // 크기 조정
            gbc.gridx = 1;
            panel.add(now_temp, gbc);

            // 온도 조절용 UP/DOWN 버튼 생성
            TempButton up_button = new TempButton("▲"); // UP
            up_button.setForeground(new Color(229,158,138));
            up_button.setBackground(new Color(255,236,236));
            up_button.setFont(new Font("KoPubDotum Bold", Font.BOLD, 30));
            up_button.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            up_button.setPreferredSize(new Dimension(70, 70));

            TempButton down_button = new TempButton("▼"); // DOWN
            down_button.setForeground(new Color(229,158,138));
            down_button.setBackground(new Color(255,236,236));
            down_button.setFont(new Font("KoPubDotum Bold", Font.BOLD, 30));

            down_button.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            down_button.setPreferredSize(new Dimension(50, 35));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(255,236,236));
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));  // 수직 배치
            buttonPanel.setBorder(new LineBorder(new Color(229,158,138), 3)); // 두께 3픽셀의 테두리

            buttonPanel.add(up_button);
            buttonPanel.add(down_button);
            gbc.gridx = 2;
            //panel.add(buttonPanel);
            panel.add(buttonPanel,gbc);

            // 온도 증가 액션
            up_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nowTemp++;
                    now_temp.setText(nowTemp+"℃");
                }
            });

            // 온도 감소 액션
            down_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nowTemp--;
                    now_temp.setText(nowTemp+"℃");
                }
            });


            return panel;
        }


        // 조명 전용 패널
        private JPanel createLightPanel(boolean power, Color bgColor) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout()); // GridBagLayout 사용
            panel.setBackground(bgColor);

            LineBorder outerBorder = new LineBorder(new Color(203,134,136), 5); // 외부 테두리
            EmptyBorder innerPadding = new EmptyBorder(5, 5, 5, 5); // 내부 여백
            LineBorder innerBorder = new LineBorder(new Color(203,134,136), 2); // 내부 테두리

            // CompoundBorder를 사용하여 두 테두리 결합
            panel.setBorder(new CompoundBorder(outerBorder, new CompoundBorder(innerPadding, innerBorder)));
            panel.setPreferredSize(new Dimension(680, 250)); // 패널 크기 조절

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // 여백 설정
            gbc.anchor = GridBagConstraints.CENTER;  // 중앙 정렬
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // 통합 Panel (버튼끼리 합치기 전용)
            JPanel listPanel = new JPanel();
            listPanel.setBackground(bgColor);
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));  // 수평 배치
            listPanel.setAlignmentX(CENTER_ALIGNMENT);
            listPanel.setPreferredSize(new Dimension(600, 200)); // 두 버튼을 위한 크기
            listPanel.setMaximumSize(new Dimension(600, 200)); // 최대 크기 설정

            // 스위치 목록 Panel
            JPanel switchPanel = new JPanel();
            switchPanel.setBackground(bgColor);
            switchPanel.setLayout(new BoxLayout(switchPanel, BoxLayout.X_AXIS));  // 수평 배치
            switchPanel.setAlignmentX(CENTER_ALIGNMENT);
            switchPanel.setPreferredSize(new Dimension(600, 150)); // 두 버튼을 위한 크기
            switchPanel.setMaximumSize(new Dimension(600, 150)); // 최대 크기 설정

            switchPanel.add(Box.createRigidArea(new Dimension(10, 0))); // 두 버튼 사이 간격
            EclipseButton switch_button1 = createSwitchButton(nowLight1, new Color(138, 89, 75));
            switchPanel.add(switch_button1);
            switchPanel.add(Box.createRigidArea(new Dimension(30, 0))); // 두 버튼 사이 간격
            EclipseButton switch_button2 = createSwitchButton(nowLight2, new Color(216,188,165));
            switchPanel.add(switch_button2);
            switchPanel.add(Box.createRigidArea(new Dimension(30, 0))); // 두 버튼 사이 간격
            EclipseButton switch_button3 = createSwitchButton(nowLight3, new Color(249,142,145));
            switchPanel.add(switch_button3);
            switchPanel.add(Box.createRigidArea(new Dimension(10, 0))); // 두 버튼 사이 간격
            listPanel.add(switchPanel); // 패널에 추가

            listPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 두 버튼 사이 간격

            // 조명 이름 목록 Panel
            JPanel switchNamePanel = new JPanel();
            switchNamePanel.setBackground(bgColor);
            switchNamePanel.setLayout(new BoxLayout(switchNamePanel, BoxLayout.X_AXIS));  // 수평 배치
            switchNamePanel.setAlignmentX(CENTER_ALIGNMENT);
            switchNamePanel.setPreferredSize(new Dimension(600, 40)); // 두 버튼을 위한 크기
            switchNamePanel.setMaximumSize(new Dimension(600, 50)); // 최대 크기 설정

            switchNamePanel.add(Box.createRigidArea(new Dimension(10, 0))); // 두 버튼 사이 간격
            LightNameButton lightName1 = createSwitchNameButton("조명1", new Color(138, 89, 75));
            switchNamePanel.add(lightName1);
            switchNamePanel.add(Box.createRigidArea(new Dimension(30, 0))); // 두 버튼 사이 간격
            LightNameButton lightName2 = createSwitchNameButton("조명2", new Color(216,188,165));
            switchNamePanel.add(lightName2);
            switchNamePanel.add(Box.createRigidArea(new Dimension(30, 0))); // 두 버튼 사이 간격
            LightNameButton lightName3 = createSwitchNameButton("조명3", new Color(249,142,145));
            switchNamePanel.add(lightName3);
            switchNamePanel.add(Box.createRigidArea(new Dimension(10, 0))); // 두 버튼 사이 간격
            listPanel.add(switchNamePanel);

            panel.add(listPanel, gbc);

            // Button 클릭 이벤트 처리
            switch_button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nowLight1 == false) {
                        nowLight1 = true;
                    }
                    else {
                        nowLight1 = false;
                    }
                }
            });
            switch_button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nowLight2 == false) {
                        nowLight2 = true;
                    }
                    else {
                        nowLight2 = false;
                    }
                }
            });



            // Button 클릭 이벤트 처리
            switch_button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nowLight3 == false) {
                        nowLight3 = true;
                    }
                    else {
                        nowLight3 = false;
                    }
                }
            });


            return panel;
        }

        // 스위치 버튼 추가용 함수 (ON/OFF 여부, 스위치 색)
        private EclipseButton createSwitchButton(boolean power, Color fgColor) {
            EclipseButton switchButton = new EclipseButton("", power);
            switchButton.setForeground(fgColor);
            switchButton.setPreferredSize(new Dimension(150, 150)); // 버튼 크기 설정
            switchButton.setLayout(new BoxLayout(switchButton, BoxLayout.Y_AXIS)); // 수직 배치
            switchButton.setAlignmentY(Component.CENTER_ALIGNMENT); // 수평 중앙 정렬
            //switchButton.addActionListener(new MyActionListener(switchButton, power));

            // 텍스트 라벨 생성
            JLabel powerLabel = new JLabel(power ? "ON" : "OFF");
            powerLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 35));
            powerLabel.setForeground(Color.WHITE);
            powerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            // 동그란 ON/OFF 표시용 버튼 생성
            OnOffSwitch powerButton = new OnOffSwitch("");
            powerButton.setForeground(power ? new Color(138, 255, 111) : new Color(248,40,43));
            powerButton.setPreferredSize(new Dimension(40, 40)); // 버튼 크기 조정
            powerButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            powerButton.setAlignmentY(Component.CENTER_ALIGNMENT); // 중앙 정렬

            // 버튼 안에 동그란 버튼과 텍스트를 추가
            switchButton.add(Box.createRigidArea(new Dimension(0, 35))); // 버튼과 텍스트 사이 간격
            switchButton.add(powerButton);

            switchButton.add(Box.createRigidArea(new Dimension(0, 10))); // 버튼과 텍스트 사이 간격
            switchButton.add(powerLabel);

            // Button 클릭 이벤트 처리
            switchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(powerLabel.getText().equals("OFF")) {
                        powerButton.setForeground(new Color(138, 255, 111));
                        powerLabel.setText("ON");
                    }
                    else {
                        powerLabel.setText("OFF");
                        powerButton.setForeground(new Color(248,40,43));
                    }
                }
            });


            return switchButton;
        }

        // 조명 이름용
        private LightNameButton createSwitchNameButton(String name, Color bgColor) {
            LightNameButton ButtonName = new LightNameButton(name);
            ButtonName.setForeground(Color.WHITE);
            ButtonName.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 조정
            ButtonName.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            ButtonName.setBackground(bgColor); // 버튼 배경색
            ButtonName.setLayout(new BoxLayout(ButtonName, BoxLayout.Y_AXIS)); // 수직 배치
            ButtonName.setAlignmentY(Component.CENTER_ALIGNMENT); // 수평 중앙 정렬

            return ButtonName;
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18)); // 글자 크기 조정
            return label;
        }
    }

    class SouthPanel extends JPanel {
        public SouthPanel() {
            setBackground(new Color(255, 220, 200));
            RoundedButton paymentButton = new RoundedButton("설정 완료");
            paymentButton.setPreferredSize(new Dimension(200, 25)); // 버튼 크기 조정
            paymentButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 22));
            paymentButton.setBackground(new Color(254, 190, 152)); // 버튼 배경색
            paymentButton.setForeground(Color.WHITE);
            add(paymentButton);
            add(new JLabel("       "));
            setPreferredSize(new Dimension(0, 100));
            paymentButton.addActionListener(e -> goBack());
        }
    }

    // 모시러 20만큼 깎인 버튼
    class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 모서리를 둥글게
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(270, 60); // 기본 크기 설정
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

    // 온도 조절에 사용된 버튼
    class TempButton extends JButton {
        public TempButton(String label) {
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

    }

    // 조명 스위치 전용으로 쓰인 버튼
    class LightNameButton extends JButton {
        public LightNameButton(String label) {
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
            return new Dimension(200, 50); // 기본 크기 설정
        }
    }

    // 원형 버튼 (조명 스위치 이용)
    class EclipseButton extends JButton {
        private boolean onPower;
        public EclipseButton(String label, boolean onPower) {
            super(label);
            this.onPower = onPower;
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false); // 배경을 투명하게 만듬
            setOpaque(false); // 배경을 완전히 투명하게 만듬
        }

        @Override
        protected void paintComponent(Graphics g) {
            // 버튼 배경을 원형으로 그리기
            g.fillOval(0, 0, getWidth(), getHeight());  // 원을 그리기
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 150); // 버튼 크기 지정
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(150, 150); // 최소 크기 지정
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(150, 150); // 최대 크기 지정
        }
    }

    class OnOffSwitch extends JButton {
        public OnOffSwitch(String label) {
            super(label);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false); // 배경을 투명하게 만듬
            setOpaque(false); // 배경을 완전히 투명하게 만듬
        }

        @Override
        protected void paintComponent(Graphics g) {
            // 버튼 배경을 원형으로 그리기
            g.fillOval(0, 0, getWidth(), getHeight());  // 원을 그리기
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(10, 10); // 버튼 크기 지정
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(10, 10); // 최소 크기 지정
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(10, 10); // 최대 크기 지정
        }
    }

    // 메인 화면으로
    private void goBack() {
        new RoomKioskTempLightFin();
        dispose(); // 창 닫기
    }
    public static Integer getNowTemp() {
        return nowTemp;
    }

    public static Boolean getNowLight1() {
        return nowLight1;
    }

    public static Boolean getNowLight2() {
        return nowLight2;
    }

    public static Boolean getNowLight3() {
        return nowLight3;
    }

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(60, 300));
        }
    }
    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(60, 300));
        }
    }
}