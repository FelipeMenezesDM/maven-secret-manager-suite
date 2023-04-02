package br.com.felipemenezesdm.suite;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class GCPSuite extends DefaultSuite {
    public String getSecretData(String secretName) {
        if(secretName.isEmpty()) {
            return secretName;
        }

        String cached = getCache(secretName);

        if(!Objects.isNull(cached)) {
            return cached;
        }

        try {
            SecretManagerServiceClient client = SecretManagerServiceClient.create();
            String projectId = Optional.ofNullable(System.getenv("GCP_PROJECT_ID")).orElse(Optional.ofNullable(System.getProperty("app.gcp.project-id")).orElse(this.projectId));
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretName, "latest");
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);

            return putCache(secretName, response.getPayload().getData().toStringUtf8());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
