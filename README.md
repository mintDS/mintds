[![Build Status](https://travis-ci.org/mintDS/mintds.svg)](https://travis-ci.org/mintDS/mintds)

What is mintDS?
--------------

mintDS is a *probabilistic data structures* server.

What are the mintDS data structures?
--------------
➤ **Bloom Filter** is a space-efficient probabilistic data structure which is used to test whether an element is a member of a set. Membership query returns either "possibly in set" or "definitely not in set". The probability of false positives can be easily configured.

➤ **Counting Bloom Filter** Bloom Filter which supports deletions and counting.

➤ **HyperLogLog** is a space-efficient probabilistic data structure which is used to get the approximate number of distinct elements in a multiset. The accuracy can be easily configured.

➤ **Count–min sketch** is a space-efficient probabilistic data structure which is used to get the approximate frequencies of specific elements in a multiset. The accuracy can be easily configured.

Performance
--------------
mintDS is super fast. Here are some numbers based on aws **c4.xlarge** instance:
- **16ms** to asynchronously send **100K** messages.
- **1s** to asynchronously send and receive **70K** messages.

**Want to try it out ?**

Check out mintd-java and run -> `java -Dconnections=100 -Dthreads=4 -Dhost=<minds-server-ip> -cp target/mintds-java-0.1.2-SNAPSHOT-all.jar com.arturmkrtchyan.mintds.benchmark.BloomFilterBenchmark`

Playing with mintDS
--------------
To run mintDS server simply type:
```shell
 ./bin/mintds-start.sh conf/mintds.yaml
```

After starting the server you can use mintds-cli to play with mintDS. 
```shell
./bin/mintds-cli.sh --host localhost --port 7657
```

####Bloom Filter

```shell

mintDS> create bloomfilter myfilter
SUCCESS

mintDS> exists bloomfilter myfilter
YES

mintDS> add bloomfilter myfilter myvalue
SUCCESS

mintDS> contains bloomfilter myfilter myvalue
YES

mintDS> contains bloomfilter myfilter mynewvalue
NO

mintDS> drop bloomfilter myfilter
SUCCESS

mintDS>
```

####Counting Bloom Filter

```shell

mintDS> create countingbloomfilter myfilter
SUCCESS

mintDS> exists countingbloomfilter myfilter
YES

mintDS> add countingbloomfilter myfilter myvalue
SUCCESS

mintDS> contains countingbloomfilter myfilter myvalue
YES

mintDS> contains countingbloomfilter myfilter mynewvalue
NO

mintDS> count countingbloomfilter myfilter myvalue
1

mintDS> add countingbloomfilter myfilter myvalue
SUCCESS

mintDS> count countingbloomfilter myfilter myvalue
2

mintDS> remove countingbloomfilter myfilter myvalue
SUCCESS

mintDS> count countingbloomfilter myfilter myvalue
1

mintDS> drop countingbloomfilter myfilter
SUCCESS

mintDS>
```

####HyperLogLog

```shell
mintDS> create hyperloglog mylog
SUCCESS

mintDS> exists hyperloglog mylog
YES

mintDS> add hyperloglog mylog myvalue
SUCCESS

mintDS> add hyperloglog mylog mynewvalue
SUCCESS

mintDS> count hyperloglog mylog
2

mintDS> drop hyperloglog mylog
SUCCESS

mintDS>
```

####Count-Min Sketch

```shell
mintDS> create countminsketch mysketch
SUCCESS

mintDS> exists countminsketch mysketch
YES

mintDS> add countminsketch mysketch myvalue
SUCCESS

mintDS> count countminsketch mysketch myvalue
1

mintDS> add countminsketch mysketch myvalue
SUCCESS

mintDS> count countminsketch mysketch myvalue
2

mintDS> drop countminsketch mysketch
SUCCESS

mintDS>
```

Client Implementations
--------------
| Client      |      Description      |
|:------------|:--------------------------------------------:|
| [mintds-java](https://github.com/mintDS/mintds-java) | Asynchronous Java client library for mintDS. |

Check out the protocol description [here](https://github.com/mintDS/mintds-protocol). 

Credits
--------------
Datastructures are based on [addthis/stream-lib](https://github.com/addthis/stream-lib).

References
--------------
The list of related open-source projects and scientific papers which mintDS makes use of:
 - **Bloom Filter** by Burton H. Bloom - [Space/Time Trade-offs in Hash Coding with Allowable Errors] (https://www.cs.upc.edu/~diaz/p422-bloom.pdf)
 - **Counting Bloom Filter** by Flavio Bonomi, et al - [An Improved Construction for Counting Bloom Filters] (https://www.eecs.berkeley.edu/~sylvia/cs268-2013/papers/countingbloom.pdf)
 - **HyperLogLog** by Philippe Flajolet, et al - [HyperLogLog: the analysis of a near-optimal
cardinality estimation algorithm] (http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf)
 - **Count-Min Sketch** by by Graham Cormode, et al - [Count-Min Sketch] (http://dimacs.rutgers.edu/~graham/pubs/papers/cmencyc.pdf)
