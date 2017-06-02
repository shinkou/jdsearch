# jdsearch

A tool to search for keys in Redis database

## Compile
```
$ mvn clean package
```

## Usage
### KEYS
```
$ java -Dhost=some.redis.server -Dport=6379 -Dmethod=keys -jar ./target/jdsearch-0.1.0.jar 'mykeys:*'
```

### SCAN
```
$ java -Dhost=some.redis.server -Dport=6379 -Dmethod=scan -Dscan.count=1024 -jar ./target/jdsearch-0.1.0.jar 'mykeys:*'
```
