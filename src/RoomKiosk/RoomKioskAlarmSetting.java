package RoomKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 알람 설정
public class RoomKioskAlarmSetting extends JFrame{
    private JCheckBox everydayBox;
    private boolean[] weekSelected = new boolean[7];
    private String[] weekTimes = {"월", "화","수","목","금","토","일"};
    private JLabel hourLabel, minuteLabel;
    //상태 추적 변수 추가
    private boolean amSelected = false;
    private boolean pmSelected = false;
    public RoomKioskAlarmSetting() {
        super("Room Kiosk"); //창 제목
        setSize(700, 850); //700x850 사이즈
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout()); // 컨탠트팬의 레이아웃 매니저를 BorderLayout으로 설정

        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new WestPanel(), BorderLayout.WEST); // 왼쪽 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST); // 왼쪽 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 왼쪽 패널 추가

        setVisible(true); // 프레임을 화면에 표시
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new RoomKioskAlarmSetting();
    }

    //상단 영역
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

            JLabel textLabel = new JLabel("   알람 설정   ");
            textLabel.setForeground(new Color(95, 70, 70));
            textLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 24)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700, 70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }

    //가운데
    class CenterPanel extends JPanel {
        private int hour =1, minute = 30;
        private Point initialClick;

        public CenterPanel() {
            setLayout(new BorderLayout());

            //센터패널 안에서 동서남북 부분에 흰색 패널 추가
            //상단 북쪽
            JPanel NorthPanel = new JPanel();
            NorthPanel.setBackground(Color.WHITE);
            add(NorthPanel,BorderLayout.NORTH);

            //하단 남쪽
            JPanel SouthPanel = new JPanel();
            SouthPanel.setBackground(Color.WHITE);
            SouthPanel.setPreferredSize(new Dimension(100, 130));
            add(SouthPanel,BorderLayout.SOUTH);

            JLabel Bar = new JLabel();//선 생성
            Bar.setOpaque(true);
            Bar.setBackground(Color.pink);
            Bar.setPreferredSize(new Dimension(468, 3));
            SouthPanel.add(Bar);
            add(SouthPanel,BorderLayout.SOUTH);

            //매일 체크박스 생성
            JPanel CheckPanel = new JPanel();//체크박스 만들기
            CheckPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,0));
            CheckPanel.setBackground(Color.WHITE);

            everydayBox= new JCheckBox("매일");
            everydayBox.setBackground(Color.WHITE);
            everydayBox.setForeground(Color.pink);
            everydayBox.setFont(new Font("Malgun Gothic",Font.BOLD,15));

            CheckPanel.add(everydayBox);
            SouthPanel.add(CheckPanel);


            //월~일까지의 버튼 생성
            JPanel BtnPanel = new JPanel();//하단에 버튼 7개 배치
            BtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
            BtnPanel.setBackground(Color.WHITE);

            BtnPanel.setPreferredSize(new Dimension(500,100));

            String[] days= {
                    "월","화","수","목","금","토","일"};

            for(int i=0; i<7; i++) {//7개의 버튼 생성
                JButton daysBtn = new JButton(days[i]);
                daysBtn.setBackground(Color.WHITE);//버튼 색
                daysBtn.setFont(new Font("Malgun Gothic",Font.BOLD,20));
                daysBtn.setForeground(Color.pink);
                daysBtn.setBorder(new LineBorder(Color.pink,3));//테두리 색
                daysBtn.setPreferredSize(new Dimension(58,50));//버튼 크기

                //버튼 클릭 시 상태 변경
                daysBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int index = -1;
                        for(int j=0; j<7; j++) {
                            if(daysBtn.getText().equals(weekTimes[j])) {
                                index=j;
                                break;
                            }
                        }
                        if(index != -1) {
                            weekSelected[index]=!weekSelected[index];
                        }
                        //클릭된 버튼이 이미 선택된 상태인지 확인
                        if(daysBtn.getBackground()==Color.WHITE) {
                            daysBtn.setOpaque(true);
                            daysBtn.setBackground(Color.pink);
                            daysBtn.setForeground(Color.RED);//글자색을 흰색으로 변경
                        }
                        else {
                            daysBtn.setBackground(Color.WHITE);
                            daysBtn.setForeground(Color.pink); //글자색 원래대로 변경
                        }
                    }
                });
                BtnPanel.add(daysBtn);
            }

            // 상단 여백을 추가하여 버튼들을 아래로 밀기
            BtnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            SouthPanel.add(BtnPanel,BorderLayout.SOUTH);

            //오른 동쪽
            JPanel EastPanel = new JPanel();
            EastPanel.setBackground(Color.WHITE);
            add(EastPanel,BorderLayout.EAST);
            //왼 서쪽
            JPanel WestPanel = new JPanel();
            WestPanel.setBackground(Color.WHITE);
            add(WestPanel,BorderLayout.WEST);

            //가운데
            JPanel CenterPanel = new JPanel();
            CenterPanel.setBackground(Color.pink);
            add(CenterPanel,BorderLayout.CENTER);

            // GridBagLayout 사용
            CenterPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            //시간
            hourLabel = DragLabel(""+hour,"hour");
            CenterPanel.add(hourLabel);
            //분
            minuteLabel = DragLabel(""+minute,"minute");
            CenterPanel.add(minuteLabel);

            //":" 생성
            JLabel colonLabel = new JLabel(":");
            colonLabel.setFont(new Font("Malgun Gothic", Font.BOLD,50));
            colonLabel.setForeground(Color.WHITE);
            gbc.gridx = 0; // 오전/오후 버튼을 시간과 분 옆에 배치
            gbc.gridy = 0;
            CenterPanel.add(colonLabel,gbc);

            //오전 오후 버튼 추가
            JPanel amPmPanel=new JPanel();
            amPmPanel.setLayout(new GridLayout(2,1));
            amPmPanel.setBackground(Color.pink);

            JButton amButton = new JButton("오전");
            amButton.setBackground(new Color(0,0,0,0));//투명배경
            amButton.setOpaque(false);
            amButton.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
            amButton.setForeground(Color.WHITE);
            amButton.setBorder(null);

            JButton pmButton = new JButton("오후");
            pmButton.setBackground(new Color(0,0,0,0));
            pmButton.setOpaque(false);
            pmButton.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
            pmButton.setForeground(Color.WHITE);
            pmButton.setBorder(null);

            //오전 버튼 이벤트
            amButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    amSelected = true;
                    pmSelected = false;

                    amButton.setBorder(BorderFactory.createLineBorder(Color.pink));
                    amButton.setForeground(Color.RED);
                    amButton.setFont(new Font("Malgun Gothic",Font.BOLD, 27));

                    pmButton.setBorder(null);
                    pmButton.setForeground(Color.WHITE);
                    pmButton.setFont(new Font("Malgun Gothic",Font.BOLD,25));
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(amSelected) {
                        amButton.setBorder(BorderFactory.createLineBorder(Color.pink));
                        amButton.setForeground(Color.RED);
                    }
                }
            });

            //오후 버튼 이벤트
            pmButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pmSelected = true;
                    amSelected = false;
                    pmButton.setBorder(BorderFactory.createLineBorder(Color.pink));
                    pmButton.setForeground(Color.RED);
                    pmButton.setFont(new Font("Malgun Gothic", Font.BOLD, 27));

                    amButton.setBorder(null);
                    amButton.setForeground(Color.WHITE);
                    amButton.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(pmSelected) {
                        pmButton.setBorder(BorderFactory.createLineBorder(Color.pink));
                        pmButton.setForeground(Color.RED);
                    }
                }
            });

            amPmPanel.add(amButton);
            amPmPanel.add(pmButton);

            gbc.gridx = 3; // 오전/오후 버튼을 시간과 분 옆에 배치
            gbc.gridy = 0;
            gbc.gridheight = 2; // 세로로 두 줄을 차지
            CenterPanel.add(amPmPanel, gbc);


            updateTimeDisplay();

        }

        //숫자 드래그 시간, 분
        private JLabel DragLabel(String text, String type) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Malgun Gothic", Font.BOLD,50));
            label.setPreferredSize(new Dimension(150,190));

            label.setForeground(Color.WHITE);

            JLabel nextLabel = new JLabel();
            JLabel previousLabel = new JLabel();
            nextLabel.setFont(new Font("Malgun Gothic",Font.BOLD,45));
            previousLabel.setFont(new Font("Malgun Gothic",Font.BOLD,45));

            //전/후 숫자 색
            previousLabel.setForeground(new Color(228,206,217));
            nextLabel.setForeground(new Color(228,206,217));

            label.setLayout(new BorderLayout());
            label.add(previousLabel, BorderLayout.NORTH);
            label.add(nextLabel,BorderLayout.SOUTH);

            //숫자 위/아래로 이동하는 전후 숫자 설정
            updateAdjacentNumbers(previousLabel,nextLabel, type);

            label.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    initialClick = e.getPoint();
                }
            });

            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    Point currentLocation = e.getLocationOnScreen();
                    int deltaX = currentLocation.x - initialClick.x;

                    boolean updated = false;

                    if(type.equals("hour")) {
                        //1시간 당 30px이동
                        int newHour = (hour + deltaX/150)%12;
                        if(newHour<1) newHour +=12;
                        if(newHour != hour) {
                            hour = newHour;
                            label.setText(""+hour);
                            updated=true;
                        }
                    }
                    else if(type.equals("minute")) {
                        //1분당 15px이동
                        int newMinute = (minute + deltaX/130) %60;
                        if(newMinute <0) newMinute += 60;
                        if(newMinute != minute) {
                            minute = newMinute;
                            label.setText(""+minute);
                            updated=true;
                        }
                    }
                    if(updated) {
                        updateAdjacentNumbers(previousLabel,nextLabel,type);
                    }
                }
            });
            return label;
        }
        private void updateAdjacentNumbers(JLabel prevLabel, JLabel next, String type) {
            if(type.equals("hour")) {
                //전 후 시간 업데이트
                prevLabel.setText("" + ((hour==1) ? 12:hour-1));
                next.setText(""+((hour == 12) ? 1 :hour +1 ));
            }
            else if(type.equals("minute")) {
                prevLabel.setText(""+((minute==0) ? 59: minute -1));
                next.setText(""+ ((minute==59) ? 0:minute+1));
            }
        }
        private void updateTimeDisplay() {
            //현재 시간을 화면에 표시하거나 업데이트하는 메소드
            hourLabel.setText("" + hour);
            minuteLabel.setText("" + minute);
        }
    }

    //왼쪽
    class WestPanel extends JPanel {
        public WestPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    //오른쪽

    class EastPanel extends JPanel {
        public EastPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    //하단
    class SouthPanel extends JPanel {
        public SouthPanel() {

            setBackground(new Color(255, 220, 200));
            setPreferredSize(new Dimension(700, 240));

            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new GridBagLayout());
            innerPanel.setBackground(new Color(255, 220, 200));

            // 알람 버튼
            RoundedButton Alarm_btn = new RoundedButton("알람설정",30,30);
            Alarm_btn.setBackground(new Color(190, 107, 104));
            Alarm_btn.setForeground(Color.WHITE);
            Alarm_btn.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
            Alarm_btn.setPreferredSize(new Dimension(160, 35));

            Alarm_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int hour =Integer.parseInt(hourLabel.getText().trim());
                    int minute = Integer.parseInt(minuteLabel.getText().trim());
                    //String ampm = amSelected ? "오전":"오후";
                    boolean everyday = everydayBox.isSelected();
                    String ampm = "";
                    if(amSelected) {
                        ampm="오전";
                    }else if(pmSelected) {
                        ampm = "오후";
                    }else {
                        ampm="오전";
                    }

                    String[] selectedDays = new String[7];  // 7일을 저장할 수 있는 배열 선언
                    int index = 0;  // 배열에 값을 추가할 때 사용할 인덱스

                    // weekSelected 배열을 순회하면서 선택된 요일을 selectedDays 배열에 추가
                    for (int i = 0; i < weekSelected.length; i++) {
                        if (weekSelected[i]) {  // 요일이 선택되었을 경우
                            selectedDays[index++] = weekTimes[i];  // 선택된 요일을 배열에 추가
                        }
                    }
                    StringBuilder selected_days = new StringBuilder();
                    for(int i=0; i<weekSelected.length; i++) {
                        if(weekSelected[i]) {
                            selected_days.append(weekTimes[i]).append(" ");
                        }
                    }
                    AlarmDatabase db = new AlarmDatabase();
                    db.insertalarmlist(ampm,hour,minute,everyday,selected_days.toString().trim());
                    db.close();

                    JOptionPane.showMessageDialog(Alarm_btn,"알람설정 정보가 저장되었습니다.");

                    new RoomKioskAlarmList();
                    SwingUtilities.getWindowAncestor(Alarm_btn).setVisible(false);
                }
            });
            add(Alarm_btn);
        }
    }

    // 모시러 20만큼 깎인 버튼 라운드 버튼 만들기
    class RoundedButton extends JButton {
        private int arcWidth;
        private int arcHeight;

        public RoundedButton(String label, int arcWidth, int arcHeight) {
            super(label);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 버튼 배경 색상
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}