import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static Integer showEntry() {
        ImageIcon icon = new ImageIcon("./files/dataIcon.png");
        Image image = icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);

        return JOptionPane.showOptionDialog(
                null,
                "Do you want contiue?",
                "Static Hash Index",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                new String[]{"No", "Yes"},
                null
        );
    }

    public static void showLoading() {
        ImageIcon icon = new ImageIcon("./files/sus.gif");
        Image image = icon.getImage().getScaledInstance(350, 200, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        JOptionPane.showMessageDialog(null, "", "Static Hash Index", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    public static Integer showMenu(Float overFlowPercentage, Float collisionPercentage) {
        String message = "Overflows = " + String.format("%.2f", overFlowPercentage) + "%\n" +
                "Collisions = " + String.format("%.2f", collisionPercentage) + "%\n" +
                "CHOOSE AN OPTION:";

        return JOptionPane.showOptionDialog(
                null,
                message,
                "Static Hash Index",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Search", "Table Scan", "Exit"},
                null
        );
    }

    public static void main(String[] args) throws Exception {
        Integer start = showEntry();
        Integer pageSize = null;

        if (start == 1) {
            BufferedReader reader = new BufferedReader(new FileReader("./files/words.txt"));
            boolean formFlag = true;
            while (formFlag) {
                try {
                    ImageIcon icon = new ImageIcon("./files/page.png");
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                    icon = new ImageIcon(image);
                    Object choose = JOptionPane.showInputDialog(
                            null,
                            "Type the page size:",
                            "Static Hash Index",
                            JOptionPane.QUESTION_MESSAGE,
                            icon,
                            null,
                            null
                    );
                    if (choose == null) {
                        System.exit(0);
                    } else {
                        pageSize = Integer.parseInt(choose.toString());
                        formFlag = false;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please, type a valid number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            Thread loadingThread = new Thread(Main::showLoading);
            loadingThread.start();

            Database database = new Database(reader, pageSize);

            loadingThread.interrupt();

            int optionNumber = -1;

            while (optionNumber != 2) {
                Float overFlowPercentage = database.getOverflowPercentage();
                Float collisionPercentage = database.getCollisionPercentage();
                optionNumber = showMenu(overFlowPercentage, collisionPercentage);

                if (optionNumber == 0) { //Search
                    boolean searchFlag = true;
                    while (searchFlag) {
                        String word = JOptionPane.showInputDialog("Type the word to search:");
                        if (word != null) {
                            String result = database.searchWord(word);
                            System.out.println(result);
                            int selected = JOptionPane.showOptionDialog(
                                    null,
                                    result,
                                    "Result of the search",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    new String[]{"Search again", "Exit"},
                                    "Search again"
                            );
                            if (selected == 1) {
                                searchFlag = false;
                            }
                        } else {
                            searchFlag = false;
                        }
                    }

                } else if (optionNumber == 1) { //Table Scan
                    JTextField textField = new JTextField(5);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Type the number of records you want to see:"));
                    panel.add(textField);

                    int result = JOptionPane.showConfirmDialog(
                            null,
                            panel,
                            "Number of Records",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            int quantidadeRegistros = Integer.parseInt(textField.getText());
                            JTextArea textArea = new JTextArea(15, 10);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            textArea.setEditable(false);
                            ArrayList<Tuple> tuples = database.getTuples(quantidadeRegistros);
                            for (Tuple tuple : tuples) {
                                textArea.append(tuple.toString() + "\n");
                            }
                            JOptionPane.showMessageDialog(
                                    null,
                                    scrollPane,
                                    "Records",
                                    JOptionPane.PLAIN_MESSAGE
                            );
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Please, type a valid number.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            }
        }
    }
}
