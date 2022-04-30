package Code; // This program is in a subfolder

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Hash {
	public static int hash(String name) {
		// polynomial hashing
		int sum = 0;

		name = name.toLowerCase(); // ensure case-insensitivity

		int a = 10;
		for (int i = 0; i < name.length(); i++) {
			sum += Math.pow(a, i) * name.charAt(i); // a^n * char
		}

		return sum;
	}

}

class Case {
	public static String toTitleCase(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}

		return converted.toString();
	}
}

class Record { // STORE RECORDS
	int id;
	String name;
	int age;
	String role;
	String position;

	public Record(String rec) {
		StringTokenizer st = new StringTokenizer(rec, ",");

		name = st.nextToken();
		name = Case.toTitleCase(name);

		id = Hash.hash(name);
		age = Integer.parseInt(st.nextToken());

		position = st.nextToken();
		position = Case.toTitleCase(position);
		
		role = st.nextToken();
		role = Case.toTitleCase(role);
	}

	public Record() {
	}

	void print() { // PRINT RECORD DETAILS
		JOptionPane.showMessageDialog(null,
				"Id: " + id + "\n" +
						"Name: " + name + "\n" +
						"Age: " + age + "\n" +
						"Role: " + role + "\n" +
						"Position: " + position,
				"Output", 3);
	}

	int getID() {
		return id;
	}

	String getName() {
		return name;
	}

	int getAge() {
		return age;
	}

	String getRole() {
		return role;
	}

	String getPos() {
		return position;
	}

}

class Queue {

	int last = 0;
	int capacity = 1000; // TOTAL CAPACITY OF STACK
	static Stack s1; // ENQ STACK
	static Stack s2; // DEQ STACK

	class Stack // SUBCLASS STACK FOR CREATING STACK OBJECTS
	{
		int t = -1;

		Record[] stack;

		public Stack() { // INITIALISES STACK WITH MENTIONED DEFAULT CAPACITY
			stack = new Record[capacity];
		}

		boolean isEmpty() {
			if (t == -1)
				return true;
			return false;
		}

		boolean isFull() {
			if (getSize() == capacity) {
				return true;
			}
			return false;
		}

		void push(Record a) {

			if (isFull())
				JOptionPane.showMessageDialog(null, "Stack Full", "Error", 3);
			else {

				t = t + 1;
				stack[t] = a;
			}

		}

		Record pop() { // POP FROM STACK
			Record s = new Record();

			if (!isEmpty()) {
				s = stack[t];
				stack[t] = null;
				t = t - 1;
			}

			return s;
		}

		void display() { // DISPLAYS RECORDS PRESENT IN STACK
			if (!isEmpty()) {
				for (int i = 0; i < getSize(); i++) {
					if (stack[i] != null)
						stack[i].print();

				}

			}
		}

		Record[] getStack() {
			return stack;
		} // RETURNS THE STACK ARRAY

		int getSize() {
			return t + 1;
		} // SIZE OF THE STACK(GETS UPDATED)
	}

	public Queue() { // INITIALIZES 2 STACKS- ONE FOR ENQUEUE AND ONE FOR DEQUEUE

		s1 = new Stack();
		s2 = new Stack();
	}

	void enqueue(Record rec) {
		s1.push(rec); // CREATING ENQUEUE STACK
	}

	void dequeue() {
		while (!s1.isEmpty()) { // CREATING DEQUEUE STACK
			Record r1 = s1.pop();
			s2.push(r1);
		}
	}

	Stack getQ() { // DEQUEUES THE ENQ STACK, S1, INTO S2 AND RETURNS S2
		dequeue();
		return s2;

	}

	void binSearch(String name) { // SEARCHES BASED ON ID
		radixSort();

		int id_no = Hash.hash(name);

		Stack stck = getQ(); // FETCHES DEQUEUE STACK OBJECT
		Record[] records = stck.getStack(); // FETCHES STACK ARRAY FOR SEARCHING

		int r = stck.getSize() - 1;
		int l = 0;
		int p = 0;

		Record rex = new Record(); // EMPTY RECORD FOR ASSIGNING FOUND RECORD

		while (l <= r) {
			int mid = (l + r) / 2;

			int id_mid = records[mid].getID();

			if (id_mid == id_no) {
				rex = records[mid];
				p = 1;
				break;
			}

			else if (id_mid < id_no) { // this was >
				l = mid + 1;
			}

			else {
				r = mid - 1;
			}

		}

		if (p == 1) {
			rex.print();
		} else {
			JOptionPane.showMessageDialog(null, name + " not found", "Output", 3);
		}
	}

	void radixSort() {
		Stack stck = getQ(); // FETCHES DEQUEUE STACK OBJECT

		Record[] records = stck.getStack(); // FETCHES STACK ARRAY FOR SEARCHING

		int len = stck.getSize(); // FETCHES THE SIZE OF THE STACK
		int max = getMax(records, len);

		for (int e = 1; max / e > 0; e *= 10) {
			sort(records, 0, len - 1);
		}
	}

	void merge(Record arr[], int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		Record L[] = new Record[n1];
		Record R[] = new Record[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i].getID() <= R[j].getID()) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	void sort(Record arr[], int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = l + (r - l) / 2;

			// Sort first and second halves
			sort(arr, l, m);
			sort(arr, m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	int getMax(Record[] r, int len) {
		int max = r[0].getID();

		for (int i = 1; i < len; i++) {
			if (max < r[i].getID()) {
				max = r[i].getID();
			}
		}
		return max;
	}

}

class Tree {
	Tree child[] = new Tree[100];
	int x = -1;
	String s;
	int p;

	Tree(String str) {
		s = Case.toTitleCase(str);
	}

	static Tree Search(Tree t, String str) {
		if (str.equalsIgnoreCase(t.s))
			return t;
		int c = 0;
		while (t.child[c] != null) {
			Tree x = Search(t.child[c], str);
			if (x != null)
				return x;
			c++;
		}
		return null;
	}

	static String traverse(Tree t, String str, Tree root, String out) {
		if (t != root) {
			if (t.p == 10)
				out = out + "\n" + str + "├──" + t.s;
			else
				out = out + "\n" + str + "└──" + t.s;
			if ((t.p == 10)) {
				str = str + ("│  ");
			} else {
				str = str + ("   ");
			}
		} else {
			out = t.s;
		}
		int c = 0;
		while (t.child[c] != null) {
			out = traverse(t.child[c], str, root, out);
			c++;
		}
		return out;
	}

	static void join(Tree m, String t) {
		if (m.x > -1) {
			m.child[m.x].p = 10;
		}
		m.x++;
		m.child[m.x] = new Tree(t);
		if (m.x == 0)
			m.child[m.x].p = 0;
		else
			m.child[m.x].p = 1;
	}

	public static void main() throws FileNotFoundException {
		String in = "data.csv";
		Scanner sc = new Scanner(new File(in));
		Tree Root = new Tree("Football");

		while (sc.hasNext()) {
			String line = sc.nextLine();
			int i2 = line.lastIndexOf(',');
			String r = Case.toTitleCase(
					line.substring(0, i2));
			String t = Case.toTitleCase(
					line.substring(i2 + 1));
			if (Search(Root, t) == null) {
				join(Root, t);
			}
			Tree m = Search(Root, t);
			int i1 = r.lastIndexOf(',');
			String u = r.substring(0, i1);
			String v = r.substring(i1 + 1);
			if (Search(Root, v) == null) {
				join(m, v);
			}
			int i = u.indexOf(',');
			u = u.substring(0, i);
			Tree z = m.child[m.x];
			join(z, u);
		}
		String output = traverse(Root, "", Root, "");
		System.out.println(output);
		System.out.println();
		System.out.println();
		System.out.println();

		String r = JOptionPane.showInputDialog(null, "Enter Role/Position to be searched", "Tree Search", 3).trim();
		if (Search(Root, r) == null)
			JOptionPane.showMessageDialog(null, "Not Found!", "Error", 3);
		else {
			Tree tr = Search(Root, r);
			if (tr.child[0] == null)
				System.out.println(r + " is a name! Invalid Option");
			else if (tr == Root)
				System.out.println(r + " is the root! Invalid Option");
			else {
				output = traverse(tr, "", tr, "");
				System.out.println(output);
			}
		}

	}
}

public class dsa extends Frame implements ActionListener, WindowListener {
	static Scanner s = new Scanner(System.in);
	static File f = new File("data.csv");
	static Scanner sc;
	static Queue q = new Queue(); // 1st stack

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void input() throws FileNotFoundException {
		sc = new Scanner(f, "utf-8");
		while (sc.hasNextLine()) {

			String line = sc.nextLine();

			Record recn = new Record(line);
			q.enqueue(recn); // INSERTS RECORDS INTO STACK S1
		}
		sc.close();
	}

	public static void menu() {
		String response1 = JOptionPane.showInputDialog(null, "Name to be searched (Case-Insensitive)", "Binary Search", 3);
		String name = response1;
		q.binSearch(name);
		menu();

	}

	public static void main(String[] args) {
		try {
			String in = JOptionPane.showInputDialog(null, "Enter 1 to Search, 2 to see the Tree", "Choose", 3);
			char c = in.charAt(0);
			switch (c) {
				case '1': {
					input();
					menu();
					break;
				}
				case '2': {
					Tree.main();
					break;
				}

				default: {
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ActionEvent handler */
	@Override
	public void actionPerformed(ActionEvent evt) {
	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent evt) {
	}

	@Override
	public void windowClosed(WindowEvent evt) {
	}

	@Override
	public void windowIconified(WindowEvent evt) {
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
	}

	@Override
	public void windowActivated(WindowEvent evt) {
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {
	}
}