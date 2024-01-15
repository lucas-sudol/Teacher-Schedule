package ui;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import java.io.*;
import java.util.*;

class TextUI
{
  public static void main (String[] args) throws IOException
  {
    Organizer object = new Organizer();
  }
}

class Organizer
{
  UserInterface obj;
  List lst;
  Queue periodOneQueue;
  Queue periodTwoQueue;
  Queue periodThreeQueue;
  Queue periodFourQueue;
  
  Organizer() throws IOException
  {
    File input = new File ("teachschedule.txt");
    Scanner inFile = new Scanner(input);
    
    lst = new List ();
    periodOneQueue = new Queue();
    periodTwoQueue = new Queue();
    periodThreeQueue = new Queue();
    periodFourQueue = new Queue();
    
    String line;
    String word;
    int ctr = 0;
    
    String name = "";
    String periodOne = "";
    String periodTwo = "";
    String periodThree = "";
    String periodFour = "";
    
    while(inFile.hasNextLine())
    {
      line = inFile.nextLine();
      
      Scanner scannerObj = new Scanner(line);
      
      while(scannerObj.hasNext())
      {
        word = scannerObj.next();
        
        if (ctr == 0)
          name = word;
        
        if (ctr == 1)
        {
          if(!word.equals("0") && !word.equals("1"))
          {
            name = name + " " + word;
            ctr = ctr -1;
          }
          else
          {
            periodOne = word;
          } 
        }
        
        if (ctr == 2)
          periodTwo = word;
        
        if (ctr == 3)
          periodThree = word;
        
        if (ctr == 4)
          periodFour = word;
        
        ctr = ctr + 1;
      }
      ctr = 0;
      
      
      if (periodOne.equals("0"))
      {
        lst.addAtFront(name, 1);
        periodOneQueue.addAtRear(name, 0);
      }
      if (periodTwo.equals("0"))
      {
        lst.addAtFront(name, 2);
        periodTwoQueue.addAtRear(name, 0);
      }
      if (periodThree.equals("0"))
      {
        lst.addAtFront(name, 3);
        periodThreeQueue.addAtRear(name, 0);
      }
      if (periodFour.equals("0"))
      {
        lst.addAtFront(name, 4);
        periodFourQueue.addAtRear(name, 0);
      }
    }
    
    JFrame frame = new JFrame("Test Frame 1");// instantiate frame object
    frame.setSize(1920,1080); // x, y, width, height
    frame.setVisible( true ); // stupid but needed 
    
    obj = new UserInterface("Absent Teacher");
    obj.setSize(450, 150);     
    obj.setVisible(true); 
  }
  
  public void showInput()
  {
    obj.setVisible(true);
  }
  
  class UserInterface extends JFrame implements ActionListener
  {
    JButton exitButton;
    JButton clearButton;
    JButton continueButton;
    
    JTextField text;
    JLabel lbl;
    
    String teacherName;
    
    // constructor for TwoButtons
    UserInterface(String title)                           
    {
      super(title);
      
      exitButton = new JButton("Exit");
      clearButton = new JButton("Clear");
      continueButton = new JButton("Continue");
      
      exitButton.setActionCommand("exit");
      clearButton.setActionCommand("clear");  // set the  command 
      continueButton.setActionCommand("continue");
      
      lbl = new JLabel ( "Enter Name of Absent Teacher:");
      text = new JTextField( 15 );
      
      // register the buttonDemo frame as the listener for both Buttons.
      exitButton.addActionListener(this);
      clearButton.addActionListener(this);
      continueButton.addActionListener(this);
      
      text.addActionListener(this);
      
      continueButton.setForeground(Color.blue);
      
      setLayout(new FlowLayout()); 
      add(lbl);
      add(text);
      
      add(exitButton);
      add(clearButton);                      
      add(continueButton);
      
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    }
    
    public void actionPerformed(ActionEvent evt)
    {
      // check which command has been sent
      if (evt.getActionCommand().equals("exit"))
        System.exit(0);
      else if(evt.getActionCommand().equals("clear"))
      {
        text.setText("");
      }
      else
      {
        teacherName = text.getText();
        
        if(lst.contains(teacherName))
        {
          // call queue checking method
          // if name is in queue, continue
          setVisible(false);
          
          teacherName = lst.teacherMatch(teacherName);
          
          String rowOne [] = new String [5];
          String rowTwo [] = new String [5];
          
          rowOne[0] = teacherName;
          rowTwo[0] = "";
          
          if (lst.teacherMatchPeriod() == 1)
          {
            periodOneQueue.moveOrder(teacherName);
            rowOne[1] = "";
            rowTwo[1] = "";
            rowOne[2] = periodTwoQueue.cycleQueue();
            rowTwo[2] = periodTwoQueue.cycleQueue();
            rowOne[3] = periodThreeQueue.cycleQueue();
            rowTwo[3] = periodThreeQueue.cycleQueue();
            rowOne[4] = periodFourQueue.cycleQueue();
            rowTwo[4] = periodFourQueue.cycleQueue(); 
          }
          
          else if (lst.teacherMatchPeriod() == 2)
          {
            periodTwoQueue.moveOrder(teacherName);
            rowOne[1] = periodOneQueue.cycleQueue();
            rowTwo[1] = periodOneQueue.cycleQueue();
            rowOne[2] = "";
            rowTwo[2] = "";
            rowOne[3] = periodThreeQueue.cycleQueue();
            rowTwo[3] = periodThreeQueue.cycleQueue();
            rowOne[4] = periodFourQueue.cycleQueue();
            rowTwo[4] = periodFourQueue.cycleQueue(); 
          }
          
          else if (lst.teacherMatchPeriod() == 3)
          {
            periodThreeQueue.moveOrder(teacherName);
            rowOne[1] = periodOneQueue.cycleQueue();
            rowTwo[1] = periodOneQueue.cycleQueue();
            rowOne[2] = periodTwoQueue.cycleQueue();
            rowTwo[2] = periodTwoQueue.cycleQueue();
            rowOne[3] = "";
            rowTwo[3] = "";
            rowOne[4] = periodFourQueue.cycleQueue();
            rowTwo[4] = periodFourQueue.cycleQueue();
          }
          
          else if (lst.teacherMatchPeriod() == 4)
          {
            periodFourQueue.moveOrder(teacherName);
            rowOne[1] = periodOneQueue.cycleQueue();
            rowTwo[1] = periodOneQueue.cycleQueue();
            rowOne[2] = periodTwoQueue.cycleQueue();
            rowTwo[2] = periodTwoQueue.cycleQueue();
            rowOne[3] = periodThreeQueue.cycleQueue();
            rowTwo[3] = periodThreeQueue.cycleQueue();
            rowOne[4] = "";
            rowTwo[4] = "";
          }
          else
          {
            System.out.println("ERROR");
          }
          
          //call method from each queue to check minute couter and delete all nodes. 
          periodOneQueue.deleteAllMinutes();
          periodTwoQueue.deleteAllMinutes();
          periodThreeQueue.deleteAllMinutes();
          periodFourQueue.deleteAllMinutes();
          
          //String rowOne [] = new String [] {teacherName, "", "", "", ""};
          //String rowTwo [] = new String [] {"", "", "", "", ""};
          
          OutputInterface outputObj = new OutputInterface("Teacher Fill Schedule", rowOne, rowTwo);
          outputObj.setSize(450, 150);     
          outputObj.setVisible(true);
          text.setText("");
        }
        else
        {
          setVisible(false);
          ErrorInterface errorObj = new ErrorInterface("Error!");
          errorObj.setSize(450, 150);
          errorObj.setVisible(true);
          text.setText("");
        }
      }
      repaint();
    }
    
    public void inputVisible()
    {
      setVisible(true);
    }
    
  }
  
  class OutputInterface extends JFrame implements ActionListener
  {
    JButton exitButton;
    JButton continueButton;
    JLabel lbl;
    OutputInterface(String title, String rowOne [], String rowTwo [])
    {
      super(title);
      
      exitButton = new JButton("Exit");
      continueButton = new JButton("Continue");
      
      exitButton.setActionCommand("exit");
      continueButton.setActionCommand("continue");
      
      exitButton.addActionListener(this);
      continueButton.addActionListener(this);
      lbl = new JLabel ( "Table");
      
      String headers [] = new String [] {"Teacher Name", "Period 1", "Period 2", "Period 3", "Period 4"};
      JPanel bigPanel = new JPanel();
      for (int i = 0; i < 5; i++)
      {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        
        JLabel testLabel = new JLabel (headers[i]);
        panel.add(testLabel);
        column.add(panel);
        
        JTextField text  = new JTextField(rowOne[i]) ;
        text.setSize   ( 300, 100 );     
        text.setVisible( true ); 
        text.setEditable(false);
        column.add(text);
        
        JTextField text2  = new JTextField(rowTwo[i]) ;
        text2.setSize   ( 300, 100 );     
        text2.setVisible( true ); 
        text2.setEditable(false);
        column.add(text2);
        bigPanel.add(column);
      }
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(exitButton, BorderLayout.WEST);
      buttonPanel.add(continueButton, BorderLayout.EAST);
      add(bigPanel, BorderLayout.NORTH);
      add(buttonPanel, BorderLayout.SOUTH);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    }
    
    public void actionPerformed(ActionEvent evt)
    {
      // check which command has been sent
      if (evt.getActionCommand().equals("exit"))
        System.exit(0);
      else
      {
        setVisible(false); 
        showInput();
      }
      repaint();
    }
  }
  
  class ErrorInterface extends JFrame implements ActionListener
  {
    JButton exitButton;
    JButton continueButton;
    
    JLabel lbl;
    
    // constructor for TwoButtons
    ErrorInterface(String title)                           
    {
      super(title);
      
      exitButton = new JButton("Exit");
      continueButton = new JButton("Continue");
      
      
      exitButton.setActionCommand("exit"); 
      continueButton.setActionCommand("continue");
      
      lbl = new JLabel ( "Incorrect name input! Please try again.");
      
      // register the buttonDemo frame as the listener for both Buttons.
      exitButton.addActionListener(this);
      continueButton.addActionListener(this);
      
      
      continueButton.setForeground(Color.blue);
      
      setLayout(new FlowLayout()); 
      add(lbl);
      
      add(exitButton);                      
      add(continueButton);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    }
    
    public void actionPerformed(ActionEvent evt)
    {
      // check which command has been sent
      if (evt.getActionCommand().equals("exit"))
        System.exit(0);
      else
      {
        showInput();
        setVisible(false);
      }
      
      repaint();
    }
  }
}

class List
{
  protected Node head;
  int period = 0;
  //setting up reference fields using and inner class
  class Node
  {
    String item;
    int periodSpare;
    Node link;
    
    Node(String i, int j, Node l)
    {
      item = i;
      periodSpare = j;
      link = l; 
    }
  }
  
  public void addAtFront (String item, int periodSpare) 
  { 
    head = new Node (item, periodSpare, head); // head is the link field 
  } 
  
  public boolean contains(String item)
  {
    boolean returnValue;
    returnValue = false;
    for (Node temp = head; temp!=null; temp = temp.link) 
    {
      if (((temp.item).toLowerCase()).equals(item.toLowerCase()))
      {
        returnValue = true;
      }
    }
    return returnValue;      
  }
  
  public String teacherMatch(String item)
  {
    String returnValue;
    returnValue = "";
    
    for (Node temp = head; temp!=null; temp = temp.link) 
    {
      if (((temp.item).toLowerCase()).equals(item.toLowerCase()))
      {
        returnValue = temp.item;
        period = temp.periodSpare;
      }
    }
    return returnValue;      
  }
  
  public int teacherMatchPeriod()
  {
    return period;
  }
}

class Queue
{
  private Node head;
  int sum = 0;
  //setting up reference fields using and inner class
  class Node
  {
    String item;
    int minutes;
    Node link;
    
    Node(String i, int m, Node l)
    {
      item = i;
      minutes = m;
      link = l; 
    }
  }
  
  public void addAtFront (String item, int minutes) 
  { 
    head = new Node (item, minutes, head); //head is the link field 
  } 
  
  public void addAtRear(String value, int minutes)
  {
    Node newNode = new Node(value, minutes, null);
    
    if (head == null)
    {
      head = newNode;
    }
    else
    {
      for (Node temp = head; temp!= null && temp != newNode;  temp = temp.link)
      {
        if (temp.link == null)
        {
          temp.link = newNode;
        }
      }
    }
  }
  
  public void printList() 
  { 
    for (Node temp = head; temp!=null; temp = temp.link) 
    { 
      if(head == null)
      {
        System.out.println("empty node");
      }
      else
      {
        System.out.println(temp.item); //info field 
      }
    } 
  }
  
  public void deleteFirst()
  {
    if (head == null)
    {
      System.out.println("empty list");
    }
    else
    {
      head = head.link;
    }
  }
  
  public void deleteLast()
  {
    if (head == null)
    {
      System.out.println("empty list");
    }
    else
    {
      for (Node temp = head; temp!=null; temp = temp.link) 
      {
        if (temp.link == null)
        {
          temp = null;
        }
      }
    }
  }
  
  void deleteAll(String x)
  {
    if (head == null)
    {
      System.out.println("Empty list.");
    }
    else
    {
      for (Node temp = head; temp != null; temp = temp.link)
      {
        if ((head.item).equals(x))
        {
          temp = head;
          head = head.link; 
        }
        else if (temp.link != null)
        {
          if ((temp.link.item).equals(x))
          {
            Node deleteNode = temp.link;
            temp.link = temp.link.link;
            deleteNode = null;
          }
        }
      }
    }
  }
  
  void moveOrder(String item)
  {
    if (contains(item))
    {
      int x = 0;
      for (Node temp = head; temp!=null; temp = temp.link) 
      {
        if (((temp.item).toLowerCase()).equals(item.toLowerCase()))
        {
          x = temp.minutes;
        }
      }
      deleteAll(item);
      addAtFront(item,x); 
    }
  }
  
  String cycleQueue()
  {
    String teacher = head.item;
    
    addAtRear(teacher, head.minutes + 40);
    deleteFirst();
    
    return teacher;
  } 
  
  public boolean contains (String item)
  {
    String x = item;
    boolean check = false;
    if (head == null)
    {
      System.out.println("Empty list.");
    }
    for (Node temp = head; temp != null && check == false; temp = temp.link)
    {
      if ((temp.item).equals(x))
      {
        check = true;
      }
    }
    return check;
  }
  
  void deleteAllMinutes()
  {
    if (head == null)
    {
      System.out.println("Empty list.");
    }
    else
    {
      for (Node temp = head; temp != null; temp = temp.link)
      {
        if ((head.minutes) >= 800)
        {
          temp = head;
          head = head.link; 
        }
        else if (temp.link != null)
        {
          if ((temp.link.minutes) >= 800)
          {
            Node deleteNode = temp.link;
            temp.link = temp.link.link;
            deleteNode = null;
          }
        }
      }
    }
  }
}





