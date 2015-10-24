Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is similar to redis and memcached.

##### BloomFilter

| Commands   | Request                       | Response                                 |
|------------|-------------------------------|------------------------------------------|
| create     | create bloomfilter myfilter   | success \| exists \| failure {msg}       |
| exists     | exists myfilter               | yes \| no \| failure {msg}               |
| add        | add myfilter myvalue          | success \| failure {msg}                 |
| contains   | contains myfilter myvalue     | yes \| no \| failure {msg}               |
| drop       | drop myfilter                 | success \| non-existent \| failure {msg} |

