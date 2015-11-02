[![Build Status](https://travis-ci.org/mintDS/mintDS.svg)](https://travis-ci.org/mintDS/mintDS)

What is mintDS?
--------------

mintDS is a *probabilistic data structures* server.

What are the mintDS data structures?
--------------
➤ **Bloom Filter** is a space-efficient probabilistic data structure which is used to test whether an element is a member of a set. Membership query returns either "possibly in set" or "definitely not in set". The probability of false positives can be easily configured.

➤ **HyperLogLog** is a space-efficient probabilistic data structure which is used for approximating the number of distinct elements in a multiset. The accuracy can be easily configured.

Running mintDS
--------------
Todo

Playing with mintDS
--------------
Todo  mintds-cli

References
--------------
The list of related open-source projects and scientific papers which mintDS makes use of:
 - **Bloom Filters** by Burton H. Bloom - [Space/Time Trade-offs in Hash Coding with Allowable Errors] (https://www.cs.upc.edu/~diaz/p422-bloom.pdf)
 - **Counting Bloom Filters** by Flavio Bonomi, et al - [An Improved Construction for Counting Bloom Filters] (https://www.eecs.berkeley.edu/~sylvia/cs268-2013/papers/countingbloom.pdf)
 - **HyperLogLog** by Philippe Flajolet, et al - [HyperLogLog: the analysis of a near-optimal
cardinality estimation algorithm] (http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf)
 - **Count-Min Sketch** by by Graham Cormode, et al - [Count-Min Sketch] (http://dimacs.rutgers.edu/~graham/pubs/papers/cmencyc.pdf)
