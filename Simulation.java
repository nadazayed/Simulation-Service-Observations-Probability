package simulation;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

/**** @author nadazayed */
public class Simulation 
{
    public static void main(String[] args) 
    {
        Scanner scan=new Scanner(System.in);
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("0.00");
        double x;
        
        System.out.println("Enter # of jobs");
        int n=scan.nextInt();
            
        ////////////////---------SERVICE--------//////////////
        
        System.out.println("Enter observations' range of SERVICE");
        int min=scan.nextInt();
        int max=scan.nextInt();
            
        System.out.println("Enter observations' number of SERVICE");
        int obs=scan.nextInt();
            
        int [] observations= new int [obs];
        double [] prob= new double [obs];
        double [] cdf=new double [obs];
        
        System.out.println("Enter SERVICE observations and probability");
        for (int i=0;i<obs;i++)
        {
            observations[i]=scan.nextInt();
            prob[i]=scan.nextDouble();
        }
            
        cdf[0]=prob[0];
        for (int i=1;i<obs;i++)
        {
            cdf[i]=Double.parseDouble(df.format(prob[i]+cdf[i-1]));
        }
        
        
        double service_col [] = new double [n];
        
        for (int i=0;i<service_col.length;i++)
        {
//            service_col[i]=Double.parseDouble(df.format((rand.nextInt(max)+1)/max));
            x=rand.nextInt(max)+1;
            x=x/max;
            service_col[i]=Double.parseDouble(df.format(x));
        }
        
        for (int i=0;i<service_col.length;i++)
        {
            for (int j=0;j<cdf.length;j++)
            {
                if (service_col[i]==cdf[0] || service_col[i]<cdf[0])
                {
                    service_col[i]=observations[0];
                    break;
                }
                
                else if (service_col[i]>cdf[j]&&service_col[i]<cdf[j+1])
                {
                    service_col[i]=observations[j+1];
                    break;
                }
                 
            }
        }
        
        
        System.out.println("Service"+"\t \t"+"Probability"+"\t \t"+"CDF");
        for (int i=0;i<obs;i++)
        {
            System.out.println(observations[i]+"\t \t"+prob[i]+"\t \t \t"+cdf[i]);
        }
        
        
        System.out.println("You're done with service");
        System.out.println("\n \n");
////////////////////---------INTERARRIVAL--------/////////////////////////
        
        System.out.println("Enter observations' range of INTERARRIVAL");
        min=scan.nextInt();
        max=scan.nextInt();
        
        System.out.println("Enter observations' number of INTERARRIVAL");
        obs=scan.nextInt();
        
        double observations2 [] = new double [obs];
        double prob2 [] = new double [obs];
        double cdf2 [] = new double [obs];
        
        System.out.println("Enter INTERARRIVAL observations and probability");
        for (int i=0;i<obs;i++)
        {
            observations2[i]=scan.nextInt();
            prob2[i]=scan.nextDouble();
        }
        
        cdf2[0]=prob2[0];
        for (int i=1;i<obs;i++)
        {
            cdf2[i]=Double.parseDouble(df.format(prob2[i]+cdf2[i-1]));
        }
        
        double interArrival_col [] = new double [n];
        for (int i=0;i<interArrival_col.length;i++)
        {
            x=rand.nextInt(max)+1;
            x=x/max;
            interArrival_col[i]=Double.parseDouble(df.format(x));
        }
        
        for (int i=0;i<interArrival_col.length;i++)
        {
            for (int j=0;j<cdf2.length;j++)
            {
                if (interArrival_col[i]==cdf2[0] || interArrival_col[i]<cdf2[0])
                {
                    interArrival_col[i]=observations2[0];
                    break;
                }
                
                else if (interArrival_col[i]>cdf2[j]&&interArrival_col[i]<cdf2[j+1])
                {
                    interArrival_col[i]=observations2[j+1];
                    break;
                }
                 
            }
        }
        
        System.out.println("InterArrival"+"\t"+"Probability"+"\t"+"CDF");
        for (int i=0;i<obs;i++)
        {
            System.out.println(observations2[i]+"\t \t"+prob2[i]+"\t \t"+cdf2[i]);
        }
        
        System.out.println("You're done with interarrival");
        System.out.println("\n \n");
        
////////////////////---------ARRIVAL--------//////////////////////////
        
        double arrival_col [] = new double [n];
        double start_col [] = new double [n];
        double end_col [] = new double [n];
        double wait_col [] = new double [n];
        double sys_col [] = new double [n];
        double idle_col [] = new double [n];
        
        arrival_col[0]=Double.parseDouble(df.format(interArrival_col[0]));
        start_col[0]=Double.parseDouble(df.format(arrival_col[0]));
        end_col[0]=Double.parseDouble(df.format(start_col[0]+service_col[0]));
        wait_col[0]=Double.parseDouble(df.format(start_col[0]-arrival_col[0]));
        sys_col[0]=Double.parseDouble(df.format(wait_col[0]+service_col[0]));
        idle_col[0]=Double.parseDouble(df.format(start_col[0]));
        for (int i=1;i<n;i++)
        {
            arrival_col[i]=Double.parseDouble(df.format(arrival_col[i-1]+interArrival_col[i]));
            
            if (arrival_col[i]>end_col[i-1])
                start_col[i]=Double.parseDouble(df.format(arrival_col[i]));
            else
                start_col[i]=Double.parseDouble(df.format(end_col[i-1]));
            
            end_col[i]=Double.parseDouble(df.format(start_col[i]+service_col[i]));
            wait_col[i]=Double.parseDouble(df.format(start_col[i]-arrival_col[i]));
            sys_col[i]=Double.parseDouble(df.format(wait_col[i]+service_col[i]));
            idle_col[i]=Double.parseDouble(df.format(start_col[i]-end_col[i-1]));
        }

////////////////////---------DESCRIPTIVE--------//////////////////////////
        
            double sum_wait=0, who_wait=0, sum_idle=0, sum_service=0, sum_inter=0, sum_sys=0;
            for (int i=0;i<wait_col.length;i++)
            {
                sum_wait+=wait_col[i];
                if (wait_col[i]>0)
                    who_wait++;
                
                sum_idle+=idle_col[i];
                sum_service+=service_col[i];
                sum_inter+=interArrival_col[i];
                sum_sys+=sys_col[i];
            }
            
            
            
////////////////////---------PRINT--------//////////////////////////
            System.out.println("I"+"\t"+"Inter"+"\t"+"Arrival"+"\t"+"Service"+"\t"+"Start"+"\t"+"End"+"\t"+"Wait"+"\t"+"System"+"\t"+"Idle");
            for (int i=0;i<n;i++)
            {
                System.out.println(i+1+"\t"+interArrival_col[i]+"\t"+arrival_col[i]+"\t"+service_col[i]+"\t"+start_col[i]+"\t"+end_col[i]+"\t"+wait_col[i]+"\t"+sys_col[i]+"\t"+idle_col[i]);
            }
            
            System.out.println("\n \n");
            
            System.out.println("Average Waiting Time="+Double.parseDouble(df.format(sum_wait))+"/"+n+"="+Double.parseDouble(df.format((sum_wait/n))));
            System.out.println("Probability (TIME)="+Double.parseDouble(df.format(who_wait))+"/"+n+"="+Double.parseDouble(df.format((who_wait/n))));
            System.out.println("Probability Idle Server="+Double.parseDouble(df.format(sum_idle))+"/"+end_col[end_col.length-1]+"="+Double.parseDouble(df.format((sum_idle/end_col[end_col.length-1]))));
            System.out.println("Average Service Time="+Double.parseDouble(df.format(sum_service))+"/"+n+"="+Double.parseDouble(df.format((sum_service/n))));
            System.out.println("Average Time Between Arrivals="+Double.parseDouble(df.format(sum_inter))+"/"+(n-1)+"="+Double.parseDouble(df.format((sum_inter/(n-1)))));
            System.out.println("Average Waiting Time of Those Who Wait="+Double.parseDouble(df.format(sum_wait))+"/"+Double.parseDouble(df.format(who_wait))+"="+Double.parseDouble(df.format((sum_wait/who_wait))));
            System.out.println("Average Time a Customer Spends in the System="+Double.parseDouble(df.format(sum_sys))+"/"+n+"="+Double.parseDouble(df.format((sum_sys/n))));
    }
    
}
