# Install ab

(if you don't have it, on Ubuntu: `sudo apt install apache2-utils`).

# Test 1: Platform Threads (Major Bottleneck)

We are requesting 500 concurrent requests (-c 500) with a total of 1000 requests (-n 1000). Given that the work blocks for 100 ms and we request 500 threads, the pool of 200 will be quickly exhausted.

```bash
echo "--- Testing Platform Threads (/platform-thread) ---"
ab -n 1000 -c 500 http://localhost:8080/platform-thread
```

---

# Test 2: Virtual Threads (High Scalability)

We are requesting the same load. The latency should remain low because the JVM manages thousands of Virtual Threads on the few Platform carrier threads.

```bash
echo "--- Testing Virtual Threads (/virtual-thread) ---"
ab -n 1000 -c 500 http://localhost:8080/virtual-thread
```

---

# Expected Observation (Key Takeaway)

* Platform Threads (/platform-thread): Test 1 will show a high average request time (several seconds). Requests 201 to 500 will have to wait for one of the 200 threads to be released, causing massive service degradation.
* Virtual Threads (/virtual-thread): Test 2 will show a low average request time, close to 100 ms (plus network overhead). Virtual Threads handle the load without exhausting the pool (no starvation) because the threads are unmounted from their carrier threads during the blocking sleep.

This very concretely illustrates why Virtual Threads are revolutionary for I/O-bound applications, allowing them to maintain high responsiveness without writing complex asynchronous code.