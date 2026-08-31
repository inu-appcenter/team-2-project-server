FROM eclipse-temurin:21-jre

WORKDIR /app

# jar는 GitHub Actions에서 미리 빌드해서 넣어줌 (build/libs/*.jar)
COPY build/libs/*.jar app.jar

EXPOSE 8080
# Oracle JDBC가 컨테이너 타임존을 "지역 이름"으로 못 찾아 ORA-01882가 나는 문제 방지
# (지역 이름 대신 UTC 오프셋으로 보내게 강제)
ENTRYPOINT ["java", "-Doracle.jdbc.timezoneAsRegion=false", "-jar", "app.jar"]
