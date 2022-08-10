/* Name: Ishmael Garcia (3526912)
 * Dr. Andrew Steinberg
 * COP3503 Fall 2021
 * Programming Assignment 1
 */

package lottery; // lottery package
import java.util.Random; // import random class

public class Lottery 
{
	// private attribute 'ticket'
	private String ticket;
	
	// default constructor
	public Lottery()
	{
		ticket = "";
	}
	
	// overloaded constructor with random class reference
	public Lottery(Random rand)
	{
		ticket = GenerateRandomWinner(rand);
	}
	
	// takes random class object as parameter
	public static String GenerateRandomWinner(Random rand)
	{
		// for loop to assign 6 digit b/w 0-9
		String grw = "";
		for(int i = 0; i < 6; i++)
		{
			grw += Integer.toString(rand.nextInt(10)); // concatenates each digit
		}
		return grw; // returns string of lotto #
	}
	
	// random object ref + maxIndex of array
	public static int GenerateSelectWinner(int maxIndex, Random rand)
	{
		// returns random int / index of winning ticket
		int gsw = rand.nextInt((maxIndex + 1));
		return gsw;
	}
	
	// bubble sort method
	public static void Sort(Lottery [] array) 
	{ 
		int i, j;
		int len = array.length;
		Lottery temp = new Lottery();
		
		// nested for loop to iterate through array and compare
		for (i = 0; i < len-1; i++)
		{
			for (j = 0; j < len-i-1; j++) 
			{
				if (array[j].GetTicket().compareTo(array[j+1].GetTicket()) > 0)
				{
					// swaps to switch higher val with lower val
					temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
				}
			}
		}
	}
	
	// solution1 running in O(n) with linear search method
	// parameters - array, winning ticket, maxIndex
	public static boolean Solution1(Lottery [] array, String win, int maxIndex)
	{
		// single for loop to search through array at most once (best - O(1) | worst - O(n))
		for(int i = 0; i <= maxIndex; i++) 
		{
			if (array[i].GetTicket().equals(win))
			{
				return true; // returns once winning ticket found
			}
		}
		return false; // no winning ticket found
	}
	
	// solution2 running in O(lg n) with binary search method
	// parameters - array, lowest index, highest index, winning ticket
	public static boolean Solution2(Lottery [] array, int l, int h, String win)
	{
		int mid;
		int low = l;
		int high = h;
		
		// while loop dividing through the array until found
		while(low <= high)
		{
			mid = (low + high) / 2; // finds mid point of the current segment of array
			
			// compares current mid point to see if bigger/smaller than winning ticket
			
			if(array[mid].GetTicket().equals(win))
			{
				return true; // winning ticket found
			}
			
			if(array[mid].GetTicket().compareTo(win) < 0)
			{
				low = mid + 1; // search upper segment of array
			}
			
			else
			{
				high = mid - 1; // search lower segment of array
			}
		}
		return false; // no winning ticket found
	}
	
	// getter method for ticket
	public String GetTicket()
	{
		return ticket;
	}

}
