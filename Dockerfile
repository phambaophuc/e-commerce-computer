FROM eclipse-temurin:17
WORKDIR /app
COPY target/LinhKienDienTu-0.0.1-SNAPSHOT.jar /app/LinhKienDienTu.jar
ENTRYPOINT ["java", "-jar", "LinhKienDienTu.jar"]
