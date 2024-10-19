import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        SwingUtilities.invokeLater(()->{
            GamePlayMains gamePlayMains = new GamePlayMains();
            jFrame.setBounds(10, 10, 705, 600);
            jFrame.setTitle("Break Breaker");
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.add(gamePlayMains);
            jFrame.setVisible(true);
        });
        
    }
}