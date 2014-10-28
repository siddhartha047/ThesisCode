package jmetal.metaheuritic.abc;

import java.util.Comparator;
import jmetal.core.*;


public class InvertFitnessComparator implements Comparator<Solution>
{
    @Override
    // x better than y:-1
    // x worse than y: 1
    public int compare(Solution x, Solution y)
    {
        if (x.getFitness() < y.getFitness()){
            return -1;
        }
        else if (x.getFitness() > y.getFitness())
        {
            return 1;
        }
        return 0;
    }
    
}