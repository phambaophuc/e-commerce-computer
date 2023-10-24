package DoAnJava.LinhKienDienTu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebLinhKienDienTuApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebLinhKienDienTuApplication.class, args);
	}

}
