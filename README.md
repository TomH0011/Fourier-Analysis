# Signal Decomposition Visualiser

This Java application visualizes the decomposition of signals into their component waves using Fourier Transform principles. It provides an interactive way to understand how complex signals can be broken down into simpler sine waves.

## Features

- Real-time visualisation of wave components
- Interactive addition of random waves
- Clear visualisation of both individual components and the combined signal
- Smooth animation of wave propagation
- Color-coded components for easy identification

## How to Run

1. Make sure you have Java installed on your system
2. Compile the Java files:
   ```
   javac SignalVisualizer.java SignalPanel.java
   ```
3. Run the application:
   ```
   java SignalVisualizer
   ```

## How to Use

1. Launch the application
2. Click the "Add Wave" button to add random wave components
3. Each new wave will be displayed in a different color
4. The red line shows the combined signal
5. Use the "Clear" button to remove all waves and start over

## Understanding the Visualization

- The black horizontal line represents the zero axis
- Each colored line represents a component wave
- The red line shows how these waves combine to form the final signal
- The waves are animated to show their propagation over time

## Technical Details

The application uses:
- Java Swing for the GUI
- Real-time animation using Timer
- Trigonometric functions for wave generation
- Anti-aliasing for smooth rendering 
