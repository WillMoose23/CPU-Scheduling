import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Starter
{
    public static void main(String[] args)
    {
        String arr[] = new String[16];
       /*
       for(int i = 0; i<arr.length; i++)
        {
            int index = i;
            System.out.println("\nTEST CASE " + (index+1) + ": ");
            arr[i] = "test cases\\input" + (index+1) + ".txt";
            readData(arr[i]);
        }
        */

        readData("C:\\Users\\willm\\Desktop\\Tools\\Programs\\Java\\scheduling\\test cases\\input2.txt");
    }

    public static void readData(String fileName)
    {
        Queue<Process> queue = new LinkedList<>();

        int TQ = 0;
        int totalPNum = 0;
        int pNum = 0;
        int arrTime = 0;
        int cpuTime = 0;
        int priority = 0;

        boolean RR = false;
        boolean SJF = false;
        boolean PR_NONE = false;
        boolean PR_PREMP = false;

        String algorithm = "";


        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            Scanner in = new Scanner(br);

            algorithm = in.next();
            switch (algorithm)
            {
                case "RR":
                {
                    RR = true;
                    TQ = Integer.parseInt(in.next());
                    in.nextLine();
                    break;
                }
                case "SJF":
                {
                    SJF = true;
                    break;
                }
                case "PR_noPREMP":
                {
                    PR_NONE = true;
                    break;
                }
                case "PR_withPREMP":
                {
                    PR_PREMP = true;
                    break;
                }
                default:
                {
                    System.out.println("An error has occurred. Please check your file formatting.");
                    System.exit(0);
                }
            }

            totalPNum = Integer.parseInt(in.next());
            //     System.out.println(TQ + " " + totalPNum + " ");
            in.nextLine();

            for(int i = 0; i< totalPNum; i++)
            {
                pNum = Integer.parseInt(in.next());
                arrTime = Integer.parseInt(in.next());
                cpuTime = Integer.parseInt(in.next());
                priority = Integer.parseInt(in.next());

                Process p = new Process(pNum, arrTime, cpuTime, priority);
                queue.add(p);
                //System.out.println(p);

                if(in.hasNextLine())
                    in.nextLine();
                else
                    break;
            }

            switch(algorithm)
            {
                case "RR":
                    roundRobin(TQ, totalPNum, queue, fileName);
                    break;
                case "SJF":
                    sjf(TQ, totalPNum, queue, fileName);
                    break;
                case "PR_noPREMP":
                    //PR_noPREMP method not implemented yet
                    break;
                case "PR_withPREMP":
                    //PR_withPREMP method not implemented yet
                    break;
                default:
                    System.out.println("An error has occurred. Please check your file formatting.");
                    System.exit(0);
            }

            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int random(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void roundRobin(int TQ, int totalPNum, Queue<Process> queue, String p)
    {
        int TIME_SUM = 0;
        int cycle = 0;
        int w_size = 0;
        int counter = 0;
        double total = 0;

        int pro[] = new int [totalPNum]; //calcs completion time per process

        String output = "";
        String path = p;
        String formattedTotal = "";

        //  Queue<Process> tempQueue = new LinkedList<>();
        //  tempQueue.addAll(queue);

        Process tempProcess;
        ArrayList<Process> arr = new ArrayList<>();

        //for(Process e: queue)
        //  System.out.println(e);

        for(int i = 0; i < totalPNum; i++)
        {
            tempProcess = queue.poll();
            arr.add(tempProcess);
            TIME_SUM = TIME_SUM + tempProcess.getCpuBurst();
            // queue.add(tempProcess);
        }

        //for(int i = 0; i<arr.size(); i++)
        // System.out.println(arr.get(i));

        while(w_size < arr.size())
        {
            if(arr.get(w_size).getArrivalTime() == 0)
            {
                //System.out.println("inner while " + queue.peek());
                queue.add(arr.get(w_size));
                arr.remove(w_size);
            }
            else
            {
                w_size++;
            }
        }

        //System.out.print("Size of arraylist: " + arr.size() + "\n");

        /*  for(Process e: queue)
        {
            System.out.println(e);
        }

        System.out.println("Time Sum: " + TIME_SUM);*/

        /*
        int x = 0;
        while(!queue.isEmpty())
        {
            tempProcess = queue.poll();
            if(tempProcess.getCpuBurst() < TQ)
            {
                //break;
                x =0 ;
                while(x < tempProcess.getCpuBurst())
                {

                    //System.out.println(tempProcess);
                    x++;
                    //System.out.println("Inner Cycle: " + cycle);
                    //System.out.println("Process Number: " + tempProcess.getProcessNum() + " at " + cycle);
                    //System.out.println("CPU Burst: " + tempProcess.getCpuBurst());
                    tempProcess.execute();
                    cycle++;
                    //System.out.println("Cycle: " + cycle);
                }
                // x = 0;
            }
            else
            {
                //System.out.println("Process Number: " + tempProcess.getProcessNum() + " at " + cycle);
                //int check = 0;
                while (x < TQ)
                {
                    x++;
                    tempProcess.execute();
                    cycle++;
                    if(tempProcess.getCpuBurst() == 0)
                    {
                        break;
                    }
                    int check = 0;
                    while(check<arr.size())
                    {
                        if(arr.get(check).getArrivalTime() == cycle)
                        {
                            queue.add(arr.get(check));
                            arr.remove(check);
                        }
                        else
                        {
                            check++;
                        }
                    }
                    for(Process e: queue)
                        System.out.println(e.getProcessNum() + " at " + cycle);
                    // cycle++;
                    // System.out.println("Cycle: " + cycle);
                }
                //x = 0;
                if(tempProcess.getCpuBurst() > 0)
                    queue.add(tempProcess);
                //System.out.println(tempProcess);
            }
            x=0;
        }
        */

        cycle = TQ;
        //System.out.println("Time quantum: " + TQ);

        while(!queue.isEmpty())
        {
            //System.out.println("Cycle: " + cycle + "\ni: " + i);

            tempProcess = queue.poll();
            output += counter + "\t" + tempProcess.getProcessNum() + "\n";

            //System.out.println(counter + " " + tempProcess.getProcessNum());

            if (tempProcess.getCpuBurst() < TQ)
            {
                //System.out.println("Temp burst: " + tempProcess.getCpuBurst());
                int t = tempProcess.getCpuBurst();
                for(int i = 0; i < t; i++)
                {
                    tempProcess.execute();
                    counter++;
                    System.out.println("For loop: " + i);
                    System.out.println("Temp burst: " + tempProcess.getCpuBurst());
                    for(int x = 0; x < arr.size(); x++)
                    {
                        if(arr.get(x).getArrivalTime() == counter)
                        {
                            queue.add(arr.get(x));
                            // arr.remove(x);
                        }
                    }
                }
                if(tempProcess.getCpuBurst() <= 0)
                {
                    //System.out.println("first cpu burst: " + tempProcess.getOG_CPU_burst());
                    pro[tempProcess.getProcessNum() - 1] = (counter - tempProcess.getOG_CPU_burst() - tempProcess.getArrivalTime());
                }
                System.out.println("Finished at time: " + counter);
            }
            else
            {
                //System.out.println("Counter: " + counter);
                cycle = 0;
                while(cycle < TQ)
                {
                    tempProcess.execute();
                    counter++;
                    cycle++;

                    // System.out.println("While cycle: " + cycle);
                    for(int x = 0; x < arr.size(); x++)
                    {
                        if(arr.get(x).getArrivalTime() == counter)
                        {
                            queue.add(arr.get(x));
                        }
                    }
                }
                if(tempProcess.getCpuBurst() > 0)
                {
                    queue.add(tempProcess);
                }
                else
                {
                    //System.out.println("cpu burst: " + tempProcess.getOG_CPU_burst());
                    pro[tempProcess.getProcessNum() - 1] = (counter - tempProcess.getOG_CPU_burst() - tempProcess.getArrivalTime());
                }
            }
            cycle = 0;
        }
        //System.out.println(output);

        // System.out.println("Totally: " + total);
        for(int i = 0; i < pro.length; i++)
        {
            total = pro[i] + total;
        }

        total = total/totalPNum;

        formattedTotal = String.format("%.2f",total);

        System.out.println("RR " + TQ + "\n" + output + "\nWaiting Time: " + formattedTotal);

    }

    public static void sjf(int TQ, int totalPNum, Queue<Process> queue, String p)
    {
        int w_size = 0;
        int TIME_SUM = 0;
        int counter = 0;
        int cycle = 0;

        String output = "";

        ArrayList<Process> arr = new ArrayList<>();

        PriorityQueue<Process> priorityQueue = new PriorityQueue<>();

        Process tempProcess = new Process(0,0,0,0);

        /*for(Process e: queue)
        {
            System.out.println(e);
        }*/

        for(int i = 0; i < totalPNum; i++)
        {
            tempProcess = queue.poll();
            // System.out.println(tempProcess);
            arr.add(tempProcess);
            //System.out.println("i: " + arr.get(i));
            TIME_SUM = TIME_SUM + tempProcess.getCpuBurst();
        }

        while(w_size < arr.size())
        {
            if(arr.get(w_size).getArrivalTime() == 0)
            {
                //System.out.println("inner while " + queue.peek());
                priorityQueue.add(arr.get(w_size));
                arr.remove(w_size);
            }
            else
            {
                w_size++;
            }
        }

      //  System.out.println("arr size: " + arr.size());

        for(int i = 0; i<arr.size(); i++)
        {
       //     System.out.println("i: " + arr.get(i));
        }

        for(Process e: priorityQueue)
        {
            //System.out.println("i" + e);
        }

        while(!priorityQueue.isEmpty())
        {
            tempProcess = priorityQueue.poll();
           // System.out.println("Test" + tempProcess);
            System.out.println("TempProcess Num: " + tempProcess.getProcessNum());
            output += counter + "\t" + tempProcess.getProcessNum() + "\n";
            //System.out.println(output);
            int temp = tempProcess.getCpuBurst();
           // System.out.println("Temp: " + temp);
            while(cycle < temp)
            {
                //System.out.println("Temp: " + temp);
                tempProcess.execute();
                counter++;
                cycle++;

                for(int x = 0; x < arr.size(); x++)
                {
                    if(arr.get(x).getArrivalTime() == counter)
                        priorityQueue.add(arr.get(x));
                }
            }
        }

        System.out.println(output);
    }
}

class Process implements Comparable<Process>
{
    private int processNum;
    private int arrivalTime;
    private int cpuBurst;
    private int priority;
    final private int OG_CPU_burst;

    public Process(int num, int time, int burst, int prio)
    {
        processNum = num;
        arrivalTime = time;
        cpuBurst = burst;
        priority = prio;
        OG_CPU_burst = burst;
    }

    public int getProcessNum()
    {
        return processNum;
    }

    public void setProcessNum(int x)
    {
        processNum = x;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(int x)
    {
        arrivalTime = x;
    }

    public int getCpuBurst()
    {
        return cpuBurst;
    }

    public void setCpuBurst(int x)
    {

        cpuBurst = Math.abs(x);
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int x)
    {
        priority = x;
    }

    public int getOG_CPU_burst()
    {
        return this.OG_CPU_burst;
    }

    public void clean()
    {
        /*
        WARNING:
        Puts the process in an unusable state.
        Only use for temp variables!
        */

        setArrivalTime(0);
        setPriority(0);
        setProcessNum(0);
        setCpuBurst(0);
    }

    public void execute()
    {
        this.setCpuBurst(this.getCpuBurst() - 1);
    }

    public void subArrivalTime()
    {
        if(this.getArrivalTime() > 0)
        {
            this.setArrivalTime(this.getArrivalTime() - 1);
        }
    }

    @Override
    public int compareTo(Process o)
    {
        if(this.getCpuBurst() == o.getCpuBurst())
        {
            if(this.getProcessNum() < o.getProcessNum())
            {
                System.out.println("Less");
                return this.getCpuBurst();
            }
            else if(this.getProcessNum() > o.getProcessNum())
            {
                System.out.println("More");
                return o.getCpuBurst();
            }
            else
            {
                System.out.println("You shouldn't be here");
                return Integer.compare(this.getCpuBurst(), o.getCpuBurst());
            }
        }
        else
        {
            System.out.println("Min");
            return Math.min(this.getOG_CPU_burst(), o.getOG_CPU_burst());
        }
    }

    public String toString()
    {
        return "Process Num: " + getProcessNum()
                + "\nArrival Time: " + getArrivalTime()
                + "\nCPU Burst Time: " + getCpuBurst()
                + "\nPriority Level: " + getPriority() + "\n\n";
    }


}