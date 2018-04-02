package se.Zeeraa.slideshow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 283455037591325999L;
	private Image img;

	public ImagePanel() {
		setBackground(Color.BLACK);
		repaint();
	}
	
	public boolean setImage(File f) {
		if(f.exists()) {
			try {
				img = ImageIO.read(f);
				repaint();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void clearImage() {
		img = null;
		repaint();
	}
	
	public boolean setImage(Image i) {
		if(i != null) {
			img = i;
			repaint();
			return true;
		} else {
			return false;
		}
	}
	
	public void paintComponent (Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(img != null) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
