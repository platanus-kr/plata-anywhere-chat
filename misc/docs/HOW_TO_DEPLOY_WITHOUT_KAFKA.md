# 카프카를 사용하지 않는 배포

> 카프카 없이 단독 구성으로 사용하는 배포방법 입니다.

## 준비물

> A 또는 B 중 택 1

#### A) Docker를 사용하는 배포

우선 도커를 배포하면 준비는 끝납니다.

```bash
docker compose -f misc/docker-compose-standalone.yml up -d
```


#### B) Docker를 사용하지 않는 배포

실제 배포를 위해 다음 준비가 필요합니다

- MariaDB 10 이상
- MongoDB
- Redis

## 처음 실행 하기 전에

> 서비스 실행을 위한 환경변수를 설정합니다. (생략)

`env` 등을 사용해 지정된 환경변수를 확인할 수 있으면 다음으로 넘어갑니다.

> 어플리케이션에 필요한 MariaDB 테이블 스키마가 필요합니다.

`misc/db/mariadb-schema-pac.sql`을 MariaDB 에 적용합니다.

> production 및 카프카 없는 환경은 기본적으로 SSL 통신을 기반으로 합니다.   
> 해당 도메인이 연결되는 게이트웨이에 SSL 인증서 설정을 합니다.   
> 아래는 필요한 도메인 예시입니다. 동일한 도메인을 사용해서 내부에서 프록시 하셔도 됩니다.   

- message.fqdn.com
- web.fqdn.com

> 방화벽에 TCP 포트를 허용합니다.   
> 기본값은 3120, 3121 입니다.

```bash
sudo firewall-cmd --permanent --zone=public --add-port=3120-3121/tcp
sudo firewall-cmd --reload
sudo firewall-cmd --list-all
```

## 실행하기

```bash
git clone https://github.com/platanus-kr/plata-anywhere-chat.git pac
cd pac

./gradlew web:clean && ./gradlew message:clean
./gradlew web:bootJar && ./gradlew message:bootJar
```

위 사항까지 진행하면 실행 가능한 jar 파일이 빌드됩니다.

```bash
nohup java -jar -Dspring.profiles.active=production web/build/libs/web-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
nohup java -jar -Dspring.profiles.active=standalone message/build/libs/message-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
```

그리고 지정한 `web` 도메인으로 접속하면 접근이 됩니다.   
production 성격의 프로파일에서는 기본 사용자를 생성하고있지 않으니 사용자 등록 후 사용해주시기 바랍니다.
