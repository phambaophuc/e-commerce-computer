package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Product;
import DoAnJava.LinhKienDienTu.repository.ProductRepository;
import DoAnJava.LinhKienDienTu.services.S3Service;
import DoAnJava.LinhKienDienTu.utils.S3Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private ProductRepository productRepository;

    @Override
    @Async
    public void uploadFileAWS(Product product, MultipartFile multipartFile, MultipartFile[] multipartFiles, boolean exist) {
        if (exist) {
            Product currentProduct = productRepository.findById(product.getProductId()).orElse(null);
            boolean isMainImageUpdated = multipartFile != null && !multipartFile.isEmpty();

            if (isMainImageUpdated) {
                uploadMainImageToS3AndSetProductField(multipartFile, product);
            } else {
                product.setMainImage(currentProduct.getMainImage());
            }

            int count = 0;
            for (MultipartFile extraMultipart : multipartFiles) {
                if (!extraMultipart.isEmpty()) {
                    uploadExtraImagesToS3AndSetProductFields(extraMultipart, product, count);
                    count++;
                } else {
                    product.setExtraImage1(currentProduct.getExtraImage1());
                    product.setExtraImage2(currentProduct.getExtraImage2());
                    product.setExtraImage3(currentProduct.getExtraImage3());
                    break;
                }
            }
        } else {
            uploadMainImageToS3AndSetProductField(multipartFile, product);

            int count = 0;
            for (MultipartFile extraMultipart : multipartFiles) {
                if (!extraMultipart.isEmpty()) {
                    uploadExtraImagesToS3AndSetProductFields(extraMultipart, product, count);
                    count++;
                }
            }
        }
    }

    private void uploadMainImageToS3AndSetProductField(MultipartFile multipartFile,
                                                         Product product) {
        String mainImageName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        product.setMainImage(mainImageName);

        try {
            S3Util.uploadFile(mainImageName, multipartFile.getInputStream());
            log.info("File " + mainImageName + " has been uploaded successfully!");
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

    private void uploadExtraImagesToS3AndSetProductFields(MultipartFile extraMultipart, Product product,
                                                            int count) {
        String extraImageName = UUID.randomUUID() + "."
                + StringUtils.getFilenameExtension(extraMultipart.getOriginalFilename());
        if (count == 0) product.setExtraImage1(extraImageName);
        if (count == 1) product.setExtraImage2(extraImageName);
        if (count == 2) product.setExtraImage3(extraImageName);

        try {
            S3Util.uploadFile(extraImageName, extraMultipart.getInputStream());
            log.info("File " + extraImageName + " has been uploaded successfully!");
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

}
