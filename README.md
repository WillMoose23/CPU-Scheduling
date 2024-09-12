# CPU-Scheduling
A project for my Operating Systems course in College that demonstrates CPU scheduling algorithms

Description: This project demonstrates four different types of CPU scheduling utilized by the Operating System: Round Robin, Shortest Job First, Priority without Preemption, and Priority with Preemption.

Round Robin (RR) - Round Robin CPU scheduling assigns each process a fixed time slice, or quantum, during which it can execute. 
Once the time slice expires, the process is moved to the back of the queue, allowing the next process to run, ensuring all processes get fair CPU time. 
This method is especially useful in time-sharing systems, promoting responsiveness but may lead to higher context-switching overhead.

Shortest Job First (SJF) - Shortest Job First is a CPU scheduling algorithm that selects the process with the shortest execution time to run next. 
It minimizes the average waiting time by prioritizing shorter jobs, but it requires knowledge of a process's execution time in advance, making it difficult to implement for unpredictable tasks.
SJF can be preemptive (Shortest Remaining Time First) or non-preemptive, depending on whether it allows switching to a shorter job during execution.

Priority without Preemption - In priority without preemption CPU scheduling, each process is assigned a priority, and the CPU is allocated to the highest-priority process that is ready to run. 
Once a process starts executing, it continues until it finishes or blocks, regardless of whether a higher-priority process arrives during its execution. 
This method can lead to starvation, where lower-priority processes may be delayed indefinitely if higher-priority processes keep arriving.

Priority with preemption - Priority with preemption CPU scheduling allows a higher-priority process to interrupt and take over the CPU from a lower-priority process if it arrives while the lower-priority process is running. 
When a new process with a higher priority becomes ready, the currently running process is preempted and placed back in the ready queue. 
This method improves responsiveness for high-priority tasks but can lead to starvation for lower-priority processes if not managed properly.

