package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
		Product product = mapToEntity(productRequestDTO);
		Product savedProduct = productRepository.save(product);
		return mapToResponseDTO(savedProduct);
	}

	public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy) {
    Pageable pageable =
            PageRequest.of(page, size, org.springframework.data.domain.Sort.by(sortBy));

    return productRepository.findAll(pageable)
            .map(this::mapToResponseDTO);
   }
   public List<ProductResponseDTO> searchProducts(String keyword, int page, int size, String sortBy) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

    Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);

    return productPage.getContent()
            .stream()
            .map(this::mapToResponseDTO)
            .toList();
   }

	public ProductResponseDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		return mapToResponseDTO(product);
	}

	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

		existingProduct.setName(productRequestDTO.getName());
		existingProduct.setDescription(productRequestDTO.getDescription());
		existingProduct.setPrice(productRequestDTO.getPrice());
		existingProduct.setStockQuantity(productRequestDTO.getStockQuantity());

		Product updatedProduct = productRepository.save(existingProduct);
		return mapToResponseDTO(updatedProduct);
	}

	public void deleteProduct(Long id) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		productRepository.delete(existingProduct);
	}

	private ProductResponseDTO mapToResponseDTO(Product product) {
		return new ProductResponseDTO(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getStockQuantity(),
				product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
				product.getCreatedAt(),
				product.getUpdatedAt()
		);
	}

	private Product mapToEntity(ProductRequestDTO dto) {
		Product product = new Product();
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setStockQuantity(dto.getStockQuantity());
		if (dto.getCategoryId() != null) {
    Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Category not found with id: " + dto.getCategoryId()));

    product.setCategory(category);
    }
		return product;
	}

}
