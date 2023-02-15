package br.com.felipemenezesdm;

import br.com.felipemenezesdm.enums.DefaultAppSuitesEnum;
import br.com.felipemenezesdm.suite.AWSSuite;
import br.com.felipemenezesdm.suite.DefaultSuite;
import br.com.felipemenezesdm.suite.GCPSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class Suite {
    @Autowired
    AWSSuite awsSuite;

    @Autowired
    GCPSuite gcpSuite;

    @Autowired
    DefaultSuite defaultSuite;

    @Value("${app.suite:#{null}}")
    String appSuite;

    public DefaultSuite get() {
        String provider = Optional.ofNullable(System.getenv("APP_SUITE")).orElse(appSuite).toUpperCase();

        switch(DefaultAppSuitesEnum.valueOf(provider)) {
            case GCP :
                return gcpSuite;
            case AWS :
                return awsSuite;
            default:
                return defaultSuite;
        }
    }
}
