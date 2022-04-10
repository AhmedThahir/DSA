package Code; // this file is in a subfolder of the git repo

// packages
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Stack
{
  static int top = -1;
  static int capacity = 10;
  static String[] students = new String[capacity];

	static String inputFile;

	Stack(String file)
	{
		inputFile = file;
	}

  public static void push() throws FileNotFoundException
  {
    Scanner readMyFile = new Scanner( new File(inputFile) );

    while (readMyFile.hasNext()) 
    {
      if(top != capacity)
      {
        top = top + 1;
        students[top] = readMyFile.nextLine();
      }
    }

    readMyFile.close();
  }

  public static void pop() throws FileNotFoundException
  {
    while(top != -1)
    {
      System.out.println(students[top]);
      top = top-1;
    }

  }
}

class Queue
{
  static int f, r, n = 10;
  static String[] students = new String[n];

  public static void enqueue() throws FileNotFoundException
  {
    f = -1;
    r = -1;
  
    while (f==-1) // simply
    {
      if( (f == 0 && r == n-1) || f == r+1 )
      {
        // overflow
      }
      else if (f == -1 && r == -1)
      {
        f = 0;
        r = 0;
      }
      else if (r == n-1)
        r = 0;
      else
        r++;
    
      // students[r] = readMyFile.nextLine();
    }

  }

  public static void dequeue() throws FileNotFoundException
  {
    while( !(f == -1 && r == -1) )
    {
      System.out.println(students[f]);
    
      if (f == n-1)
				f = 0;
      else if (f == r)
      {
        f = -1;
        r = -1;
      }
      else
				f = f+1;
    }

  }

  public static void displayNames() throws FileNotFoundException
  {
    enqueue();
  
    while( !(f == -1 && r == -1) )
    {
      String[] strArray = students[f].split(" ");  
      float cgpa = Float.parseFloat( strArray[strArray.length -1] );
      String name;
    
      if(cgpa < 9f)
      {
        name = strArray[1];
        System.out.println(name);
      }
    
      if (f == n-1)
				f = 0;
      else if (f == r)
      {
        f = -1;
        r = -1;
      }
      else
				f = f+1;
    }
  }

  public static void main(String[] args) throws FileNotFoundException
  {
    enqueue();
    dequeue();
    displayNames();
  }
}

class dsa
{
	static String inputFile = "input.csv";
	static String outputFile = "output.txt";
	
	static Stack stack;
	static Queue queue;

	public static void menu()
	{
		String message = "Hi there! Welcome to the Sports DBMS" + "\n\n" +
			"Enter" + "\n" +
			"1 for linking to the data";
		System.out.println(message);

		Scanner inp = new Scanner(System.in);
		int s = inp.nextInt();
		switch (s) {
			case 1:
				stack = new Stack(inputFile);
				break;
		
			default:
				break;
		}

		inp.close();
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		menu();
	}
}