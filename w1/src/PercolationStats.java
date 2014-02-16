

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
    	
    	for ( int i=0; i<T; i++ )
    	{

    	    	  
    	    	  Percolation pl = new Percolation(N);
    	    	  bPercolated = false;
    	    	  openNum=0; // the number of opened grids  	    	 
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
	        					openNum ++;
	        					bPercolated = pl.percolates();
	        					bOpened = true;    
	        				}
	        			}
	        		}
	        		probability[i] = ((double)openNum)/((double)N)/((double)N);  		

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
    		pclStat.tryPercolate();
    		System.out.println();
    		System.out.println("mean = \t\t\t\t"+ pclStat.mean());
    		System.out.println("Stddev = \t\t\t"+ pclStat.stddev());
    		System.out.println("95% confidence inverval = \t"+pclStat.confidenceLo()+","+pclStat.confindenHi());  		        	

    	}    		
    	else
    		throw new IllegalArgumentException("N = ?, T =?");    
    	
    }
}
    	
  
  
    
