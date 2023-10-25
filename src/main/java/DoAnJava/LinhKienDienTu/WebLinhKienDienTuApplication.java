package DoAnJava.LinhKienDienTu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class WebLinhKienDienTuApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebLinhKienDienTuApplication.class, args);
	}

}
