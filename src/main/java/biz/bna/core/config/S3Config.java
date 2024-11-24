package biz.bna.core.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
public class S3Config {
    @Value("${yandex.s3.private-key}")
    String privateKey;

    @Value("${yandex.s3.id-key}")
    String idKey;

    @Value("${yandex.s3.region}")
    public String regionName;

    @Value("${yandex.s3.enpoint-url}")
    public String endpoint;

    private AwsCredentials credentials;

    @PostConstruct
    private void init(){
        credentials = AwsBasicCredentials.create(idKey, privateKey);
    }

    public AwsCredentials getCredentials(){
        return this.credentials;
    }
}
