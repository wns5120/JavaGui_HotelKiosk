package RoomKiosk;
import HotelKiosk.HotelKioskMain;
import HotelKiosk.ReservationInitial;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class RoomKioskAlarmList extends JFrame {
    private AlarmDatabase alarmDatabase;

    public RoomKioskAlarmList() {
        super("Room Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정

        //알람데이터베이스 객체 초기화
        alarmDatabase = new AlarmDatabase();

        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(alarmDatabase), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 하단 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST);
        contentPane.add(new WestPanel(), BorderLayout.WEST);


        //스크롤 추가
        CenterPanel centerPanel = new CenterPanel(alarmDatabase);
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scrollPane, BorderLayout.CENTER);

        setVisible(true); // 프레임을 화면에 표시


    }

    public static void main(String[] args) {
        new RoomKioskAlarmList();
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
            leftBar.setPreferredSize(new Dimension(240, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(Color.WHITE);
            rightBar.setPreferredSize(new Dimension(240, 3));

            JLabel textLabel = new JLabel("   알람 목록   ");
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
        private AlarmDatabase alarmDatabase;

        public CenterPanel(AlarmDatabase alarmDatabase) {
            this.alarmDatabase = alarmDatabase;
            setBackground(new Color(255, 220, 200));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 배치


            //알람데이터 가져오기
            List<Alarm> alarms = alarmDatabase.getAlarms();

            for(Alarm alarm : alarms) {
                boolean isDay = alarm.get_ampm().equalsIgnoreCase("오전");
                add(createAlarmList(alarm, new Color(251, 173, 151)));
                add(Box.createRigidArea(new Dimension(0,  20))); // 간격 추가
            }
        }
        // 알람 목록 Panel
        private JPanel createAlarmList(Alarm alarm, Color bgColor) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout()); // GridBagLayout 사용
            panel.setBackground(bgColor);

            int hour = alarm.get_hour(); // Alarm 객체에서 시간 가져오기
            int minute = alarm.get_minute(); // Alarm 객체에서 분 가져오기
            boolean isDay = alarm.get_ampm().equalsIgnoreCase("오전"); // 오전/오후 여부
            boolean onAlarm = alarm.isOn(); // Alarm 객체에서 알람 상태 가져오기

            LineBorder outerBorder = new LineBorder(new Color(255,236,236), 5); // 외부 테두리
            EmptyBorder innerPadding = new EmptyBorder(5, 5, 5, 5); // 내부 여백
            LineBorder innerBorder = new LineBorder(new Color(255,236,236), 2); // 내부 테두리

            // CompoundBorder를 사용하여 두 테두리 결합
            panel.setBorder(new CompoundBorder(outerBorder, new CompoundBorder(innerPadding, innerBorder)));
            panel.setPreferredSize(new Dimension(500, 80)); // 패널 크기 조절

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // 여백 설정
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // 알람이 적용되는 요일이 적힌 레이블
            JLabel dayAlarm = createLabel(getDays(hour, minute, isDay));
            dayAlarm.setForeground(new Color(255,236,236));
            dayAlarm.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            dayAlarm.setPreferredSize(new Dimension(50, 40)); // 크기 조정
            dayAlarm.setHorizontalAlignment(SwingConstants.LEFT); // 왼쪽 정렬
            gbc.gridx = 0;
            panel.add(dayAlarm, gbc);

            // 알람 적용 시간이 적힌 레이블
            JLabel time_alarm = createLabel(hour + ":" + minute);
            time_alarm.setForeground(new Color(255,236,231));
            time_alarm.setFont(new Font("KoPubDotum Bold", Font.BOLD, 50));
            time_alarm.setVerticalAlignment(SwingConstants.CENTER);  // 세로로 중앙 정렬
            time_alarm.setPreferredSize(new Dimension(150, 70)); // 크기 조정
            gbc.gridx = 1;
            panel.add(time_alarm, gbc);

            // 오전/오후 여부가 적힌 레이블
            JLabel dayNight = createLabel("");
            if (isDay) {
                dayNight.setText("오전");
            } else {
                dayNight.setText("오후");
            }
            dayNight.setForeground(new Color(255,236,231));
            dayNight.setFont(new Font("SeoulHangang CBL", Font.BOLD, 30));
            dayNight.setPreferredSize(new Dimension(70, 40)); // 크기 조정
            dayNight.setHorizontalAlignment(SwingConstants.LEFT); // 왼쪽 정렬
            gbc.gridx = 2;
            panel.add(dayNight, gbc);

            // 알람 스위치
            AlarmSwitchButton alarmOnButton = new AlarmSwitchButton("", onAlarm);
            alarmOnButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 조정
            alarmOnButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            alarmOnButton.setBackground(new Color(255,236,231)); // 버튼 배경색
            alarmOnButton.setForeground(Color.WHITE);
            alarmOnButton.addActionListener(new MyActionListener(alarmOnButton, onAlarm));
            gbc.gridx = 3;
            panel.add(alarmOnButton, gbc);

            // 알람 패널 클릭 시 삭제 확인 대화상자 표시
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int result = JOptionPane.showConfirmDialog(panel, "정말로 이 알람을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // 알람 삭제 메서드 호출
                        alarmDatabase.deleteAlarm(alarm.getId());
                        // 패널에서 삭제
                        remove(panel);
                        revalidate();
                        repaint();
                    }
                }
            });
            return panel;
        }

        private String getDays(int hour, int minute, boolean isDay) {
            for(Alarm alarm : alarmDatabase.getAlarms()) {
                if(alarm.get_hour()== hour&&alarm.get_minute()==minute &&
                        ((isDay && alarm.get_ampm().equalsIgnoreCase("오전")) ||
                                (!isDay && alarm.get_ampm().equalsIgnoreCase("오후")))) {
                    //매일 선택된 경우
                    if(alarm.isEveryday()) {
                        return "매일";
                    }
                    //요일이 선택된 경우
                    else if(!alarm.get_SelectedDays().isEmpty()) {
                        return alarm.get_SelectedDays();
                    }
                }
            }
            return " ";
        }


        // 액션 리스너 추가
        class MyActionListener implements ActionListener {
            private boolean onAlarm;
            private AlarmSwitchButton alarmOnButton;

            public void actionPerformed(ActionEvent e) {
                if (alarmOnButton.on == true)
                    alarmOnButton.on = false;
                else
                    alarmOnButton.on = true;
            }
            public MyActionListener(AlarmSwitchButton paymentButton, boolean onAlarm) {
                this.onAlarm = onAlarm;
                this.alarmOnButton = paymentButton;
            }
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
            RoundedButton paymentButton = new RoundedButton("알람 추가");
            paymentButton.setPreferredSize(new Dimension(200, 25)); // 버튼 크기 조정
            paymentButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 22));
            paymentButton.setBackground(new Color(190, 107, 104)); // 버튼 배경색
            paymentButton.setForeground(Color.WHITE);
            add(paymentButton);
            add(new JLabel("       "));
            setPreferredSize(new Dimension(0, 100));

            //RoomKioskAlarmSetting 화면으로 이동
            paymentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new RoomKioskAlarmSetting().setVisible(true);;
                    dispose();
                }
            });
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

    // 알람 ON/OFF 스위치 버튼
    class AlarmSwitchButton extends JButton {
        private boolean on;
        public AlarmSwitchButton(String label, boolean on) {
            super(label);
            this.on = on;
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color shadowColor = new Color(0, 0, 0, 50); // 가장 오른쪽은 투명도
            int shadowOffsetX = 4; // X축 오프셋
            int shadowOffsetY = 4; // Y축 오프셋

            // 그림자 그리기
            g2d.setColor(shadowColor);
            g2d.fillRoundRect(5 + shadowOffsetX, 4 + shadowOffsetY, 75, 25, 25, 25);

            g.setColor(getBackground());
            g.fillRoundRect(5, 4, 75, 25, 25, 25); // 모서리를 둥글게

            if (on == true) {
                g2d.setColor(shadowColor);
                g2d.fillOval(57, 2, 35, 35);

                // 원 (버튼)
                g.setColor(new Color(241, 132, 128));
                g.fillOval(55, 0, 35, 35);
            } else {

                g2d.setColor(shadowColor);
                g2d.fillOval(2, 2, 35, 35);

                // 원 (버튼)
                g.setColor(new Color(190, 107, 104));
                g.fillOval(0, 0, 35, 35);
            }

            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 40); // 기본 크기 설정
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(100, 40); // 최소 크기 지정
        }
    }

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(70, 300));
        }
    }
    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(70, 300));
        }
    }
}