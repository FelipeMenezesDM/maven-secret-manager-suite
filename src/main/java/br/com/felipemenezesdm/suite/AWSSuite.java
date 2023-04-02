package br.com.felipemenezesdm.suite;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
public class AWSSuite extends DefaultSuite {
    public String getSecretData(String secretName) {
        if(secretName.isEmpty()) {
            return secretName;
        }

        String cached = getCache(secretName);

        if(!Objects.isNull(cached)) {
            return cached;
        }

        String region = Optional.ofNullable(Optional.ofNullable(System.getenv("AWS_DEFAULT_REGION")).orElse(Optional.ofNullable(System.getProperty("app.aws.region")).orElse(this.region))).orElse("us-east-1");
        String endPoint = Optional.ofNullable(Optional.ofNullable(System.getenv("AWS_ENDPOINT")).orElse(Optional.ofNullable(System.getProperty("app.aws.end-point")).orElse(this.endPoint))).orElse(String.format("//secretsmanager.%s.amazonaws.com", this.region));
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region)).build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);

        return putCache(secretName, client.getSecretValue(getSecretValueRequest).getSecretString());
    }
}
