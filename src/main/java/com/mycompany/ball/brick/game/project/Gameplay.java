
 
package com.mycompany.ball.brick.game.project;

 import java.awt.event.ActionListener;
 import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener,ActionListener{
 private boolean play = false;
  private int score =0;
  private int totalbricks = 21;
  private Timer time;
  private int delay = 5;
  private int player1= 310;
  private int ballposx = 120;
  private int ballposy = 350;
  private int ballxdir = -1;
  private int ballydir = -2;
  
  private mapbrick map; 
  
  public Gameplay() {
    map = new mapbrick (3,7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    time = new Timer(delay, this);
    time.start();
  }
  
  public void paint(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(1,1, 692, 592);
    map.draw((Graphics2D)g);
    g.setColor(Color.Pink);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(0,0 , 692, 3);
    g.fillRect(691,0,3, 592);
    g.setColor(Color.Green);
    g.setFont(new Font("Arial", Font.BOLD, 25));
    g.drawString(""+score, 590, 30);
    g.setColor(Color.Orange);
    g.fillRect(player1, 550, 100,8);
    g.setColor(Color.yellow);
    g.fillOval(ballposx,ballposy, 20, 20);
    
    if(totalbricks <=0) {
      play = false;
      ballxdir = 0;
      ballydir = 0;
      g.setColor(Color.white);
      g.setFont(new Font("Arial", Font.BOLD, 35));
      g.drawString("YOU WIN!", 180, 290);
      
      g.setFont(new Font("Arial", Font.BOLD, 30));
      g.drawString("Press ENTER to restart",190, 330);
    }
    
    if (ballposy==0) {
      play = false;
      ballxdir = -2;
      ballydir = -1;
      g.setColor(Color.white);
      g.setFont(new Font("Arial", Font.BOLD, 40));
      g.drawString("YOU WIN!", 180, 290);
      
      g.setFont(new Font("Arial", Font.BOLD, 30));
      g.drawString("Press ENTER to restart",190, 330);
    }
    g.dispose();
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    time.start();
    if(play) {
          if (new Rectangle(ballposx,ballposy, 20, 20).intersects(new Rectangle(player1, 550, 100, 8))) {
        ballydir = -ballydir; 
      }
      
      B:
      for (int i=0; i< map.map.length; i++) {
        for (int j=0; j<map.map[0].length; j++) {
          if (map.map[i][j]>0) {
            int brickx = j*map.brickwidth + 80;
            int bricky = i* map.brickheight + 50; 
            int brickwidth = map.brickwidth;
            int brickheight = map.brickheight;
            
            Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
            Rectangle ballrect = new Rectangle(ballposx, ballposy, 20, 20);
            Rectangle brickrect = rect; 
            
            if(ballrect.intersects(brickrect)) {
              map.setBrickval(0,i,j);
              totalbricks--;
              score += 10;
              
              if(ballposx + 19 <= brickrect.x || ballposx + 1 >= brickrect.x + brickrect.width) {
                ballxdir = - ballxdir;
              } else {
                ballydir = -ballydir; 
              }
              
              break B;
            }
          }
        }
      }
      ballposx += ballxdir;
      ballposy += ballydir;
      if (ballposx < 0) { 
        ballxdir = - ballxdir;
      }
      if (ballposy < 0) { 
        ballydir = - ballydir;
      }
      if (ballposx > 670) { 
        ballxdir = - ballxdir;
      }
      
    }
    repaint();
  }


  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if(player1 >= 600) {
        player1 = 600;
      } else {
        moveright();
      }
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
      if(player1 <10) {
        player1 = 10;
      } else {
        moveleft();
      }
    }
    
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) {
        play = true;
        ballposx = 120;
        ballposy = 350;
        ballxdir = -1;
        ballydir = -2;
        player1 = 310;
        score = 0;
        totalbricks = 21;
        map = new mapbrick(3,7);
        
        repaint();
      };
      
    }
  }

  public void moveright() {
    play = true;
    player1+=20;
  }
  public void moveleft() {
    play = true;
    player1 -=20;
  }
  
}  
}
