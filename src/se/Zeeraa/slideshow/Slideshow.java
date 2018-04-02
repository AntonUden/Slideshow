package se.Zeeraa.slideshow;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Slideshow {
	private JFrame frame = new JFrame();
	private ArrayList<Image> images = new ArrayList<>();
	private ImagePanel imgP = new ImagePanel();
	private ArrayList<String> fileTypes = new ArrayList<>();
	private Path p;

	private File[] folderContent;
	
	private int imageIndex = 0;
	private Timer nextTimer = new Timer(100, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			nextImage();
		}
	});
	
	private Timer updateTimer = new Timer(5000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!Arrays.equals(folderContent, p.toFile().listFiles())) {
				nextTimer.stop();
				System.out.println("Update in image folder detected. Reloading files");
				loadImages(p.toFile());
				nextTimer.start();
			}
		}
	});

	public static void main(String[] args) {
		new Slideshow(args);
	}

	public Slideshow(String[] args) {
		if(!(args.length < 2 || args.length > 2)) {
			try {
				p = Paths.get(args[0]);
				nextTimer.setDelay(Integer.parseInt(args[1]));
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error. use java -jar Slideshow.jar <path> <delay>");
				System.exit(0);
			}
		} else {
			System.err.println("Error. use java -jar Slideshow.jar <path> <delay>");
			System.exit(0);
		}
		
		fileTypes.clear();
		for (String ext : ImageIO.getReaderFormatNames()) {
			fileTypes.add(ext);
		}
		
		loadImages(p.toFile());

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(imgP);
		
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.out.println("Escape Pressed. Closing");
					System.exit(0);
				}
			}
		});
		
		nextTimer.start();
		updateTimer.start();
		nextImage();
	}

	public void nextImage() {
		if(images.size() > 0) {
			if(imageIndex >= images.size()) {
				imageIndex = 0;
			}
			imgP.setImage(images.get(imageIndex));
			imageIndex++;
		} else {
			imgP.clearImage();
		}
	}
	
	public boolean loadImages(File folder) {
		images.clear();
		System.out.println("Loading images...");
		try {
			if (folder.exists()) {
				if (folder.isDirectory()) {
					for (File f : folder.listFiles()) {
						if (fileTypes.contains(getFileExtension(f))) {
							System.out.println("Reading " + f.getAbsolutePath());
							images.add(ImageIO.read(f));
						}
					}
					folderContent = folder.listFiles();
					System.out.println(images.size() + " Images loaded from " + folder.getPath());
					return true;
				} else {
					System.err.println("Error. is file");
					return false;
				}
			} else {
				System.err.println("Error. 404");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}
