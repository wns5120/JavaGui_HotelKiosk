package RoomKiosk;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// 룸서비스 화면을 구현하는 RoomKioskFood 클래스
public class RoomKioskFood extends JFrame {
    private int roomNumber = 801;
    private int totalPrice = 0; // 총 가격을 저장하는 변수
    private ArrayList<String> cart = new ArrayList<>(); // 장바구니 리스트
    private ArrayList<String> cart_name = new ArrayList<>(); // 장바구니 리스트
    private JLabel totalLabel; // 총 가격을 표시할 라벨

    // 생성자: 창의 기본 구성 설정
    public RoomKioskFood() {
        super("Room Kiosk"); // 창 제목 설정
        setSize(700, 850); // 창 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout()); // 레이아웃 설정
        contentPane.add(new NPanel(), BorderLayout.NORTH); // 상단 패널 추가
        contentPane.add(new CPanel(), BorderLayout.CENTER); // 중앙 패널 추가
        contentPane.add(new SPanel(), BorderLayout.SOUTH); // 하단 패널 추가
        contentPane.add(new WPanel(), BorderLayout.WEST); // 좌측 여백 패널
        contentPane.add(new EPanel(), BorderLayout.EAST); // 우측 여백 패널

        setVisible(true); // 창 표시
        //    FoodDatabase db = new FoodDatabase();
    }

    // 프로그램 실행을 위한 main 메서드
    public static void main(String[] args) {
        new RoomKioskFood(); // RoomKioskFood 객체 생성
    }

    // 상단 패널: 로고 및 텍스트를 표시
    class NPanel extends JPanel {
        public NPanel() {
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
                    SwingUtilities.getWindowAncestor(NPanel.this).setVisible(false);
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

            JLabel textLabel = new JLabel("   룸서비스   ");
            textLabel.setForeground(new Color(95, 70, 70));
            textLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 24)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700, 70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }

    // 중앙 패널: 카테고리와 메뉴 항목 표시
    class CPanel extends JPanel {
        private JPanel menuPanel;
        private boolean isClicked = false;

        public CPanel() {
            setLayout(new BorderLayout());
            JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            categoryPanel.setBackground(new Color(255, 220, 200));
            categoryPanel.setAlignmentY(CENTER_ALIGNMENT);
            String[] categories = {"식사류", "주류", "기타"};
            JButton[] categoryButtons = new JButton[categories.length];

            for (int i = 0; i < categories.length; i++) {
                categoryButtons[i] = new NameButton(categories[i]);
                categoryButtons[i].setBackground(new Color(255, 220, 200));
                categoryButtons[i].setForeground(new Color(255, 220, 200));
                categoryButtons[i].setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
                categoryButtons[i].setAlignmentY(Component.CENTER_ALIGNMENT); // 수평 중앙 정렬
                int index = i;
                categoryButtons[i].addActionListener(e -> showMenu(categories[index], categoryButtons[index]));
                categoryPanel.add(categoryButtons[i]);
            }
            add(categoryPanel, BorderLayout.NORTH);

            menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(2, 3, 10, 10));
            menuPanel.setBackground(new Color(255, 220, 200));
            menuPanel.setAlignmentX(CENTER_ALIGNMENT);
            menuPanel.setPreferredSize(new Dimension(400,400));
            add(menuPanel, BorderLayout.CENTER);

            showMenu("식사류", categoryButtons[0]); // 초기 화면
        }

        private void showMenu(String category, JButton clickedButton) {
            menuPanel.removeAll();

            Component[] components = ((JPanel) getComponent(0)).getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton) {
                    comp.setBackground(new Color(255, 236, 236));
                    comp.setForeground(new Color(203, 134, 136));
                }
            }

            clickedButton.setBackground(new Color(249, 142, 145));
            clickedButton.setForeground(Color.WHITE);

            String[] itemNames;
            int[] itemPrices;
            String[] itemImages;
            boolean[] itemClicked;

            switch (category) {
                case "식사류":
                    itemNames = new String[]{"스테이크", "스파게티", "샐러드", "피자"};
                    itemPrices = new int[]{45000, 18000, 15000, 23000};
                    itemImages = new String[]{"images/steak.jpg", "images/pasta.jpg", "images/salad.jpg", "images/pizza.jpg"};
                    itemClicked = new boolean[] {false, false, false, false};
                    break;
                case "주류":
                    itemNames = new String[]{"와인", "맥주", "칵테일", "하이볼"};
                    itemPrices = new int[]{30000, 10000, 15000, 10000};
                    itemImages = new String[]{"images/wine.jpg", "images/beer.jpg", "images/cocktail.jpg", "images/highball.jpg"};
                    itemClicked = new boolean[] {false, false, false, false};
                    break;
                default:
                    itemNames = new String[]{"커피", "케이크", "차", "빵"};
                    itemPrices = new int[]{7000, 10000, 7500, 8000};
                    itemImages = new String[]{"images/coffee.jpg", "images/cake.jpg", "images/tea.jpg", "images/bread.jpg"};
                    itemClicked = new boolean[] {false, false, false, false};
                    break;
            }

            for (int i = 0; i < itemNames.length; i++) {
                String name = itemNames[i];
                int price = itemPrices[i];
                String image = itemImages[i];
                boolean isOn = itemClicked[i];

                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(255, 236, 236));
                card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                card.setPreferredSize(new Dimension(180,230));

                JLabel imgLabel = new JLabel(new ImageIcon(image));
                imgLabel.setBorder(BorderFactory.createLineBorder(new Color(138, 89, 75), 5));
                imgLabel.setPreferredSize(new Dimension(140,140));

                JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
                JLabel priceLabel = new JLabel(price + "원", SwingConstants.CENTER);

                nameLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
                nameLabel.setForeground(new Color(138, 89, 75));
                priceLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
                priceLabel.setForeground(new Color(138, 89, 75));

                card.add(imgLabel, BorderLayout.CENTER);
                card.add(nameLabel, BorderLayout.NORTH);
                card.add(priceLabel, BorderLayout.SOUTH);

                menuPanel.add(card);

                imgLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isOn == false) {
                            imgLabel.setBorder(BorderFactory.createLineBorder(new Color(229, 158, 138), 5));
                        }
                        cart_name.add(name);
                        cart.add(name + " - " + price + "원");
                        totalPrice += price;
                        updateCart();
                    }
                });
            }

            menuPanel.revalidate();
            menuPanel.repaint();
        }
    }

    // 하단 패널: 총 가격과 버튼
    class SPanel extends JPanel {
        public SPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(255, 220, 200));

            totalLabel = new JLabel("현재 가격: " + totalPrice + "원", SwingConstants.CENTER);
            totalLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 16));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(new Color(255, 220, 200));

            NameButton backButton = new NameButton("장바구니 확인");
            NameButton confirmButton = new NameButton("주문하기");

            backButton.setForeground(Color.WHITE);
            backButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 12));
            backButton.setBackground(new Color(190, 107, 104));
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 12));
            confirmButton.setBackground(new Color(190, 107, 104));

            backButton.addActionListener(e -> checkOrder());
            confirmButton.addActionListener(e -> confirmOrder());

            buttonPanel.add(backButton);
            buttonPanel.add(confirmButton);

            add(totalLabel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
            //add(new JLabel("       "));
            //add(Box.createRigidArea(new Dimension(0, 10)));
            setPreferredSize(new Dimension(0, 90));
            //add(new JLabel("       "));
        }
    }

    // 좌측 여백 패널
    class WPanel extends JPanel {
        public WPanel() {
            setPreferredSize(new Dimension(10, 0));
            setBackground(new Color(255, 220, 200));
        }
    }

    // 우측 여백 패널
    class EPanel extends JPanel {
        public EPanel() {
            setPreferredSize(new Dimension(10, 0));
            setBackground(new Color(255, 220, 200));
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

    class NameButton extends JButton {
        public NameButton(String label) {
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

    // 장바구니 갱신 메서드
    private void updateCart() {
        totalLabel.setText("현재 가격: " + totalPrice + "원");
    }

    // 뒤로가기 메서드
    private void goBack() {
        JOptionPane.showMessageDialog(this, "메인 화면으로 돌아갑니다.");
        new RoomKioskMain();
        dispose(); // 창 닫기
    }

    // 주문 확인 메서드
    private void confirmOrder() {
        StringBuilder message = new StringBuilder("주문 내역:\n");
        Map<String, Integer> choices = new HashMap<>();

        for (String item : cart) {
            message.append(item).append("\n");
        }

        for (String item : cart_name) {
            choices.put(item, 1);
        }
        message.append("\n총 가격: ").append(totalPrice).append("원");
        //       FoodDatabase.insertFood(roomNumber, totalPrice, choices);
        JOptionPane.showMessageDialog(this, message.toString(), "주문 완료", JOptionPane.INFORMATION_MESSAGE);
        totalPrice = 0;
        cart.clear();
        updateCart();
        new RoomKioskFoodFin();
        dispose(); // 현재 창 닫기
    }

    // 주문 확인 메서드 (장바구니용)
    private void checkOrder() {
        StringBuilder message = new StringBuilder("장바구니 목록:\n");
        Map<String, Integer> choices = new HashMap<>();

        for (String item : cart) {
            message.append(item).append("\n");
        }

        for (String item : cart_name) {
            choices.put(item, 1);
        }
        message.append("\n총 가격: ").append(totalPrice).append("원");
        JOptionPane.showMessageDialog(this, message.toString(), "장바구니", JOptionPane.INFORMATION_MESSAGE);
    }
}