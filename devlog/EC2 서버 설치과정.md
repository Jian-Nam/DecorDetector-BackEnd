# EC2 amazon linux 서버 설치과정
## 환경변수
### 일시적 환경변수
```
export 환경변수_이름="환경변수_값"
```

### 영구 환경변수 설정
- **/etc/profile**

이 파일에 설정한 변수는 bash 로그인 쉘에 진입할 때 로드된다. 해당 파일 마지막에 다음 선언하면 영구적으로 사용가능하다.
```
# 설정
export 환경변수_이름="환경변수_값"

# 해제
unset 환경변수_이름

# 확인
echo $환경변수_이름
```
적용시키기 
```
source /etc/profile
```
- **~/.bashrc**

사용자별 쉘 구성 파일이다. ~를 붙이면 현재 사용자 경로가 붙기 때문에 현재 사용자 대상 bashrc이다. 선언 방법은 위와 같다.

### 환경변수 확인
```
echo $환경변수_이름
```

## 프로세스 확인
```
ps -f | grep 프로세스명
```

## 권한 주기
실행 권한: 
```
chmod +x 파일명
```
읽기 권한: 
```
chmod +r 파일명
```
쓰기 권한:
```
chmod +w 파일명
```
합쳐서도 사용 가능하다.
```
chmod +rw 파일명
```
`sudo`를 앞에 붙이고 명령어를 실행하면 관리자 권한으로 실행 가능

## swap메모리 설정(메모리 늘리기)
swap 용량 확인 
```
sudo swapon -s
```
메모리 용량 확인 
```
sudo free -m
```

1. dd 명령을 사용하여 루트 파일 시스템에 스왑 파일을 생성한다. 명령에서 bs는 블록 크기이고 count는 블록 수이 다. 
스왑 파일의 크기는 dd 명령의 블록 크기 옵션에 블록 수 옵션을 곱한 값이다. 이러한 값을 조정하여 원하는 스왑 파일 크기를 결정한다.
지정한 블록 크기는 인스턴스에서 사용 가능한 메모리보다 작아야 한다. 그렇지 않으면 "memory exhausted" 오류가 발생합니다.
이 예제 dd 명령에서 스왑 파일은 4GB(128MB x 32)입니다.
```
$ sudo dd if=/dev/zero of=/swapfile bs=128M count=32
```
2. 스왑 파일의 읽기 및 쓰기 권한을 업데이트합니다.
```
$ sudo chmod 600 /swapfile
```
3. Linux 스왑 영역을 설정합니다.
```
$ sudo mkswap /swapfile
```
4. 스왑 공간에 스왑 파일을 추가하여 스왑 파일을 즉시 사용할 수 있도록 합니다.
```
$ sudo swapon /swapfile
```
5. 프로시저가 성공적인지 확인합니다.
```
$ sudo swapon -s
```
6. /etc/fstab 파일을 편집하여 부팅 시 스왑 파일을 시작합니다.
편집기에서 파일을 엽니다.
```
$ sudo vi /etc/fstab
```
파일 끝에 다음 줄을 새로 추가하고 파일을 저장한 다음 종료합니다.
```
/swapfile swap swap defaults 0 0
```
출처: https://repost.aws/ko/knowledge-center/ec2-memory-swap-file

## ElasticSearch 설치

### 압축파일 다운로드
```
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.7.1-linux-x86_64.tar.gz
```

### 압축 풀기
```
tar -xzf elasticsearch-8.7.1-linux-x86_64.tar.gz
```

### 폴더 이동
```
cd elasticsearch-8.7.1/
```

### elastic search 실행
```Linux
# 프론트에서 실행
./bin/elasticsearch

# 백그라운드에서 실행
./bin/elasticsearch -d

# 백그라운드에서 실행, pid 저장
./bin/elasticsearch -d -p pid
```

### elasticsearch 종료
```
pkill -F pid
```

비밀번호 생성(옵션) - elasticsearch가 켜져있을 때 실행해야 된다.
```
# 랜덤으로 생성 
bin/elasticsearch-reset-password -u elastic

# 스스로 설정
bin/elasticsearch-reset-password -u elastic -i

# 비밀번호 환경변수 선언 
export ELASTIC_PASSWORD="your_password"
```

인증절차를 무시하고 싶다면 elasticsearch를 한번 실행했다가 끈 후
`/config/elasticsearch.yml` 파일을 다음과 같이 4개의 요소를 true -> false로 변경
```
xpack.security.enabled: false

xpack.security.enrollment.enabled: false

xpack.security.http.ssl: 
    enabled: false
    
xpack.security.transport.ssl:
    enabled: false
```

실행 확인 - 환경번수에 주의하자 `$ES_HOME` 변수를 설정해두었다면 다음 그대로 실행
```
# 인증절차 있다면
curl --cacert $ES_HOME/config/certs/http_ca.crt -u elastic https://localhost:9200

# 비밀번호 한번에 입력(ES_PASSWORD 환경변수 설정 필요)
curl --cacert $ES_HOME/config/certs/http_ca.crt -u elastic:$ELASTIC_PASSWORD https://localhost:9200

# 인증절차 없다면
curl -XGET localhost:9200
```

## mysql 설치
아마존 리눅스 2023을 쓸 때는 el9 버전 레포지토리를 사용해주어야 한다
```
sudo dnf install https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm
sudo dnf install mysql-community-server
```

기존방식(비교용)
```
wget dev.mysql.com/get/mysql80-commuity-release-e17-5.noarch.rpm
rpm -ivh mysql-community-release-e17.5.noarch.rpm
```

버전 확인
```
mysql --version
```

백그라운드 실행
```
systemctl start mysqld
```

상태체크
```
systemctl status mysqld
```

비밀번호 바꾸기
```
ALTER user 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '비밀번호';
```

mysql 접속
```
mysql -u root -p
```
## Spring Backend 배포
```
git clone 깃허브 주소
cd 폴더 디렉토리
chmod +x gradlew
./gradlew build
cd build/libs
nohup java -jar api-0.0.1-SNAPSHOT.jar &
```
```
jobs
fg %인덱스
control + c
```
nohup java -jar api-0.0.1-SNAPSHOT.jar &