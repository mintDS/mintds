Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is similar to redis and memcached.

| Commands   | Request                       | Response                                 |
|------------|-------------------------------|------------------------------------------|
| create     | create bloomfilter myfilter   | success \| exists \| failure {msg}       |
| drop       | drop myfilter                 | success \| non-existent \| failure {msg} |
