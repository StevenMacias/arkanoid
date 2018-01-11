package arkanoid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MockDB;

public class Main {
	public static int X = 0;
	public static int Y = 0;
	public static int WIDTH = 437;
	public static int HEIGHT = 490;
	public static int BUTTON_WIDTH = 100;
	public static int BUTTON_HEIGHT = 50;
	public static int BUTTON_TOP_MARGIN = 20;
	public static int BUTTON_X = WIDTH / 2 - BUTTON_WIDTH / 2;
	public static int BUTTON_Y = HEIGHT / 2;
	public static int TITLE_IMAGE_WIDTH = 300;
	public static int TITLE_IMAGE_HEIGHT = 100;
	public static int TABLE_WIDTH = (int) (WIDTH * 0.75);
	public static int TABLE_HEIGHT = WIDTH / 2;

	public static JFrame menu;
	public static JFrame playScreen;
	public static JButton btnPlay;
	public static JButton btnScores;
	public static JButton btnExit;
	public static JButton btnBack;
	public static JScrollPane scrollPane;
	public static ArrayList<String[]> listData = new ArrayList<String[]>();
	static JTable table;

	public static String name;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		MockDB db = new MockDB(); 
		db.connect();
		listData = db.getAllGames();
		initMenu();
		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) { // 1-left, 2-middle, 3-right button
					startGame();
				}
			}
		});
		btnScores.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) { // 1-left, 2-middle, 3-right button
					showScores();
				}
			}
		});
		btnExit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) { // 1-left, 2-middle, 3-right button
					db.close();
					System.exit(0);
				}
			}
		});
		btnBack.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) { // 1-left, 2-middle, 3-right button
					btnExit.setVisible(true);
					btnPlay.setVisible(true);
					btnScores.setVisible(true);
					btnBack.setVisible(false);
					scrollPane.setVisible(false);
				}
			}
		});

	}

	public static void initMenu() {
		menu = new JFrame();
		menu.setBounds(Main.X, Main.Y, Main.WIDTH, Main.HEIGHT);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.getContentPane().setLayout(null);
		menu.getContentPane().setBackground(Color.black);

		Object columnNames[] = { "Name", "Score" };
		Object[][] rowData = (Object[][]) listData.toArray(new Object[listData.size()][columnNames.length]);
		table = new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds((WIDTH - TABLE_WIDTH) / 2, HEIGHT / 3, TABLE_WIDTH, TABLE_HEIGHT);
		menu.add(scrollPane, BorderLayout.CENTER);

		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("/title.png")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(TITLE_IMAGE_WIDTH, TITLE_IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		JLabel labelImage = new JLabel();
		labelImage.setIcon(imageIcon);
		menu.getContentPane().add(labelImage);
		labelImage.setBounds((WIDTH - TITLE_IMAGE_WIDTH) / 2, HEIGHT / 8, TITLE_IMAGE_WIDTH, TITLE_IMAGE_HEIGHT);

		btnPlay = new JButton("PLAY");
		btnPlay.setBounds(BUTTON_X, BUTTON_Y + (BUTTON_HEIGHT + BUTTON_TOP_MARGIN) * 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		menu.getContentPane().add(btnPlay);

		btnScores = new JButton("SCORES");
		btnScores.setBounds(BUTTON_X, BUTTON_Y + (BUTTON_HEIGHT + BUTTON_TOP_MARGIN) * 1, BUTTON_WIDTH, BUTTON_HEIGHT);
		menu.getContentPane().add(btnScores);

		btnExit = new JButton("EXIT");
		btnExit.setBounds(BUTTON_X, BUTTON_Y + (BUTTON_HEIGHT + BUTTON_TOP_MARGIN) * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		menu.getContentPane().add(btnExit);

		btnBack = new JButton("BACK");
		btnBack.setBounds(BUTTON_X, BUTTON_Y + (BUTTON_HEIGHT + BUTTON_TOP_MARGIN) * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		menu.getContentPane().add(btnBack);
		btnBack.setVisible(false);
		scrollPane.setVisible(false);

		menu.setVisible(true);
	}

	public static void startGame() {
		name = JOptionPane.showInputDialog(menu, "What's your name?");
		menu.setVisible(false);
		playScreen = new JFrame();
		Game game;
		try {
			game = new Game();
			// Game needs to extend from a JPanel to add it to a Jframe.
			playScreen.add(game);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		playScreen.setBounds(0, 0, WIDTH, HEIGHT);
		playScreen.setTitle("Arkanoid");
		playScreen.setResizable(true);
		playScreen.setVisible(true);
		playScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void showScores() {
		Object columnNames[] = { "Name", "Score" };
		Object[][] rowData = (Object[][]) listData.toArray(new Object[listData.size()][columnNames.length]);
		table = new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds((WIDTH - TABLE_WIDTH) / 2, HEIGHT / 3, TABLE_WIDTH, TABLE_HEIGHT);
		menu.add(scrollPane, BorderLayout.CENTER);

		btnExit.setVisible(false);
		btnPlay.setVisible(false);
		btnScores.setVisible(false);
		btnBack.setVisible(true);
		scrollPane.setVisible(true);

	}

	public static void closeGame() {
		playScreen.dispose();
	}

	public static void saveScore(int score) {
		System.out.println(name + " - " + score);
		String[] myString1 = { name, Integer.toString(score) };
		listData.add(myString1);
		

	}

}
