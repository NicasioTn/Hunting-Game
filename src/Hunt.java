import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hunt {
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}
}

class MyFrame extends JFrame{
	
	MyPanel bg = new MyPanel();
	public MyFrame() {
		setSize(800,458);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		add(bg);
	}
}

class MyPanel extends JPanel {
	
	
	Image st =  Toolkit.getDefaultToolkit()
			.createImage(System.getProperty("user.dir")
			+ File.separator + "sight1.png");
	
	Image bg = Toolkit.getDefaultToolkit()
			.createImage(System.getProperty("user.dir")
			+ File.separator + "Gotji.jpg");
	
	Image gh =  Toolkit.getDefaultToolkit()
			.createImage(System.getProperty("user.dir")
			+ File.separator + "wow.png");

	int x = 0,y = 0;
	int pX = 0, pY = 0;
	int mX, mY;
	int num = 7;
	int [] ghX = new int[num];
	int [] ghY = new int[num];
	int [] kill = new int[num];
	boolean flag = true;
	
	public MyPanel()
	{
		setSize(800,458);
		setLayout(null);
		
		for(int i= 0;i < num;i++)
		{
			ghX[i] = (int)(Math.random() *650);
			ghY[i] = (int)(Math.random() *250);
		}
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				/*
				 * @sight shooting
				 *  
				 */
				MyMouse(e);
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new MyTime(this), 0,100);
		
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				for(int i = 0 ;i < num; i++)
				{
					if((e.getX() >= ghX[i] && e.getX() <= ghX[i]+100) && (e.getY() >= ghY[i] && e.getY() <= ghY[i]+100))
					{
						kill[i] = 1;
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				Playsound playsound = new Playsound();
				playsound.start();
			
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override 
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	void MyMouse(MouseEvent e) {
		
		this.x = e.getX()-100;
		this.y = e.getY()-70;
		mX = e.getX();
		mY = e.getY();
	}
	void setghpiont(int x, int y)
	{
		this.pX = x;
		this.pY = y;
	}
	
	@Override
	public void paint(Graphics g) {
		
		
		g.drawImage(bg, 0, 0, this);
		
		for(int i = 0;i < num; i++)
		{
			if(kill[i] == 0)
			{
				g.drawImage(gh, ghX[i], ghY[i],100,100, this);
			}
			else if(kill[i] == 1)
			{
				g.drawImage(null, 0, 0,100,100, this);
			}
			
		}
		Font font = new Font("Tahoma", Font.BOLD, 20);
		g.setColor(Color.GREEN);
		g.setFont(font);
		
		
		g.drawImage(st, x,y,200,150, this);
		
	}
	
}
class MyTime extends TimerTask
{

	int rnX, rnY;
	MyPanel panel ;
	
	MyTime(MyPanel panel)
	{
		this.panel = panel;
		
	}
	@Override
	public void run() {
		for(int i = 0;i < panel.num;i++)
		{
			rnX = (int)(new Random().nextInt(80)-40);
			rnY = (int)(new Random().nextInt(80)-40);
			
			int x2 =  panel.ghX[i] + rnX;
			int y2 =  panel.ghY[i] + rnY;
			
			
			if(x2 > 650)
			{
				x2 = 650;
			}
			if(x2 < 0)
			{
				x2 = 0;
			}
			if(y2 > 250)
			{
				y2 = 250;
			}
			if(y2 < 0)
			{
				y2 = 0;
			}
			
			panel.ghX[i] = x2;
			panel.ghY[i] = y2;
			
			panel.setghpiont(x2, y2);
			/*
			 * System.out.println(panel.ghX[i]);
			 * System.out.println(panel.ghY[i]);	
			 */

		}
		panel.repaint();
		
	}
	
}
class Playsound extends Thread
{
	@Override
	public void run() {
		
		try {
		
			File wavFile = new File(System.getProperty("user.dir")
					+ File.separator + "Shooting.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(wavFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}