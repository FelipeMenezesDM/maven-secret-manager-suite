# Spring Secret Manager Suite
Biblioteca para recuperação de secrets da AWS ou GCP.

## Tópicos
- [Instalação com Maven](#instalação-com-maven)
- [Deploy manual](#deploy-manual)
- [Configuração](#configuração)
  - [Variáveis de ambiente](#variáveis-de-ambiente)
  - [Propriedades da aplicação](#propriedades-da-aplicação)

## Instalação com Maven
Crie o arquivo de configuração do maven ou inclua o repositório e o servidor no arquivo já existente:
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <activeProfiles>
    <activeProfile>general</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>general</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>spring-secret-manager-suite</id>
          <url>https://maven.pkg.github.com/felipemenezesdm/spring-secret-manager-suite</url>
        </repository>
      </repositories>
    </profile>
  </profiles>
</settings>
```

Inclua a dependência no arquivo pom:
```xml
<dependency>
  <groupId>br.com.felipemenezesdm</groupId>
  <artifactId>spring-secret-manager-suite</artifactId>
  <version>1.0.0</version>
</dependency>
```

Execute com comando abaixo para download de dependências:
```
mvn install
```

## Deploy manual
O deploy da biblioteca é realizado automaticamente sempre que houver a criação de uma nova tag de versão. Entretanto, se for necessário realizar seu deploy manual, é preciso seguir os passos abaixo:

1. No _settings.xml_, confirmar que o servidor do GitHub está configurado:
    ```xml
      <servers>
        <server>
          <id>github</id>
          <username>${repo.usrnm}</username>
          <password>${repo.pswd}</password>
        </server>
      </servers>
    ```
2. Executar o comando abaixo, substuindo os parâmetros por seus respectivos valores:
    ```
    mvn deploy -s settings.xml -Drepo.usrnm=USERNAME -Drepo.pswd=PASSWORD
    ```

## Uso
Para usar a biblioteca de recuperação de secrets, é necessário injectar o service _@Suite_ na classe. Abaixo, um exemplo de uso:

```java
import br.com.felipemenezesdm.Suite;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    Suite suite;

    @GetMapping("/get-secret")
    public String getSecret() {
        return suite.get().getSecretData("my-secret");
    }
}
```

## Configuração
É possível configurar a biblioteca usando variáveis de ambiente do sistema ou propriedades da aplicação.

### Variáveis de ambiente
| Name                           | Valor padrão       | Example                                                                          |
|--------------------------------|--------------------|----------------------------------------------------------------------------------|
| APP_SUITE                      | gcp, aws ou vazio  | Definição do suite para recuperação de secrets                                   |
| AWS_ACCOUNT_ID                 | 000000000000       | Definir a ID da conta AWS para a aplicação                                       |
| AWS_ENDPOINT                   | http:\/\/127.0.0.1 | Definir o endpoint dos serviços AWS (indicado quando houver o uso do localstack) |
| AWS_DEFAULT_REGION             | us-east-1          | Definir a região padrão para uma aplicação alocada na AWS                        |
| GCP_PROJECT_ID                 | N/A                | ID do projeto no Google Cloud Plataform                                          |
| GOOGLE_APPLICATION_CREDENTIALS | N/A                | Arquivo de credenciais do Google Cloud Platform                                  |

### Propriedades da aplicação
- **app:**
  - **suite:**
    - **tipo:** _string_
    - **descrição:** definição do suite para recuperação de secrets. Atualmente disponíveis: aws, gcp e default
    - **obrigatório:** sim
  - **aws:**
    - **region:**
      - **tipo:** _string_
      - **descrição:** exclusivo para o provedor **aws**, para identificar a região padrão do cliente.
      - **obrigatório:** sim, quando o provedor for **aws**
    - **end-point:**
      - **tipo:** _string_
      - **descrição:** quando o provedor for igual a **aws**, este parâmetro pode ser configurado para definir o endpoint de onde serão obtidas as credenciais. É bastante útil para quando se está utilizando o LocalStack, por exemplo.
      - **obrigatório:** não
  - **gcp:**
    - **project-id**
      - **tipo:** _string_
      - **descrição:** exclusivo para o provedor **gcp**, para identificar o projeto do qual as credencials serão obtidas.
      - **obrigatório:** sim, quando o provedor for **gcp**
    
