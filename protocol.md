Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is similar to redis and memcached.

#### BloomFilter

| Commands   | Request                       | Response                                 |
|------------|-------------------------------|------------------------------------------|
| create     | create bloomfilter myfilter   | success \| exists \| failure {msg}       |
| exists     | exists myfilter               | yes \| no \| failure {msg}               |
| add        | add myfilter myvalue          | success \| failure {msg}                 |
| contains   | contains myfilter myvalue     | yes \| no \| failure {msg}               |
| drop       | drop myfilter                 | success \| non-existent \| failure {msg} |

#### HyperLogLog

| Commands   | Request                       | Response                                 |
|------------|-------------------------------|------------------------------------------|
| create     | create hyperloglog mylog      | success \| exists \| failure {msg}       |
| exists     | exists mylog                  | yes \| no \| failure {msg}               |
| add        | add mylog myvalue             | success \| failure {msg}                 |
| count      | count mylog                   | {number} \| failure {msg}                |
| drop       | drop mylog                    | success \| non-existent \| failure {msg} |

#### LinearCounter

| Commands   | Request                         | Response                                 |
|------------|---------------------------------|------------------------------------------|
| create     | create linearcounter mycounter  | success \| exists \| failure {msg}       |
| exists     | exists mycounter                | yes \| no \| failure {msg}               |
| add        | add mycounter myvalue           | success \| failure {msg}                 |
| count      | count mycounter                 | {number} \| failure {msg}                |
| drop       | drop mycounter                  | success \| non-existent \| failure {msg} |

