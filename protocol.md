Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is similar to redis and memcached.

#### BloomFilter

| Commands   | Request                               | Response                                 |
|------------|---------------------------------------|------------------------------------------|
| create     | create bloomfilter myfilter           | success \| exists \| failure {msg}       |
| exists     | exists bloomfilter myfilter           | yes \| no \| failure {msg}               |
| add        | add bloomfilter myfilter myvalue      | success \| failure {msg}                 |
| contains   | contains bloomfilter myfilter myvalue | yes \| no \| failure {msg}               |
| drop       | drop bloomfilter myfilter             | success \| non-existent \| failure {msg} |

#### HyperLogLog

| Commands   | Request                       | Response                                 |
|------------|-------------------------------|------------------------------------------|
| create     | create hyperloglog mylog      | success \| exists \| failure {msg}       |
| exists     | exists hyperloglog mylog      | yes \| no \| failure {msg}               |
| add        | add hyperloglog mylog myvalue | success \| failure {msg}                 |
| count      | count hyperloglog mylog       | {number} \| failure {msg}                |
| drop       | drop hyperloglog mylog        | success \| non-existent \| failure {msg} |

#### LinearCounter

| Commands   | Request                             | Response                                 |
|------------|-------------------------------------|------------------------------------------|
| create     | create linearcounter mycounter      | success \| exists \| failure {msg}       |
| exists     | exists linearcounter mycounter      | yes \| no \| failure {msg}               |
| add        | add linearcounter mycounter myvalue | success \| failure {msg}                 |
| count      | count linearcounter mycounter       | {number} \| failure {msg}                |
| drop       | drop linearcounter mycounter        | success \| non-existent \| failure {msg} |

#### CountMinSketch

| Commands   | Request                               | Response                                 |
|------------|---------------------------------------|------------------------------------------|
| create     | create countminsketch mysketch        | success \| exists \| failure {msg}       |
| exists     | exists countminsketch mysketch        | yes \| no \| failure {msg}               |
| add        | add countminsketch mysketch myvalue   | success \| failure {msg}                 |
| count      | count countminsketch mysketch myvalue | {number} \| failure {msg}                |
| drop       | drop countminsketch mysketch          | success \| non-existent \| failure {msg} |

