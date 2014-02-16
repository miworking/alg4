/*  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF.java 
 *
 *  judge if a grid is percolated
 *  will print out the grid status: F for Full, 1 for blocked, 0 for opened 
 *
 *************************************************************************/

/**
 *
 *  @author Julie Zhou
 */
 
public class Percolation
{
    private WeightedQuickUnionUF uf;
    private int N;
    private boolean[] openStatus; //record if this site if empty
    
    public Percolation(int N)
    {
        if (N < 1)
            throw new IllegalArgumentException("N <1 ?");
        
        this.N = N;
        uf = new WeightedQuickUnionUF((N+1)*N+1);
        openStatus = new boolean[(N+1)*N+1];
        
        openStatus[0] = true;
        for (int i = 1; i <= N; i++)
        {
            openStatus[i] = true;            
            uf.union(0, i);           
        }
        
        for (int i = N+1; i <= (N+1)*N; i++)
        {
            openStatus[i] = false;         
        }
    
    }
    
    public void open(int i,int j)
    {
        if(i<1 || i>N)
        {
            System.out.println("i="+i+",while N="+N);
            throw new IndexOutOfBoundsException("i is outside this range");            
        }
        if( j<1 || j>N)
        {
            System.out.println("j="+j+",while N="+N);
            throw new IndexOutOfBoundsException("j is outside this range");
        }
        
// open current site
        int index = xyTo1D(i,j);
        if (openStatus[index]==true)
            return; //opened, do nothing
        openStatus[index]=true;
        
        //connect upper site
        int upIndex = xyTo1D(i-1,j);
        if ( i>1)
        {
            if (openStatus[upIndex])
                uf.union(index,upIndex);
        }
        else // i=1,can still connect to row 0
        {
            uf.union(index,upIndex);
        }        
         
        
        //connect below site         
        if( i<N )   // i=N, then do nothing
        {
            int downIndex = xyTo1D(i+1,j);
            if (openStatus[downIndex])
                uf.union(index,downIndex);
        }       
        
        //connect left site
        if( j>1 )//if j==1, do nothing
        {            
            int leftIndex = xyTo1D(i,j-1);            
            if(openStatus[leftIndex])
                uf.union(index,leftIndex);
        }
        //connect right site
        if ( j<N ) // if j==N, do nothing
        {
            int rightIndex = xyTo1D(i,j+1);
            if(openStatus[rightIndex])
                uf.union(index,rightIndex);
        }
    }
    
    public boolean isOpen(int i, int j)
    {
        if(i<1 || i>N)
        {
            System.out.println("i="+i+",while N="+N);            
            throw new IndexOutOfBoundsException("i is outside this range:"+i);            
        }
        if( j<1 || j>N)
        {
            System.out.println("j="+j+",while N="+N);
            throw new IndexOutOfBoundsException("j is outside this range");
        }        
        return openStatus[xyTo1D(i,j)];
    }
    
    public boolean isFull(int i, int j)
    {   
        if (N==0)            
            throw new IllegalArgumentException("N not initialized");             
        if ( i<1 || i>N )
         throw new IndexOutOfBoundsException("i is outside this range");
        if ( j<1 || j>N)
         throw new IndexOutOfBoundsException("j is outside this range") ; 
        int currentIndex = xyTo1D(i,j);
        return uf.connected(0,currentIndex)&&isOpen(i,j);
    }
    
    public boolean percolates()
    {
       boolean isPercolate = false;
       for ( int j=1; j<=N; j++ )
       {
           if(uf.connected(0,xyTo1D(N,j)))
                  isPercolate = true;
       }
       return isPercolate;
    }
    
    private int xyTo1D(int i, int j)
    {
        if (N==0)            
            throw new IllegalArgumentException("N not initialized");             
        if ( i<0 || i>N )
        {
            System.out.println("i="+i);
            throw new IndexOutOfBoundsException("i is outside this range");
        }
        if ( j<1 || j>N)
         throw new IndexOutOfBoundsException("j is outside this range") ; 
        return i*N+j;
    }
    
    private void isConnected(int i, int j, int ii, int jj)
    {
        if ( i >= 0 && i <=N+1 && j >=1 && j<=N && ii>=0 && ii<=N+1 && jj>=1 && jj<=N)
        {
            int indexij = xyTo1D(i,j);
            int indexiijj = xyTo1D(ii,jj);
            if ( uf.connected(indexij,indexiijj))
                System.out.println("("+i+","+j+")--("+ii+","+jj+")");
            else
                System.out.println("("+i+","+j+")-×-("+ii+","+jj+")");                
        }
    }
    private void printGrid()
    {
        if (N>1)
        {
             System.out.println("N="+N);             
                 
             for( int i=1;i<=N;i++ )
             {
                 for( int j=1;j<=N;j++ )
                 {
                     if (isOpen(i,j))
                     {
                         if(isFull(i,j))                     
                         {
                             System.out.printf("%1c\t",'F');   
                         }
                         else
                         {
                             System.out.printf("%1d\t",0);  
                         }
                     }
                     else
                     {
                         System.out.printf("%1d\t",1);              
                     }                    
                 }
                 System.out.printf("\n");
             }
              
        }
    }
   
   
    
    public static void main(String[] args)
    {       
        
        Percolation grid = new Percolation(5);        
        grid.printGrid();    
        grid.open(1,2);
        grid.open(1,4);
        
        grid.open(2,2);
        grid.open(2,3);        
        grid.open(2,4); 
        
        grid.open(3,3);         
        
        grid.open(4,2); 
        grid.open(4,3); 
        grid.open(4,4); 
        grid.open(4,5); 
        
        
        grid.open(5,5); 
              
        grid.printGrid();        
        if (grid.percolates())
            System.out.println("it percolates");
        else
            System.out.println("it DOES NOT percolate");
    }
}