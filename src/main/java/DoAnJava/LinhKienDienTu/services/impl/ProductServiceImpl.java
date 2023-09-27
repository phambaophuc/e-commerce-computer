package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Product;
import DoAnJava.LinhKienDienTu.repository.ProductRepository;
import DoAnJava.LinhKienDienTu.services.ProductService;
import DoAnJava.LinhKienDienTu.utils.S3Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại.");
        }
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByProductNameContaining(productName);
    }

    @Override
    public List<Product> getProductByCategory(String categoryName) {
        return productRepository.findByCategory_CategoryName(categoryName);
    }

    @Override
    public List<Product> getProductByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void uploadFileAWS(Product product, MultipartFile multipartFile,
                              MultipartFile[] multipartFiles, boolean exist) {

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
