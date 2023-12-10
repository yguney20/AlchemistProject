package UI.Swing.MenuScreens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class HelpScreen extends JFrame {

    private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private int initialX;
    private int initialY;

    public HelpScreen(Frame frame) {
        contentPane = new JPanel() {
           
            
        };

        // Set the background color of the JFrame
        contentPane.setBackground(new Color(65, 40, 25)); // Dark brown color

        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        
        setTitle("KU Alchemist Help");
        setSize(1000, 800);
        setUndecorated(true); // Remove title bar
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/logo.png"));
        setIconImage(icon.getImage());

        // Add ActionListener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct user to GameEntranceScreen
                frame.setVisible(true);
                dispose();
            }
        });

        // Add the Back button to the top left of the frame
        backButton.setBounds(10, 10, 75, 25);
        contentPane.add(backButton);

        addQuestionsAndAnswers();

        // Add a MouseListener to make the frame movable
        contentPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialX;
                int y = getLocation().y + e.getY() - initialY;
                setLocation(x, y);
            }
        });

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Set the content pane to the custom JPanel
        setContentPane(contentPane);
        setLayout(new BorderLayout()); // Set layout for JFrame
    }

    private void addQuestionsAndAnswers() {
        // Create a scroll pane to allow scrolling through questions and answers
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 980, 720);
        contentPane.add(scrollPane);

        // Create a panel for questions and answers
        JPanel qaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(getClass().getResource("/UI/Swing/Images/screenBackground.jpg")).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        qaPanel.setLayout(new BoxLayout(qaPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(qaPanel);

     // Add questions and answers
        addQuestionAndAnswer(qaPanel, "Q: How can I gather magical ingredients for my experiments?\n", 
                "A: Navigate to the Player Dashboard and use the \"Forage for Ingredient\" option to acquire magical components for your alchemical endeavors.");
        addQuestionAndAnswer(qaPanel, "Q: What's the process for creating potions in the game?\n", 
                "A: Utilize the \"Brew Potions\" feature on the Player Dashboard to combine collected ingredients and concoct powerful potions.");
        addQuestionAndAnswer(qaPanel, "Q: How can I unravel the mysteries of magical ingredients?\n" ,
                "A: Engage in experiments by selecting the \"Conduct Experiments\" option on the Player Dashboard to gain insights into the properties of different components.");
        addQuestionAndAnswer(qaPanel, "Q: How can I share my discoveries with other alchemists?\n" ,
                "A: Share your findings by choosing the \"Publish Theories\" option on the Player Dashboard to contribute to the collective knowledge of the alchemical community.");
        addQuestionAndAnswer(qaPanel, "Q: What's the significance of reputation points in the game?\n" ,
                "A: Reputation points serve as a measure of your standing in the alchemical world. Successfully brewing potions, and publishing theories will elevate your reputation.");
        addQuestionAndAnswer(qaPanel, "Q: What are Artifact Cards, and how do they impact the game?\n" ,
                "A: You can find detailed explanations below, one by one, and discover the rest in-game.");
        addQuestionAndAnswer(qaPanel, "Q: How are reputation points calculated in the game?\n" ,
                "A: Multiply your reputation points by 10 to determine your score points. The higher your reputation, the greater your score.");
        addQuestionAndAnswer(qaPanel, "Q: Can you explain the effects of the selected Artifact card Koc Cure?\n" ,
                "- Koc Cure: Grants an immediate effect of obtaining 1 healing potion when selected.\n");
        addQuestionAndAnswer(qaPanel, "Q: Can you explain the effects of the selected Artifact card Rumeli Selector?\n" ,
        		"- Rumeli Selector: Allows you to choose the ingredient you want when performing the action 'Forage for Ingredient.' This effect can be used one time.\n");
        addQuestionAndAnswer(qaPanel, "Q: Can you explain the effects of the selected Artifact card Dean's Honor?\n" ,
        		"- Dean's Honor: Provides an immediate effect of gaining 1 reputation point upon selection.\n");
        addQuestionAndAnswer(qaPanel, "Q: Can you explain the effects of the selected Artifact card Vehbi's Vision?\n" ,
        		"- Vehbi's Vision: Grants the ability to see the ingredients of the next potion the other player makes. This effect is a one-time use.\n");
        addQuestionAndAnswer(qaPanel, "Q: Can you explain the effects of the selected Artifact card Random Ingredient Saver?\n" ,
        		"- Random Ingredient Saver: When making a potion, instead of discarding both ingredients used, only 1 is randomly chosen to be discarded (once per round)");
        addQuestionAndAnswer(qaPanel, "Q: What's the primary objective of the game?\n" ,
                "A: Goal is to uncover the secrets of magical ingredients, create powerful potions, publishing theories, and earning reputation points.");
        addQuestionAndAnswer(qaPanel, "Q: How do I maximize my score in the game?\n" ,
                "A: Engage in diverse alchemical activities, from collecting ingredients to brewing potions and publishing theories. Use Artifact Cards for bonus points at the end.");
        // Add more questions and answers as needed
        qaPanel.setOpaque(false);
    }

    private void addQuestionAndAnswer(JPanel panel, String question, String answer) {
        // Create a button for the question
        JButton questionButton = new JButton(question);
        questionButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Set a fixed size for the question buttons
        questionButton.setPreferredSize(new Dimension(950, 30));

        // Center the text on the button
        questionButton.setHorizontalAlignment(SwingConstants.LEFT);
        questionButton.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(questionButton);

        // Create a label for the answer
        JLabel answerLabel = new JLabel(answer);
        answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        answerLabel.setVisible(false);

        // Set the background color of the answer label to white
        answerLabel.setBackground(Color.WHITE);
        answerLabel.setOpaque(true);

        panel.add(answerLabel);

        // Add ActionListener to show/hide the answer
        questionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerLabel.setVisible(!answerLabel.isVisible());
            }
        });
    }

    public void display() {
        // Make the frame visible
        setVisible(true);
    }

}