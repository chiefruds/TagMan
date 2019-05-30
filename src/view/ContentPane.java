package view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class ContentPane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel leftSideContainer;

	public ContentPane(TimeView timeView, GameView gameView, PlayView playView) {

		// a container to put the timeView below the gameView.
		leftSideContainer = new JPanel();

		// set the layout so that the playView is next to the gameView and timeView.
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// adding the gameView and timeView to a container with BoxLayout.Y_AXIS.
		leftSideContainer.setLayout(new BoxLayout(leftSideContainer, BoxLayout.Y_AXIS));
		leftSideContainer.add(gameView);
		leftSideContainer.add(timeView);

		// add the leftSideContainer and the playView to the contentPane.
		add(leftSideContainer);
		add(playView);
	}

}
