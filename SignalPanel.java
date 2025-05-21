import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SignalPanel extends JPanel {
    private List<Wave> waves;
    private javax.swing.Timer animationTimer;
    private double time;
    private static final int POINTS = 1000;
    private static final double TIME_STEP = 0.05;
    private JTextArea waveInfoArea;
    
    public SignalPanel() {
        waves = new ArrayList<>();
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        // Create wave info text area
        waveInfoArea = new JTextArea(5, 30);
        waveInfoArea.setEditable(false);
        waveInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(waveInfoArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        add(scrollPane, BorderLayout.SOUTH);
        
        // Setup animation timer
        animationTimer = new javax.swing.Timer(16, e -> {
            time += TIME_STEP;
            repaint();
        });
        animationTimer.start();
    }
    
    public void addRandomWave() {
        Random random = new Random();
        double amplitude = 50 + random.nextDouble() * 100;
        double frequency = 0.5 + random.nextDouble() * 2;
        double phase = random.nextDouble() * Math.PI * 2;
        Color color = new Color(
            random.nextInt(200),
            random.nextInt(200),
            random.nextInt(200)
        );
        
        Wave newWave = new Wave(amplitude, frequency, phase, color);
        waves.add(newWave);
        
        // Add wave information to the text area
        String waveInfo = String.format("Added Wave %d:\nAmplitude: %.2f\nFrequency: %.2f\nPhase: %.2f\n\n",
            waves.size(), amplitude, frequency, phase);
        waveInfoArea.append(waveInfo);
    }
    
    public void clearWaves() {
        waves.clear();
        waveInfoArea.setText("");
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight() - 100; // Adjust height for wave info area
        int centerY = height / 2;
        
        // Calculate box dimensions
        int boxWidth = width / 3;
        int boxHeight = height / 2;
        int padding = 20;
        
        // Draw individual waves in boxes
        for (int i = 0; i < waves.size(); i++) {
            Wave wave = waves.get(i);
            int boxX = (i % 3) * boxWidth + padding;
            int boxY = (i / 3) * boxHeight + padding;
            
            // Draw box
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(boxX, boxY, boxWidth - 2 * padding, boxHeight - 2 * padding);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(boxX, boxY, boxWidth - 2 * padding, boxHeight - 2 * padding);
            
            // Draw wave in box
            g2d.setColor(wave.color);
            drawWaveInBox(g2d, wave, boxX, boxY, boxWidth - 2 * padding, boxHeight - 2 * padding);
        }
        
        // Draw combined wave at the bottom
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        drawCombinedWave(g2d, height - boxHeight/2);
    }
    
    private void drawWaveInBox(Graphics2D g2d, Wave wave, int boxX, int boxY, int boxWidth, int boxHeight) {
        double[] points = new double[POINTS];
        int centerY = boxY + boxHeight/2;
        
        for (int i = 0; i < POINTS; i++) {
            double x = (double) i / POINTS * boxWidth;
            double t = x / boxWidth * 10 + time;
            points[i] = wave.amplitude * Math.sin(2 * Math.PI * wave.frequency * t + wave.phase);
        }
        
        for (int i = 1; i < POINTS; i++) {
            int x1 = boxX + (int) ((i - 1) * boxWidth / POINTS);
            int y1 = (int) (centerY - points[i - 1]);
            int x2 = boxX + (int) (i * boxWidth / POINTS);
            int y2 = (int) (centerY - points[i]);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
    
    private void drawCombinedWave(Graphics2D g2d, int centerY) {
        int width = getWidth();
        double[] combinedPoints = new double[POINTS];
        
        for (int i = 0; i < POINTS; i++) {
            double x = (double) i / POINTS * width;
            double t = x / width * 10 + time;
            double sum = 0;
            
            for (Wave wave : waves) {
                sum += wave.amplitude * Math.sin(2 * Math.PI * wave.frequency * t + wave.phase);
            }
            
            combinedPoints[i] = sum;
        }
        
        for (int i = 1; i < POINTS; i++) {
            int x1 = (int) ((i - 1) * width / POINTS);
            int y1 = (int) (centerY - combinedPoints[i - 1]);
            int x2 = (int) (i * width / POINTS);
            int y2 = (int) (centerY - combinedPoints[i]);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
    
    private static class Wave {
        double amplitude;
        double frequency;
        double phase;
        Color color;
        
        Wave(double amplitude, double frequency, double phase, Color color) {
            this.amplitude = amplitude;
            this.frequency = frequency;
            this.phase = phase;
            this.color = color;
        }
    }
} 