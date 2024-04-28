#!/bin/bash
# Docker 컨테이너와 이미지를 빌드
docker-compose up --build -d

# 사용하지 않는 모든 이미지 정리
docker image prune -f
