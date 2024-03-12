import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static Integer showEntry() {
        ImageIcon icon = new ImageIcon("./files/dataIcon.png");
        Image image = icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);

        return JOptionPane.showOptionDialog(
                null,
                "Podemos iniciar?",
                "Indice Hash Estático",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                new String[]{"Não", "Sim"},
                "Sim"
        );
    }

    public static Integer[] showForm() {
        JTextField tableSizeField = new JTextField();
        JTextField pageSizeField = new JTextField();
        JTextField bucketSizeField = new JTextField();

        Object[] message = {
                "Tamanho da Tabela:", tableSizeField,
                "Tamanho da Página:", pageSizeField,
                "Tamanho do Bucket:", bucketSizeField
        };

        int option = JOptionPane.showOptionDialog(null, message, "Configurações do Banco de Dados",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            int tableSize = Integer.parseInt(tableSizeField.getText());
            int pageSize = Integer.parseInt(pageSizeField.getText());
            int bucketSize = Integer.parseInt(bucketSizeField.getText());

            return new Integer[]{tableSize, pageSize, bucketSize};
        } else {
            System.exit(0);
            return null;
        }
    }

    public static void showLoading() {
        ImageIcon icon = new ImageIcon("./files/duck3.gif");
        Image image = icon.getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        JOptionPane.showMessageDialog(null, "Processando...", "Indice Hash Estático", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("./files/words.txt"));
        Integer start = showEntry();
        Integer[] form;
        Integer tableSize = null;
        Integer pageSize = null;
        Integer bucketSize = null;
        Integer numberOfBuckets = 50;

        if (start == 1) {
            boolean formFlag = true;
            while (formFlag) {
                try {
                    form = showForm();
                    tableSize = form[0];
                    pageSize = form[1];
                    bucketSize = form[2];
                    formFlag = false;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            Thread loadingThread = new Thread(Main::showLoading);
            loadingThread.start();

            Database database = new Database(tableSize, pageSize, bucketSize);
            database.populateDatabase(reader);

            loadingThread.interrupt();


            JOptionPane.showMessageDialog(null, "Taxa de Overflow = " + database.getOverflowPercentage());
            boolean searchFlag = true;
            while (searchFlag) {
                String word = JOptionPane.showInputDialog("Digite uma palavra para buscar:");
                if (word != null) {
                    String result = database.searchWord(word);
                    System.out.println(result);
                    int selected = JOptionPane.showOptionDialog(
                            null,
                            result,
                            "Resultado da Busca",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{"Buscar outra palavra", "Sair"},
                            "Buscar outra palavra"
                    );

                    if (selected == 1) {
                        searchFlag = false;
                    }

                } else {
                    searchFlag = false;
                }
            }
        }
    }
}
