package TestStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class application implements CommandLineRunner {

    final static private Logger logger = LoggerFactory.getLogger(application.class);

    @Autowired
    Ignite ignite;

    public static void main(String[] args) {
        SpringApplication.run(application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CacheConfiguration<Integer, String> cfgCache = new CacheConfiguration<Integer, String>("Cache1");

        cfgCache.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE);
        cfgCache.setCacheMode(CacheMode.PARTITIONED);

        IgniteCache<Integer, String> igniteCache = ignite.getOrCreateCache(cfgCache);

        CacheConfiguration<Integer, String> cfgCache1 = new CacheConfiguration<Integer, String>("Cache2");

        cfgCache.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE);
        cfgCache.setCacheMode(CacheMode.PARTITIONED);

        IgniteCache<Integer, String> igniteCache1 = ignite.getOrCreateCache(cfgCache1);

//        IgniteCache<Integer, String> igniteCache = ignite.getOrCreateCache("Cache1");
//        IgniteCache<Integer, String> igniteCache1 = ignite.getOrCreateCache("Cache2");


        for (int i = 0; i < 20; ++i) {
            igniteCache.putIfAbsent(i, " ----- " + i + " -----");
            igniteCache1.putIfAbsent(i, " ----- " + i + " -----");
        }

    }
}


