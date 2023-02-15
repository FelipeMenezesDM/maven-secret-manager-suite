package br.com.felipemenezesdm.suite;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AWSSuite extends DefaultSuite {
    public String getSecretData(String secretName) {
        String region = Optional.ofNullable(Optional.ofNullable(System.getenv("AWS_DEFAULT_REGION")).orElse(this.region)).orElse("us-east-1");
        String endPoint = Optional.ofNullable(Optional.ofNullable(System.getenv("AWS_ENDPOINT")).orElse(this.endPoint)).orElse(String.format("//secretsmanager.%s.amazonaws.com", this.region));
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region)).build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);

        return client.getSecretValue(getSecretValueRequest).getSecretString();
    }
}
