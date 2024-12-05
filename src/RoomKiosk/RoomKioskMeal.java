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

public class RoomKioskMeal extends JFrame {
    public RoomKioskMeal() {
        super("Room Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH);


        CenterPanel centerPanel = new CenterPanel(); // CenterPanel 인스턴스 생성
        SouthPanel southPanel = new SouthPanel(centerPanel); // CenterPanel을 SouthPanel에 전달
        centerPanel.setSouthPanel(southPanel);

        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(new EastPanel(), BorderLayout.EAST);
        contentPane.add(new WestPanel(), BorderLayout.WEST);

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new RoomKioskMeal();
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
        private int[] a;
        private SouthPanel southPanel;

        public CenterPanel() {
            setBackground(new Color(255, 220, 200));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 배치

            a = new int[]{1};
            southPanel = null;

            add(createMealPanel("16,000원", new Color(255, 236, 236)));
            add(Box.createRigidArea(new Dimension(0, 20)));
        }

        public void setSouthPanel(SouthPanel southPanel){
            this.southPanel = southPanel;
        }

        // 조식권 구매 패널
        private JPanel createMealPanel(String price, Color bgColor) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout()); // GridBagLayout 사용
            panel.setBackground(bgColor);
            panel.setAlignmentX(CENTER_ALIGNMENT);

            LineBorder outerBorder = new LineBorder(new Color(203, 134, 136), 5); // 외부 테두리
            EmptyBorder innerPadding = new EmptyBorder(5, 5, 5, 5); // 내부 여백
            LineBorder innerBorder = new LineBorder(new Color(203, 134, 136), 2); // 내부 테두리

            // CompoundBorder를 사용하여 두 테두리 결합
            panel.setBorder(new CompoundBorder(outerBorder, new CompoundBorder(innerPadding, innerBorder)));
            panel.setPreferredSize(new Dimension(680, 250)); // 패널 크기 조절

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // 여백 설정
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            MealButton imageName = createMealButton("1인 조식권", new Color(211, 168, 156));
            imageName.setAlignmentX(Component.CENTER_ALIGNMENT);
            gbc.gridx = 0;
            panel.add(imageName, gbc);

            // 설명 배치용 패널
            JPanel mealDescPanel = new JPanel();
            mealDescPanel.setBackground(bgColor);
            mealDescPanel.setLayout(new BoxLayout(mealDescPanel, BoxLayout.Y_AXIS));  // 수평 배치
            mealDescPanel.setPreferredSize(new Dimension(170, 170));
            mealDescPanel.setAlignmentY(Component.LEFT_ALIGNMENT);

            // 타이틀
            JLabel mealTitle = new JLabel("K 호텔 조식권");
            mealTitle.setForeground(new Color(95, 70, 70));
            mealTitle.setFont(new Font("KoPubDotum Bold", Font.BOLD, 24)); // 글꼴 및 크기 설정
            mealTitle.setPreferredSize(new Dimension(110, 35));
            mealTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            mealDescPanel.add(mealTitle);

            // 구매 개수 안내 Label (디폴트 1개)
            JLabel mealPrice = new JLabel(price);
            mealPrice.setForeground(new Color(223, 122, 118));
            mealPrice.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18)); // 글꼴 및 크기 설정
            mealPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
            mealDescPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mealDescPanel.add(mealPrice);

            // 조식권 구매 개수 조정용 버튼
            MealCountButton minusButton = new MealCountButton("-"); // 구매 수량 DWON
            minusButton.setForeground(new Color(255, 236, 236));
            minusButton.setBackground(new Color(176, 131, 132));
            minusButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 25));
            minusButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            minusButton.setPreferredSize(new Dimension(30, 35));

            MealCountButton mealCount = new MealCountButton(String.valueOf(a[0])); // 수량 표시
            mealCount.setForeground(new Color(176, 131, 132));
            mealCount.setBackground(new Color(255, 236, 236));
            mealCount.setFont(new Font("KoPubDotum Bold", Font.BOLD, 25));
            mealCount.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            mealCount.setPreferredSize(new Dimension(30, 35));

            MealCountButton plusButton = new MealCountButton("+"); // 구매 수량 UP
            plusButton.setForeground(new Color(255, 236, 236));
            plusButton.setBackground(new Color(176, 131, 132));
            plusButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 25));
            plusButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            plusButton.setPreferredSize(new Dimension(30, 35));

            // 버튼 부착용 Panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(255, 236, 236));
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            buttonPanel.setBorder(new LineBorder(new Color(176, 131, 132), 3)); // 두께 3픽셀의 테두리
            buttonPanel.setPreferredSize(new Dimension(120, 35));

            buttonPanel.add(minusButton);
            buttonPanel.add(mealCount);
            buttonPanel.add(plusButton);

            mealDescPanel.add(Box.createRigidArea(new Dimension(0, 65)));
            mealDescPanel.add(buttonPanel);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1; // 공간 완전히 차지

            panel.add(mealDescPanel, gbc);

            // 버튼 클릭 이벤트 처리
            minusButton.addActionListener(e -> {
                if (a[0] > 1) { // 수량이 1보다 클 때만 감소
                    a[0]--; // 수량 감소
                    mealCount.setText(String.valueOf(a[0])); // 수량 업데이트
                    southPanel.updateTotalPrice(this);
                }
            });

            plusButton.addActionListener(e -> {
                a[0]++; // 수량 증가
                mealCount.setText(String.valueOf(a[0])); // 수량 업데이트
                southPanel.updateTotalPrice(this);
            });

            return panel;
        }

        public int getBreakfastCount() {
            return a[0];
        }

        // 1인 조식권 표시용
        private MealButton createMealButton(String name, Color bgColor) {
            MealButton ButtonName = new MealButton(name);
            ButtonName.setForeground(Color.WHITE);
            ButtonName.setPreferredSize(new Dimension(170, 170)); // 버튼 크기 조정
            ButtonName.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            ButtonName.setBackground(bgColor); // 버튼 배경색

            return ButtonName;
        }


    }

    class SouthPanel extends JPanel {
        private int totalPrice;
        private CenterPanel centerPanel;

        public SouthPanel(CenterPanel centerPanel) {
            this.centerPanel = centerPanel;

            updateTotalPrice(centerPanel);
            int breakfastCount = centerPanel.getBreakfastCount();
            totalPrice = breakfastCount * 16000;

            setBackground(new Color(255, 220, 200));
            RoundedButton paymentButton = new RoundedButton("구매하기");
            paymentButton.setPreferredSize(new Dimension(200, 25)); // 버튼 크기 조정
            paymentButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 22));
            paymentButton.setBackground(new Color(229, 158, 138)); // 버튼 배경색
            paymentButton.setForeground(Color.WHITE);
            add(paymentButton);
            add(new JLabel("       "));
            setPreferredSize(new Dimension(0, 350));
            paymentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	/*
          //          Database db = new Database();
                    db.insertBreakfastOrder(801, totalPrice);
                    db.close();
*/
                    new RoomKioskMealFin();
                    SwingUtilities.getWindowAncestor(paymentButton).setVisible(false);
                }
            });
        }

        public void updateTotalPrice(CenterPanel centerPanel) {
            int breakfastCount = centerPanel.getBreakfastCount();
            totalPrice = breakfastCount * 16000;
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

    // Meal 수량 조절 시 사용된 버튼
    class MealCountButton extends JButton {
        public MealCountButton(String label) {
            super(label);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0); // 모서리를 둥글게
            super.paintComponent(g);
        }

    }

    class MealButton extends JButton {
        public MealButton(String label) {
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

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(80, 300));
        }
    }

    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(255, 220, 200));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(80, 300));
        }
    }
}