package com.example.ecommerce.service;

import com.example.ecommerce.dto.CategoryRequestDTO;
import com.example.ecommerce.dto.CategoryResponseDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
		Category category = mapToEntity(requestDTO);
		Category savedCategory = categoryRepository.save(category);
		return mapToResponseDTO(savedCategory);
	}

	public List<CategoryResponseDTO> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map(this::mapToResponseDTO)
				.toList();
	}

	public CategoryResponseDTO getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		return mapToResponseDTO(category);
	}

	public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

		existingCategory.setName(requestDTO.getName());
		existingCategory.setDescription(requestDTO.getDescription());

		Category updatedCategory = categoryRepository.save(existingCategory);
		return mapToResponseDTO(updatedCategory);
	}

	public void deleteCategory(Long id) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		categoryRepository.delete(existingCategory);
	}

	private Category mapToEntity(CategoryRequestDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		return category;
	}

	private CategoryResponseDTO mapToResponseDTO(Category category) {
		return new CategoryResponseDTO(
				category.getId(),
				category.getName(),
				category.getDescription(),
				category.getCreatedAt(),
				category.getUpdatedAt()
		);
	}

}
