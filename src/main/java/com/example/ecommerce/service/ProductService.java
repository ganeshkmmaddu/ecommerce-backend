package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
		Product product = mapToEntity(productRequestDTO);
		Product savedProduct = productRepository.save(product);
		return mapToResponseDTO(savedProduct);
	}

	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAll().stream()
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
		return product;
	}

}
