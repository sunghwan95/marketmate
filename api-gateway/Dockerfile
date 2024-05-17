# Ubuntu 22.04 이미지를 기반으로 설정
FROM ubuntu:22.04

# 패키지 목록 업데이트 및 필수 소프트웨어 설치
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# 애플리케이션 디렉토리 생성 및 설정
WORKDIR /app

# Gradle 래퍼와 프로젝트 파일 복사
COPY gradlew /app/
COPY gradle /app/gradle
COPY ../build.gradle /app/
COPY ../settings.gradle /app/
COPY src /app/src

# Gradle 실행 권한 부여 및 빌드 실행
RUN chmod +x ./gradlew && ./gradlew clean build bootJar -x test

# 빌드된 JAR 파일 복사
COPY build/libs/*.jar apiGateway.jar

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","apiGateway.jar"]
