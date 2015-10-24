Protocol
--------

By default, mintDS will listen for TCP connections on port 7657. It uses a simple ASCII protocol that is very similar to memcached.

| Commands   |      Example                  |
|------------|-------------------------------|
| create     | create bloomfilter myfilter   |
| drop       | drop myfilter                 |
