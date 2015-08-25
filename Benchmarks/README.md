Benchmarks
=======
 
During my placement, I have made a simple benchmarks for "simple stream" (one event as input) for JavaScript. For the other streams, I do not really take the time to do them. (I have prefered to add some features to the language.) This benchmark was executed on a virtual machine managed by OpenStack with the following specifications: 
* OS: Ubuntu 12.04 LTS
* RAM: 2GO
* CPU: 2 vCPU, AMD A10-7700K Black Edition, 3,5GHz
* NodeJS: v0.12.6
* ReactiveX: v2.4.3
 

* `launchScript.sh` and `resultTxtToCSV.sh` are two crappy scripts that are used for the benchmark.
* `Scala-Avg-Bench` : another crappy script in Scala to compute the average of the results
* `SourceCode`
    * `SourceCode` : source code in ThingML that i Used for the benchmarks
    * `Generated` : JS code that are executed
* `Result` : some CSV file with the cleaned results of the top command

