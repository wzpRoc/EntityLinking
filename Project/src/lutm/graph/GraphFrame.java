package lutm.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphFrame extends JFrame {
    public GraphFrame() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        GraphPanel graphPanel = new GraphPanel();
        container.add(graphPanel, BorderLayout.CENTER);

        this.setTitle("Show Sin Fuction!");
        this.setSize(new Dimension(800, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GraphFrame();
    }
}

class GraphPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.white);

        Dimension panelSize = this.getSize();
        Location center = new Location(panelSize.width / 2, panelSize.height / 2);
        int radius = (int) ((Math.min(panelSize.width, panelSize.height) / 2) * 0.9);

        // 确定每个点的坐标
        int[] x = new int[2 * radius + 1];
        int[] y = new int[2 * radius + 1];
        for (int i = 0; i < 2 * radius + 1; i++) {
            x[i] = center.x - radius + i;
            double y1 = Math.sin(((double) (-radius + i) / radius) * 4 * Math.PI);// 这个很重要，sin()里面必须为double值
            int y2 = (int) (y1 * 100);
            y[i] = center.y - y2;
        }

        g.setColor(Color.black);
        // 画坐标轴
        // 横轴
        g.drawLine(center.x - radius, center.y, center.x + radius, center.y);
        // 纵轴
        g.drawLine(center.x, center.y - radius, center.x, center.y + radius);
        // 箭头
        g.drawLine(center.x + radius, center.y, center.x + radius - 10, center.y - 5);
        g.drawLine(center.x + radius, center.y, center.x + radius - 10, center.y + 5);
        g.drawLine(center.x, center.y - radius, center.x - 5, center.y - radius + 10);
        g.drawLine(center.x, center.y - radius, center.x + 5, center.y - radius + 10);

        // 画线
        g.drawPolyline(x, y, 2 * radius + 1);

        g.setColor(Color.red);
        g.setFont(new Font("ScanSerif", Font.BOLD, 12));
        g.drawString("X", center.x + radius, center.y - 10);
        g.drawString("Y", center.x + 10, center.y - radius);
    }
}

