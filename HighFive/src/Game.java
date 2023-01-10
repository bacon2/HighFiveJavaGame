import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Game {
	private static JFrame display;
	private static ImageIcon[] boardKey;
	private static int[] board;
	private static JComponent[] components;
	private static int squaresInGame[] = {1,3,4,5,7};
	private static String message;
	private static int points;
	private static int highScore;
	private static String[] rankings = {"beginner", "middler", "almost-ender", "pretty good", "quite good", "just good", "almost great", "almost awesome", "almost epic", "almost legendary", "legendary-but still believable", "almost unbelievable", "unbelievable but technically possible", "impossibly gifted", "you're literally about to break the game"};
	private static Color[] colors = {Color.green, Color.yellow, Color.orange, Color.blue, Color.magenta, Color.pink,      Color.cyan,     Color.lightGray, Color.black,     Color.black,       Color.red,                       Color.yellow,            Color.orange,                           Color.green,         Color.red};
	
	//private static AudioInputStream stream = AudioSystem.getAudioInputStream(new File ("clap.wav"));
	
	private static Thread tooSlow;
	
	public static void main(String[] args)
	{
		display = new JFrame();
		boardKey = new ImageIcon[3];
		board = new int[9]; 
		components = new JComponent[9];
		message = "High Five!";
		
		restartBoard();
		
		Arrays.setAll(components, c -> new JButton());
		components[0]= new JLabel(message, SwingConstants.CENTER);
		components[2]= new JLabel(message, SwingConstants.CENTER);
		components[6]= new JLabel(message, SwingConstants.CENTER);
		components[8]= new JLabel(message, SwingConstants.CENTER);
		
		
		for (JComponent c: components)
        {
        	display.add(c);
        	if (c instanceof JButton)
			{
				addFunctionality((JButton)c);
			}
        }
		
		boardKey[1] = new ImageIcon("hand.jpg");
		boardKey[2] = new ImageIcon("start.jpg");
		
		display.setLayout(new GridLayout(3,3));
        display.setBounds(0, 0, 900, 900);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setBackground(Color.BLACK);
        display.setVisible(true);
        paint();
	}
	public static void moveRandom()
	{
		int i = squaresInGame[(int)(Math.random() * squaresInGame.length)];
		if (board[4] == 2 || Math.random() > 0.7)
		{
			Arrays.setAll(board, z -> 0);
			board[i] = 1;
		}
		paint();
	}
	public static void paint()
	{	
		for (int i = 0; i < 9; i++)
        {
			if (components[i] instanceof JButton)
			{
				((JButton)components[i]).setIcon(boardKey[board[i]]);
			}
			if (components[i] instanceof JLabel)
			{
				((JLabel)components[i]).setText(message);
				try {
					((JLabel)components[i]).setForeground(colors[points/20]);
				} catch (Exception e)
				{
					System.err.println("congratulations, you broke the game with your epic skills");
				}
			}
        }
		//JLabel l = new JLabel();
		//l.set
	}
	private static void addFunctionality(JButton button)
	{
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
	   			{
					if (((JButton)e.getSource()).getIcon() != null)
					{
						points ++;
						message = "Streak " + points;
						moveRandom();
						runNewThread();
					}
					else
					{
						highScore = Math.max(points, highScore);
						points = 0;
						message = "Ya missed! Highscore " + highScore;
						if (tooSlow != null)
						{
							tooSlow.interrupt();
						}	
						restartBoard();
						paint();
					}
	   				
	   			}
	   	});
	}
	private static void initializeThread()
	{
		 tooSlow = new Thread() {
				public void run(){
					try {
						Thread.sleep(700);
						if (!Thread.interrupted())
						{
							highScore = Math.max(points, highScore);
							points = 0;
							message = "Too slow. Highscore " + highScore;
							restartBoard();
							paint();
						}
					} catch (InterruptedException e) {
					}
				}
			};
	}
	private static void runNewThread()
	{
		if (tooSlow != null)
		{
			tooSlow.interrupt();
		}
		initializeThread();
		tooSlow.start();
	}
	public static void restartBoard()
	{
		Arrays.setAll(board, z -> 0);
		board[4] = 2;
	}
}
