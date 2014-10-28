package jmetal.metaheuritic.abc;
import java.util.Comparator;

import jmetal.core.Solution;

public class Low_HighFitnessComparator implements Comparator<Solution>
{
    @Override
    public int compare(Solution x, Solution y)
    {
        if (x.getFitness() > y.getFitness()){
            return 1;
        }
        else if (x.getFitness() < y.getFitness())
        {
            return -1;
        }
        return 0;
    }
    
}