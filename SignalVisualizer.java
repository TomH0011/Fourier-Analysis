import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignalVisualizer extends JFrame {
    private SignalPanel signalPanel;
    private JPanel controlPanel;
    private JButton addWaveButton;
    private JButton clearButton;
    
    public SignalVisualizer() {
        setTitle("Signal Decomposition Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        
        // Initialize components
        signalPanel = new SignalPanel();
        controlPanel = new JPanel();
        addWaveButton = new JButton("Add Wave");
        clearButton = new JButton("Clear");
        
        // Setup control panel
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(addWaveButton);
        controlPanel.add(clearButton);
        
        // Add components to frame
        add(signalPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addWaveButton.addActionListener(e -> signalPanel.addRandomWave());
        clearButton.addActionListener(e -> signalPanel.clearWaves());
        
        // Center the window
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignalVisualizer visualizer = new SignalVisualizer();
            visualizer.setVisible(true);
        });
    }
} 