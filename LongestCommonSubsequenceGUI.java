import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LongestCommonSubsequenceGUI extends JFrame {
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JTextField inputField1;
    private JTextField inputField2;
    private JTextArea table1;
    private JTextArea table2;
    private JTextArea resultArea;

    public LongestCommonSubsequenceGUI() {
        setTitle("Longest Common Subsequence");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label1 = new JLabel("String 1:");
        inputField1 = new JTextField();
        JLabel label2 = new JLabel("String 2:");
        inputField2 = new JTextField();
        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeLCS();
            }
        });

        inputPanel.add(label1);
        inputPanel.add(inputField1);
        inputPanel.add(label2);
        inputPanel.add(inputField2);
        inputPanel.add(new JLabel());
        inputPanel.add(computeButton);

        outputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        table1 = new JTextArea();
        table1.setEditable(false);
        JScrollPane table1ScrollPane = new JScrollPane(table1);
        table2 = new JTextArea();
        table2.setEditable(false);
        JScrollPane table2ScrollPane = new JScrollPane(table2);
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        outputPanel.add(createPanel(table1ScrollPane, "Length of LCS Table"));
        outputPanel.add(createPanel(table2ScrollPane, "Path taken for LCS"));
        outputPanel.add(createPanel(resultScrollPane, "Longest Common Subsequence"));
        outputPanel.add(new JPanel());

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private JPanel createPanel(Component component, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void computeLCS() {
        String text1 = inputField1.getText();
        String text2 = inputField2.getText();

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        StringBuilder[][] path = new StringBuilder[text1.length() + 1][text2.length() + 1];

        for (int i = 0; i <= text1.length(); i++) {
            for (int j = 0; j <= text2.length(); j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                    path[i][j] = new StringBuilder();
                } else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    path[i][j] = new StringBuilder(path[i - 1][j - 1]).append(text1.charAt(i - 1));
                } else {
                    if (dp[i - 1][j] >= dp[i][j - 1]) {
                        dp[i][j] = dp[i - 1][j];
                        path[i][j] = new StringBuilder(path[i - 1][j]);
                    } else {
                        dp[i][j] = dp[i][j - 1];
                        path[i][j] = new StringBuilder(path[i][j - 1]);
                    }
                }
            }
        }

        table1.setText("Length of LCS Table:\n");
        StringBuilder table1Output = new StringBuilder();
        for (int i = 0; i <= text1.length(); i++) {
            for (int j = 0; j <= text2.length(); j++) {
                table1Output.append(String.format("%-4d", dp[i][j]));
            }
            table1Output.append("\n");
        }
        table1.setText(table1Output.toString());

        table2.setText("Path taken for LCS:\n");
        table2.append(path[text1.length()][text2.length()].toString());

        resultArea.setText("Longest Common Subsequence:\n");
        resultArea.append(path[text1.length()][text2.length()].toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LongestCommonSubsequenceGUI::new);
    }
}
