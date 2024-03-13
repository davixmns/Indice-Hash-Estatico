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

    public static void showLoading() {
        ImageIcon icon = new ImageIcon("./files/duck3.gif");
        Image image = icon.getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        JOptionPane.showMessageDialog(null, "Processando...", "Indice Hash Estático", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    public static void main(String[] args) throws Exception {
        Integer start = showEntry();
        Integer pageSize = null;

        if (start == 1) {
            BufferedReader reader = new BufferedReader(new FileReader("./files/words.txt"));
            boolean formFlag = true;
            while (formFlag) {
                try {
                    pageSize = Integer.parseInt(JOptionPane.showInputDialog("Digite o tamanho da página :"));
                    formFlag = false;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            Thread loadingThread = new Thread(Main::showLoading);
            loadingThread.start();

            Database database = new Database(reader, pageSize);

            loadingThread.interrupt();


            JOptionPane.showMessageDialog(null, "Taxa de Overflow = " + String.format("%.2f", database.getOverflowPercentage()) + "%");
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
