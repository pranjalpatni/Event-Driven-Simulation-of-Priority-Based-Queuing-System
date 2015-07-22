# Event-Driven-Simulation-of-Priority-Based-Queuing-System 

This project implements the simulation for a queueing network with two classes of customers. There are two queues in the network. The queue capacities are assumed to be inﬁnite, and each queue has a single server. High-priority customers in each queue are always served before low-priority customers in each queue; however, if a low-priority customer is already in service, it will not be pre-empted by a high-priority customer.

Customers arrive to the overall system according to a Poisson process with rate λ customers per second. With probability pH , the customer will be a high-priority customer, and with probability pL = 1 − pH , the customer will be a low-priority customer. Low-priority customers will always arrive to Queue 1, and high-priority customers will always arrive to Queue 2.

The service time for a customer at Queue 1 is exponentially distributed with an average service time of 1/µ1 and the service time for a customer at Queue 2 is exponentially distributed with an average service time of 1/µ2. The service times do not depend on the the priority of the customer.

When a high-priority customer departs from Queue 1, it always enters Queue 2. When a low-priority customer departs from Queue 1, it enters Queue 2 with probability r12L and returns to Queue 1 with probability r11L . When a customer of either priority departs from Queue 2, it departs the network with probability r2d , and it goes back to Queue 1 with probability r21.

A discrete event simulation needs to be implemented for the above system. The simulation will run until at least 500,000 customers have departed from the system. Let pH = 1/4 , pL = 3/4 , r11L = 1/5 , r12L = 4/5 , r2d = 1/2 , r21 = 1/2 , µ1 = 25 customers per second, and µ2 = 40 customers per second. The performancce metrices to be calculated are:

1- High-low priority throughput of each queue

2- Expected number of each priority of customers in each queue (including the one in service)

3- Average time each priority customer spends in Queue 1 (including service time) during a single visit to Queue 1

#How to compiple the code:

javac Event.java Exp_rv.java Main.java QueuingSystem.java

#How to run the code:

java Main


#How to change input values:

The input values are passed in the Main function as arguments in the following order:
<PH, PL, r11L, r12L, r2d, r21, mu1, mu2, lambda>
