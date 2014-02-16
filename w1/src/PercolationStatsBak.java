import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class PercolationStats
{
    private int N;
    private int T;
    private double[] probability;
    private double mean;
    private double stddev;
    public PercolationStats(int N, int T)
    {
    	if ( N<=0 )
    		throw new IllegalArgumentException("N > 0 ?");
    	if ( T<=0 )
    		throw new IllegalArgumentException("T > 0 ?");    	
    	this.N = N;
    	this.T = T;
    	probability = new double[T];
    	mean = 0;
    	stddev = 0;
    }
    
    public void tryPercolate()
    {
    	boolean bPercolated=false;
    	boolean bOpened = false;
    	int openNum = 0;
    	int randomi = 0;
    	int randomj = 0;	
    	
    	String path = "./input/";
        String fileName = null ;
        FileWriter fo = null;
        BufferedWriter bw = null;

    	
    	for ( int i=0; i<T; i++ )
    	{
    		 fileName = path + String.format("%08d", i+1)+".txt";
    	      System.out.println(fileName);
    	      try
    	      {
    	    	  fo = new FileWriter(fileName);
    	    	  bw = new BufferedWriter(fo);
    	    	  bw.write(String.valueOf(N));
    	    	  bw.newLine();
    	    	  System.out.println("**********************************************************************************************************");
    	    	  
    	    	  Percolation pl = new Percolation(N);
    	    	  bPercolated = false;
    	    	  openNum=0; // the number of opened grids
    	    	  System.out.println("i="+i);    	    	 
    	    	  while  ( !bPercolated )
    	    	  {   	    	
	        			bOpened=false;
	        			while (!bOpened)
	        			{
	        				randomi = (int)(StdRandom.uniform(N))+1;
	        				randomj = (int)(StdRandom.uniform(N))+1;
	        				if ( !pl.isOpen(randomi,randomj))
	        				{			
	        					pl.open(randomi, randomj);
	        					bw.write(String.valueOf(randomi)+"\t"+String.valueOf(randomj));
	        					bw.newLine();
	        					openNum ++;
	        					bPercolated = pl.percolates();
	        					bOpened = true;    
	        				}
	        			}
	        		}
	        		System.out.println("final openNum="+openNum);
	        		probability[i] = ((double)openNum)/((double)N)/((double)N);    		
	        		System.out.println("probability:"+probability[i]);
    	      } 
    	      catch(IOException e)
    	      {
    	       e.printStackTrace(); 
    	      } 
    	      finally
    	      { 
    	       try
    	       {
    	        bw.close();
    	        fo.close(); 
    	       }
    	       catch (IOException e)
    	       {
    	        e.printStackTrace();
    	       }
    	      }   	
    	}
    }
    
    public double mean()
    {
    	double sum = 0;
    	for (int i=0; i<T; i++)
    	{
    		sum += probability[i];
    	}
    	mean = (double)(sum / T) ;
    	return mean;
    }
    
    public double stddev()
    {
    	if ( T>1 )
    	{
    		double sumDev = 0;
    		for (int i=0;i<T;i++)
    		{
    			sumDev += (this.probability[i]-mean)*(this.probability[i]-mean);
    		}
    		sumDev = (double)(sumDev / (T-1));
    		this.stddev = Math.sqrt(sumDev);
    		return stddev;
    	}
    	else 
    		return Double.NaN;
    }
    
    public double confidenceLo()
    {
    	if ( T>1 )
    	{ 
    		return (double)(mean-1.96*(double)(stddev/Math.sqrt(T)));
    	}
    	if ( T==0 )
    	{
    		return mean;
    	}
    	return -1;
    	
    }
    
    public double confindenHi()
    {
    	if ( T>1 )
    	{
    		return (double)(mean+1.96*(double)(stddev/Math.sqrt(T)));
    		}
    	if ( T==0 )
    	{
    		return mean;
    		}
    	return -1;
    
    }
    
    private void debugRandom()
    {
    	int randomj;
    	double sum = 0;
    	int times = 1000000;
    	System.out.println("N:"+N);
    	int counts[] = new int[N];    	
    	for ( int i=0; i<times; i++ )
    	{
    		randomj = (int)(StdRandom.uniform(N))+1;
    		if (randomj==1)
    			counts[0]++;
    		else if (randomj==2)
    			counts[1]++;
    		else if (randomj==3)
    			counts[2]++;
    		else if (randomj==4)
    			counts[3]++;
    		else if (randomj==5)
    			counts[4]++;
    		else if (randomj==6)
    			counts[5]++;
    		else if (randomj==7)
    			counts[6]++;
    		else if (randomj==8)
    			counts[7]++;
    		else if (randomj==9)
    			counts[8]++;
    		else if (randomj==10)
    			counts[9]++;
    		else    			
    			System.out.println("random:"+randomj);
    		
    		sum += randomj;
//    		System.out.println("random:"+randomj);
    		
    	}
    	
    	System.out.println("mean = "+(double)(sum/times));
    	for ( int j=0; j<N; j++)
    	{
    		System.out.println("counts["+(j+1)+"]="+counts[j]);
    	}
    		    	
    }

    private void debugFileName()
    {
    	String path = "./input/";
    	String fileName = null ;
    	FileWriter fo = null;
    	BufferedWriter bw  = null;
    	
    	for ( int i=0; i<=100; i++)
    	{    	
    		fileName = path + String.format("%08d", i)+".txt";
    		System.out.println(fileName);
    		try
    		{
    			fo = new FileWriter(fileName);
    			bw = new BufferedWriter(fo);
    			bw.write("Hello world");
    			bw.newLine();
    		} 
    		catch(IOException e)
    		{
    			e.printStackTrace();    			
    		} 
    		finally
    		{ 
    			try
    			{
    				bw.close();
    				fo.close();    				
    			}
    			catch (IOException e)
    			{
    				e.printStackTrace();
    			}
    		}   
    	}
    }
    
    public static void main(String[] args) throws Exception 
    {
    	if (args.length==2 )
    	{
    		int N = Integer.parseInt(args[0]);
    		int T = Integer.parseInt(args[1]);
    		if ( N<=0 )
    			throw new IllegalArgumentException("N >0 ?");
    		if ( T<=0 )
    			throw new IllegalArgumentException("T >0 ?");
    		PercolationStats pclStat = new PercolationStats(N,T);
//    		pclStat.debugRandom();
//    		pclStat.debugFileName();
    		pclStat.tryPercolate();
    		System.out.println();
    		System.out.println("mean = "+ pclStat.mean());
    		System.out.println("Stddev = "+ pclStat.stddev());
    		System.out.println("95% confidence inverval = "+pclStat.confidenceLo()+","+pclStat.confindenHi());   		        	
    		for (int k=1;k<=T;k++)
    		{
    			PercolationDraw.batDraw("./input/"+String.format("%08d",k)+".txt");
    			
    		}
    	}    		
    	else
    		throw new IllegalArgumentException("N = ?, T =?");    
    	
    }
}
    	
  
  
    
