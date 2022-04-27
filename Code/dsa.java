package Code; // this file is in a subfolder of the git repo

import java.util.*;
// import java.lang.*;
import java.io.*;

class Record { // STORE RECORDS
	int id;
	String name;
	int age;
	String role;
	String position;

	public Record() {
		String x = "empty";
	}

	public Record(String rec) {
		StringTokenizer st = new StringTokenizer(rec, ",");
		this.name = st.nextToken();
		this.id = hash(this.name);
		this.age = Integer.parseInt(st.nextToken());
		this.role = st.nextToken();
		this.position = st.nextToken();
		// print();
	}

	public static int hash(String name) {
		// polynomial hashing
		int sum = 0;

		name = name.replaceAll("\\s", ""); // remove spaces
		name = name.toLowerCase(); // ensure case-insensitivity
		
		int a = 10;
		for (int i = 0; i < name.length(); i++) {
			sum += Math.pow(a, i) * name.charAt(i); // a^n * char
		}
		
		return sum;
	}

	void print() {
		System.out.println(
				"\nid: " + id + "\nname: " + name + "\nage: " + age + "\nrole: " + role + "\nposition: " + position);

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

	static int size;
	static int last = 0;

	class Stack // SUBCLASS STACK FOR CREATING STACK OBJECTS
	{
		int t = -1;
		Record[] stack;
		// int size=0;

		public Stack() {

			stack = new Record[size];
			last = size;
		}

		boolean isEmpty() {
			if (t == -1)
				return true;
			return false;
		}

		boolean isFull() {
			if (t == size - 1) {

				return true;

			}
			return false;
		}

		void push(Record a) {

			if (t == size - 1)
				System.out.println("stack full");
			else {

				t = t + 1;
				stack[t] = a;
				// System.out.println("VALUE INSERTED");
			}

		}

		Record pop() {
			Record s = new Record();
			if (!isEmpty()) {
				s = stack[t];
				stack[t] = null;
				t = t - 1;

				// System.out.println(s+" removed from STACK");
			}

			return s;
		}

		void display() { // DISPLAYS RECORDS PRESENT IN STACK
			if (!isEmpty()) {
				for (int i = 0; i < size; i++) {
					if (stack[i] != null)
						stack[i].print();

				}

			}
		}

		Record[] getStack() {
			return stack;
		}

	}

	Stack s1;
	Stack s2;

	void copy(Stack stack1, Stack stack2) {

		for (int i = 0; i < size; i++) {
			stack2.push(stack1.stack[i]);
		}
		// stack2.display();
	}

	public Queue(int n) { // INITIALIZES 2 STACKS- ONE FOR ENQUEUE AND ONE FOR DEQUEUE
		size = n;
		s1 = new Stack();
		s2 = new Stack();
	}

	void enqueue(Record rec) {
		s1.push(rec); // CREATING ENQUEUE STACK
		while (!s1.isEmpty()) {
			Record r = s1.pop();
			s2.push(r);
		}

	}

	Record[] getEnQ() {
		return s1.getStack();
	}

	int getSize() {
		return size;
	}
}

class dsa {
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			File f = new File("data.csv");
			Scanner sc = new Scanner(f, "utf-8");

			Queue q = new Queue(5); // 1st stack

			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				Record recn = new Record(line);
				q.enqueue(recn);
			}
			sc.close();
			/*
			 * System.out.println("enqueue stack");
			 * q.s1.display();
			 * System.out.println("\n\ndequeue stack");
			 * q.s2.display();
			 */

			System.out.println(
					"1: Binarysearch\n" +
							"2: Radix Sort\n" +
							"Choice: ");
			int n = s.nextInt();

			switch (n) {
				case 1: {
					System.out.println("Binary Search\n");
					Record[] s_stack = q.getEnQ();

					int len = q.getSize();
					BinSearch(s_stack, len);
					break;
				}

				case 2: {
					System.out.println("Radix Sort\n");
					Record[] s_stack = q.getEnQ();

					int len = q.getSize();
					radixSort(s_stack, len);
					for (Record re : s_stack) {
						re.print();
						System.out.println();
					}
					break;
				}

				default: {
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static void BinSearch(Record[] list, int len) {

		System.out.print("enter name to search: ");
		String name = s.next();
		int id_no = Record.hash(name);
		Record rex = new Record();

		int flag = 0;
		int l = 0;
		int r = len - 1;
		int mid;

		while (l <= r) {

			mid = (r + l) / 2;

			if (list[mid].getID() == id_no) {
				rex = list[mid];
				flag = 1;
				break;
			}

			else if (list[mid].getID() > id_no) {
				r = mid - 1;
			}

			else {
				l = mid + 1;
			}
		}

		if (flag == 1) {
			System.out.println("record found");
			rex.print();
		} else {
			System.out.println("Record not found");
		}

	}

	static void radixSort(Record[] r, int len) {
		int max = getMax(r, len);

		for (int e = 1; max / e > 0; e *= 10) {
			sort(r, 0, len - 1);
		}

	}

	static void merge(Record arr[], int l, int m, int r) {
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

	static void sort(Record arr[], int l, int r) {
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

	static int getMax(Record[] r, int len) {
		int max = r[0].getID();

		for (int i = 1; i < len; i++) {
			if (max < r[i].getID()) {
				max = r[i].getID();
			}
		}
		return max;
	}

}