package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    void uploadFileAWS(Product product, MultipartFile multipartFile,
                       MultipartFile[] multipartFiles, boolean exist);
}
