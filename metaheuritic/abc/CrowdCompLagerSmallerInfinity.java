package jmetal.metaheuritic.abc;

import java.util.Comparator;
import jmetal.core.*;


public class CrowdCompLagerSmallerInfinity implements Comparator<Solution>
{
    @Override
    // x better than y:-1
    // x worse than y: 1
    public int compare(Solution x, Solution y)
    {
    	if(Double.isInfinite(x.getCrowdingDistance())
    			&& Double.isInfinite(y.getCrowdingDistance()))
    	{
    		return 0;
    	}
    	if(Double.isInfinite(x.getCrowdingDistance())){
    		return 1;
    	}
    	else if(Double.isInfinite(y.getCrowdingDistance())){
    		return -1;
    	}
        if (x.getCrowdingDistance() < y.getCrowdingDistance()){
            return 1;
        }
        else if (x.getCrowdingDistance() > y.getCrowdingDistance())
        {
            return -1;
        }
        return 0;
    }
    
}