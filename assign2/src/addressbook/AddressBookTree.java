/* Name: Ishmael Garcia (3526912)
 * Dr. Andrew Steinberg
 * COP3503 Fall 2021
 * Programming Assignment 2
 */

package addressbook;
import java.util.Stack;

public class AddressBookTree<T extends Comparable<T>,U> 
{
	// initialize nil and root
	private Node<T,U> nil = new Node<T,U>();
	private Node<T,U> root = nil;
	
	// default constructor with default values
	public AddressBookTree()
	{
		root.parent = nil;
		root.left = nil;
		root.right = nil;
	}
	
	//insert method passing name and office
	public void insert(T name, U office)
	{
		//initialize nodes
		Node<T,U> z = new Node<T, U>(name, office);
		Node<T,U> y = nil;
		Node<T,U> x = this.root;
		
		//textbook defined insert method
		while(x != nil)
		{
			y = x;
			
			// if z goes before x
			if(z.name.compareTo(x.name) < 0)
			{
				x = x.left;
			}
			
			else
			{
				x = x.right;
			}
			
			z.parent = y;
			
			if(y == nil)
				root = z;
			else if(z.name.compareTo(y.name) < 0)
				y.left = z;
			else
				y.right = z;
			
			z.left = nil;
			z.right = nil;
			z.color = Node.RED;
			insertfix(z); //fix tree after insert
		}
	} 
	
	//fix after insert method
	private void insertfix(Node<T,U> z)
	{
		Node<T,U> y = nil;
		
		while (z.parent.color == Node.RED)
		{

			//if z parent is left child
			if (z.parent == z.parent.parent.left)
			{
				y = z.parent.parent.right;

				// if y is red recolor
				if (y.color == Node.RED){
					z.parent.color = Node.BLACK;
					y.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					z = z.parent.parent;
				}
				//if y is black & z is a right child
				else if (z == z.parent.right)
				{

					// rotate
					z = z.parent;
					leftrotate(z);
				}

				else
				{
					// recolor and rotate
					z.parent.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					rightrotate(z.parent.parent);
				}
			}

			else
			{
				// y to zs cousin
				y = z.parent.parent.left;

				//if y is red recolor
				if (y.color == Node.RED){
					z.parent.color = Node.BLACK;
					y.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					z = z.parent.parent;
				}

				//if y is black and z is a left child
				else if (z == z.parent.left)
				{
					// rightRotate
					z = z.parent;
					rightrotate(z);
				}
				
				else
				{
					//recolor and rotate
					z.parent.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					leftrotate(z.parent.parent);
				}
			}
		}
	//color root black at all times
	root.color = Node.BLACK;
	}
	
	//textbook delete method
	public void deleteNode(T name)
	{
		Node<T,U> z  = root;
		Node<T,U> x,y;
		//loop finds node belonging to name
		while(z != nil)
		{
			if(z.name.equals(name))
				break;
			else if(z.name.compareTo(name) < 0)
				z = z.right;
			else
				z = z.left;
		}
		
		y = z;
	    int yOC = y.color;
	    
	    //find leaf
	    if (z.left == nil) 
	    {
	      x = z.right;
	      transplant(z, z.right);
	    } 
	    
	    else if (z.right == nil) 
	    {
	      x = z.left;
	      transplant(z, z.left);
	    } 
	    
	    else 
	    {
	      y = minimum(z.right);
	      yOC = y.color;
	      x = y.right;
	      
	      if (y.parent == z) 
	      {
	        x.parent = y;
	      } 
	      
	      else 
	      {
	        transplant(y, y.right);
	        y.right = z.right;
	        y.right.parent = y;
	      }

	      transplant(z, y);
	      y.left = z.left;
	      y.left.parent = y;
	      y.color = z.color;
	    }
	    
	    //if black fix deletion
	    if (yOC == 0) 
	    {
	      deletefix(x);
	    }
		
		
	}
	
	//textbook fix delete method
	private void deletefix(Node<T,U> x)
	{
		Node<T,U> w;
		//loop while not root and node is black
	    while (x != root && x.color == Node.BLACK) 
	    {
	      if (x == x.parent.left) 
	      {
	        w = x.parent.right;
	        //if red rotate left
	        if (w.color == Node.RED) 
	        {
	          w.color = Node.BLACK;
	          x.parent.color = Node.RED;
	          leftrotate(x.parent);
	          w = x.parent.right;
	        }

	        //if left black and right black parent must be red
	        if (w.left.color == Node.BLACK && w.right.color == Node.BLACK)
	        {
	          w.color = Node.RED;
	          x = x.parent;
	        }
	        
	        else
	        {
	        	//if right black change left blck and parent red
	          if (w.right.color == Node.BLACK) 
	          {
	            w.left.color = Node.BLACK;
	            w.color = Node.RED;
	            rightrotate(w);
	            w = x.parent.right;
	          }

	          //recolor
	          w.color = x.parent.color;
	          x.parent.color = Node.BLACK;
	          w.right.color = Node.BLACK;
	          leftrotate(x.parent);
	          x = root;
	        }
	        
	      }
	      else
	      {
	        w = x.parent.left;
	        //if node red recolor and rotate
	        if (w.color == Node.RED)
	        {
	          w.color = Node.BLACK;
	          x.parent.color = Node.RED;
	          rightrotate(x.parent);
	          w = x.parent.left;
	        }

	        //if right/left is black then node red
	        if (w.right.color == Node.BLACK && w.left.color == Node.BLACK)
	        {
	          w.color = Node.RED;
	          x = x.parent;
	        }
	        
	        else
	        {
	        	//repeat but other side
	          if (w.left.color == Node.BLACK)
	          {
	            w.right.color = Node.BLACK;
	            w.color = Node.RED;
	            leftrotate(w);
	            w = x.parent.left;
	          }

	          w.color = x.parent.color;
	          x.parent.color = Node.BLACK;
	          w.left.color = Node.BLACK;
	          rightrotate(x.parent);
	          x = root;
	        }
	      }
	    }
	    x.color = Node.BLACK;
	}
	
	//finding minimum method to help
	public Node<T,U> minimum(Node<T,U> node)
	{
		while (node.left != nil) 
		{
			node = node.left;
		}
		    return node;
	}
	
	//textbook left rotate method
	private void leftrotate(Node<T,U> x)
	{
		Node<T,U> y;
		y = x.right;
		x.right = y.left;

		//check for existence of y.left and make pointer changes
		if (y.left != nil)
			y.left.parent = x;
		y.parent = x.parent;

		// if parent nil
		if (x.parent == nil)
			root = y;

		//if x is the left child of it's parent
		else if (x.parent.left == x)
			x.parent.left = y;

		//if x is the right child of it's parent.
		else
			x.parent.right = y;
		y.left = x;
		x.parent = y;
	}
	
	private void rightrotate(Node<T,U> y)
	{
		Node<T,U> x = y.left;
        y.left = x.right;

        //check for existence of x.right
        if (x.right != nil)
            x.right.parent = y;
        x.parent = y.parent;

        //if  y.parent is nil
        if (y.parent == nil)
            root = x;

        //if y is a right child of its parent.
        else if (y.parent.right == y)
            y.parent.right = x;

        //if y is a left child of its parent.
        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;
	}
	
	//textbook def of transplant method
	private void transplant(Node<T,U> u, Node<T,U> v)
	{
		//if parent null
		if (u.parent == nil) 
		{
			root = v;
		} 
		//if left parent
		else if (u == u.parent.left) 
		{
			u.parent.left = v;
		} 
		//if right parent
		else 
		{
			u.parent.right = v;
		}
		
		v.parent = u.parent;
	}
	
	//attempt at display
	public void display()
	{
		if(root == nil)
            return;
 
        Stack<Node<T,U>> s = new Stack<Node<T,U>>();
        Node<T,U> currentNode = root;
 
        while(!s.empty() || currentNode!=nil){
 
            if(currentNode!=nil)
            {
                s.push(currentNode);
                currentNode=currentNode.left;
            }
            else
            {
                Node<T,U> n = s.pop();
                System.out.println(n.name + " " + n.office);
                currentNode = n.right;
            }
        }
	}
	
	//count black node method
	public int countBlack(Node<T,U> x)
	{
		int black = 0;
		//exits recursive call
		if (x == nil)
		{
	        return 0;
	    }
		//recursive call for right/left side
	    black += countBlack(x.left);
	    black += countBlack(x.right);

	    //counts only black colors
	    if(x.color == Node.BLACK)
	    {
	        black++;
	    }
		return black;
	}
	
	//count red node method
	public int countRed(Node<T,U> x)
	{
		int red = 0;
		//exits recursive call
		if (x == null) 
		{
	        return 0;
	    }
		//recursive call for left/right side
	    red += countRed(x.left);
	    red += countRed(x.right);
	    //counts only red colors
	    if(x.color == Node.RED)
	    {
	        red++;
	    }
		return red;
	}
	
	//access root
	public Node<T,U> getRoot() 
	{
		return this.root;
	}

}

//Node class
class Node<T extends Comparable<T>,U> 
{
	//constant for black and red color
    public static final int BLACK = 0;
    public static final int RED = 1;
	// the key of each node
	public T name;
	public U  office;

	//parent, left and right nodes
    Node<T, U> parent;
    Node<T, U> left;
    Node<T, U> right;
    // the color of a node
    public int color;

    //default constructor set values
    Node()
    {
        color = BLACK;
        parent = null;
        left = null;
        right = null;
    }

	// Constructor which sets key to the argument.
	Node(T name, U office)
	{
        this();
        this.name = name;
        this.office = office;
	}
}