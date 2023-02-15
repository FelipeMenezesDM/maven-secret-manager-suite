package br.com.felipemenezesdm.suite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultSuite {
    @Value("${app.aws.region:#{null}}")
    String region;

    @Value("${app.aws.end-point:#{null}}")
    String endPoint;

    @Value("${app.gcp.project-id:#{null}}")
    String projectId;

    public String getSecretData(String secretName) {
        return secretName;
    }
}
