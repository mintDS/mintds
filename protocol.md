Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is very similar to memcached.

| Commands   | Request                       | Response                           |
|------------|-------------------------------|------------------------------------|
| create     | create bloomfilter myfilter   | success \| failure {msg} \| exists |
| drop       | drop myfilter                 | success \| failure {msg}           |
