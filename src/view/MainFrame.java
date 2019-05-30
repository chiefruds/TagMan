package view;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private ContentPane contentPane;
	
	public MainFrame(TimeView timeView, GameView gameView, PlayView playView) {
		contentPane = new ContentPane(timeView, gameView, playView);
		
		//initializing the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("TagMan by Ruben de Heer");
		setResizable(false);
		setContentPane(contentPane);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		
	}

}
