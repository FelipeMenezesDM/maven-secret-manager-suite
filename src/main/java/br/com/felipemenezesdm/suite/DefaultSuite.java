package br.com.felipemenezesdm.suite;

import br.com.felipemenezesdm.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@EnableCaching
public class DefaultSuite {
    private static final String CACHE_NAME = "SECRETS";

    @Value("${app.aws.region:#{null}}")
    String region;

    @Value("${app.aws.end-point:#{null}}")
    String endPoint;

    @Value("${app.gcp.project-id:#{null}}")
    String projectId;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    HashService hashService;

    public String getSecretData(String secretName) {
        return secretName;
    }

    protected String getCacheKey(String key) {
        return String.format("secret[%s]", hashService.getSHA512(key));
    }

    protected String getCache(String secretName) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        Cache.ValueWrapper cacheValueWrapper = Objects.requireNonNull(cache).get(getCacheKey(secretName));

        if(!Objects.isNull(cacheValueWrapper)) {
            Object cachedValue = cacheValueWrapper.get();
            return !Objects.isNull(cachedValue) ? cachedValue.toString() : null;
        }

        return null;
    }

    protected String putCache(String secretName, String value) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        Objects.requireNonNull(cache).put(getCacheKey(secretName), value);
        return value;
    }
}
