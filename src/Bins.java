import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
	public static final String DATA_FILE = "example.txt";

	/**
	 * Reads list of integer data from the given input.
	 *
	 * @param input tied to an input source that contains space separated numbers
	 * @return list of the numbers in the order they were read
	 */
	public List<Integer> readData (Scanner input) {
		List<Integer> results = new ArrayList<Integer>();
		while (input.hasNext()) {
			results.add(input.nextInt());
		}
		return results;
	}
	public static int newHelp(List<Integer> data,PriorityQueue<Disk> pq, int total){
	//Takes in data, a list of integers
	//takes in a priority queue pq of disks
	//takes in an integer called total
		
	
		pq.add(new Disk(0));
	//add a disk with ID of 0 to priority queue pq
		int diskId = 1;
	
		for (Integer size : data) {
			//for loop through every integer in list data, a parameter, which is a list of all integers in scanner
			Disk d = pq.peek();
			//Disk d equals the first variable on priority queue pq
			if (d.freeSpace() > size) {
			//this finds a disk with empty space and adds the size to the free disk
				// while free space on the disk is greater than integer size in data, add size to that disk
				pq.poll();
				d.add(size);
				pq.add(d);
			} else {
				// if free space on disk is greater than integer size in data
				//create new Disk d2, and adds intger size to d2 as wlel as incrementing the disk ID
				//add d2 to pq
				Disk d2 = new Disk(diskId);
				diskId++;
				d2.add(size);
				pq.add(d2);
			}
			//increment intger total for every integer in data
			total += size;
		}
		return total;
	}



	/**
	 * The main program.
	 */
	public static void main (String args[]) {
		Bins b = new Bins();
		Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
		List<Integer> data = b.readData(input);
				//data is a list of all integers in scanner input
				PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
		pq.add(new Disk(0));

		int diskId = 1;
		int total = 0;
		total=newHelp(data, pq, total);

		System.out.println("total size = " + total / 1000000.0 + "GB");
		System.out.println();
		System.out.println("worst-fit method");
		System.out.println("number of pq used: " + pq.size());
		while (!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		System.out.println();

		Collections.sort(data, Collections.reverseOrder());
		pq.add(new Disk(0));

		diskId = 1;
		for (Integer size : data) {
			Disk d = pq.peek();
			if (d.freeSpace() >= size) {
				pq.poll();
				d.add(size);
				pq.add(d);
			} else {
				Disk d2 = new Disk(diskId);
				diskId++;
				d2.add(size);
				pq.add(d2);
			}
		}

		System.out.println();
		System.out.println("worst-fit decreasing method");
		System.out.println("number of pq used: " + pq.size());
		while (!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		System.out.println();
	}
}
