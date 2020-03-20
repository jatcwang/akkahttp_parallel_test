# Akka-http parallel test

Test is a test project showing the behaviour of `akka.http.host-connection-pool.max-open-requests` setting

This project consists of two modules

- A mock server to simulate the server (1 sec delay to all responses)
- Akka-http client that sends lots of parallel requests

By running the server first (`runMain example.ServerMain`) and then the client in 
another SBT instance `runMain example.ClientMain`

You will notice:

- All future starts running immediately, but only 4 requests are being sent at a time 
(controlled by `akka.http.host-connection-pool.max-connections` config). This is the limit per host
- If we set `num-parallel-requests` to higher than `max-open-requests` then 
  we'll see BufferOverflowException being thrown
  
## Conclusion

`max-open-requests` is like a queue size. Any requests, whether being "materialized" (actually sent to the server) 
or not gets counted. Thus we can adjust this number to fit what we think the queue size needs to be
during a burst of requests, and any application code can just send as many requests as it needs to without doing its own
rate limiting (maybe not big list views that performs additional enrichments for each item)
