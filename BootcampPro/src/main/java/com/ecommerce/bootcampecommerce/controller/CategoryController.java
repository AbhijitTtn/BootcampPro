package com.ecommerce.bootcampecommerce.controller;


import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.dto.CategoryDTO;
import com.ecommerce.bootcampecommerce.entity.Category;
import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;
import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataValue;
import com.ecommerce.bootcampecommerce.entity.Product;
import com.ecommerce.bootcampecommerce.repository.CategoryMetaDataFieldRepository;
import com.ecommerce.bootcampecommerce.repository.CategoryMetaDataValueRepository;
import com.ecommerce.bootcampecommerce.repository.CategoryRepository;
import com.ecommerce.bootcampecommerce.repository.ProductRepository;
import com.ecommerce.bootcampecommerce.response.CategoryFilteringResponse;
import com.ecommerce.bootcampecommerce.response.CategoryResponse;
import com.ecommerce.bootcampecommerce.response.MetaDataResponse;
import com.ecommerce.bootcampecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetaDataFieldRepository categoryMetaDataFieldRepository;

    @Autowired
    private CategoryMetaDataValueRepository categoryMetaDataValueRepository;

    @Autowired
    private ProductRepository productRepository;
@PreAuthorize("hasRole('ADMIN')")
    //1. Admin(OK)
    @PostMapping("/admin/addCategoryMetaDataField")
    public void addCategoryMetaDataField(@Valid @RequestBody CategoryMetaDataField categoryMetaDataField
            ,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Optional<CategoryMetaDataField> categoryMeta = categoryMetaDataFieldRepository
                .findByName(categoryMetaDataField.getName());

        if(categoryMeta.isEmpty()){
            categoryMetaDataFieldRepository.save(categoryMetaDataField);
            JsonFormatExceptionClass.getJsonMessage("categoryMetadata field  added successfully",request,response);
        }else{
            JsonFormatExceptionClass.getJsonMessage("category already exist",request,response);
        }
    }

    //2. Admin(Ok)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findMetaField")
    public ResponseEntity<?> findAllCategoryMetaField(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<CategoryMetaDataField> allData = categoryService.findAllCategory();
        if(allData.size() != 0){
            return ResponseEntity.ok(categoryService.findAllCategory());
        }else{

            JsonFormatExceptionClass.getJsonMessage("no category metadata field found",request,response);
        }
        return null;
    }

    //3. Admin(Ok)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addCategory")
    public void addAllCategory(@RequestBody CategoryDTO categoryDTO
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        Category category = categoryRepository.findByCategoryName(categoryDTO.getName());
        if(category == null){
            Category category1 = new Category();
            category1.setCategoryName(categoryDTO.getName());
            if(categoryDTO.getParentId() != null)
            category1.setCategory(categoryRepository.findById(categoryDTO.getParentId()).get());
            categoryRepository.save(category1);
            JsonFormatExceptionClass.getJsonMessage("category successfully added",request,response);
        }else{
            JsonFormatExceptionClass.getJsonMessage("category name already exist",request,response);
        }

    }

    //4. Admin(Partial)

    @GetMapping("/admin/findAllCategoryById")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse findAllCategoryByCategoryId(
            @RequestParam Long id,
            HttpServletRequest request,HttpServletResponse response) throws Exception{
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {

            List<Category> parentCategories = categoryRepository.findByParentCategoryId(id);
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setChildCategory(category.get());
            categoryResponse.setParentCategory(parentCategories);
            return categoryResponse;
        }else{
            JsonFormatExceptionClass.getJsonMessage("category id does not exist" , request,response);
        }
        return null;
    }


    //5. Admin(Partial)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findAllCategory")
    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    //7. Admin
    @PutMapping("/admin/updateCategory")
    public void updateCategoryValue(@RequestBody CategoryDTO categoryDTO
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        Category category = categoryRepository.findByCategoryName(categoryDTO.getName());
        if(category == null){
            Category category1 = categoryRepository.findById(categoryDTO.getId()).get();
            category1.setCategoryName(categoryDTO.getName());
            categoryRepository.save(category1);
            JsonFormatExceptionClass.getJsonMessage("category successfully updated",request,response);
        }else{
            JsonFormatExceptionClass.getJsonMessage("category name already exist",request,response);
        }
    }

    //8. Admin(Ok)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addMetadataAndValues")
    public void addMetadatafieldAndValues(@RequestParam Long id
            , @RequestBody MetaDataResponse metaDataResponse
            ,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Optional<Category> category = categoryRepository.findById(id);
        Optional<CategoryMetaDataField> categoryMetaDataField =
                categoryMetaDataFieldRepository.findById(metaDataResponse.getId());
        if(category.isPresent() && categoryMetaDataField.isPresent()){
            CategoryMetaDataValue categoryMetaDataValue = new CategoryMetaDataValue();
            categoryMetaDataValue.setCategoryMetaDataField(categoryMetaDataField.get());
            categoryMetaDataValue.setValues(metaDataResponse.getValues());
            categoryMetaDataValue.setCategory(category.get());
            categoryMetaDataValueRepository.save(categoryMetaDataValue);
            JsonFormatExceptionClass.getJsonMessage("Meta feild value added sucessfully",request,response);
        } else {
            JsonFormatExceptionClass.getJsonMessage("Metefeildid/ categoryid is not present",request,response);
        }
    }


    //9. Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/updateMetadataAndValues")
    @Transactional
    public void updateMetadatafieldAndValues(@RequestParam Long id,@RequestParam Long metaid
            , @RequestBody MetaDataResponse metaDataResponse
            ,HttpServletRequest request,HttpServletResponse response) throws Exception{

        CategoryMetaDataValue categoryMetaDataValue =
                categoryMetaDataValueRepository.findByMetaId(metaid,id);
        if(categoryMetaDataValue != null){
            categoryMetaDataValue.setValues(metaDataResponse.getValues());

            categoryMetaDataValueRepository.save(categoryMetaDataValue);
            JsonFormatExceptionClass.getJsonMessage("metafield updated",request,response);
        }else{
            JsonFormatExceptionClass.getJsonMessage("wrong category id and metafield id",request,response);
        }

    }

    //1. Seller(NW)
    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller/findAllCategory")
    public List<CategoryMetaDataValue> findAllCategoryOfSeller(){
        List<CategoryMetaDataValue> categoryMetaDataValues = categoryMetaDataValueRepository.findAll();
        for(CategoryMetaDataValue c : categoryMetaDataValues){
            c.getCategory().setProducts(null);
        }
        return categoryMetaDataValues;
    }

    //1. Customer
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer/categories")
    public List<Category>  listAllCategories(@RequestParam(value = "categoryId" , required = false) Long id, HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        if(id==null)
            return categoryRepository.findAllRootCategory();
        else
        {
            if(!categoryRepository.findByParentCategoryId(id).isEmpty())
                return categoryRepository.findByParentCategoryId(id);
            else
                JsonFormatExceptionClass.getJsonMessage("Category not found",request,response);
        }
        return null;
    }

    //2. Customer
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer/filteredCategory")
    public CategoryFilteringResponse filteredCategory (@RequestParam(value = "categoryId") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Category> category=categoryRepository.findByParentCategoryId(id);
        if(category.isEmpty()) {
            CategoryFilteringResponse categoryFilteringResponse = new CategoryFilteringResponse();
            List<CategoryMetaDataValue> categoryMetaDataValues =
                    categoryMetaDataValueRepository.findByCategoryId(id);
            Map<String, String> mapOfCategoryValues = new HashMap<>();
            for (CategoryMetaDataValue value : categoryMetaDataValues) {
                mapOfCategoryValues.put(value.getCategoryMetaDataField().getName(), value.getValues());
            }
            categoryFilteringResponse.setCategoryMetaDataValues(mapOfCategoryValues);
            List<Product> products = productRepository.findByCategoryId(id);
            categoryFilteringResponse.setBrands(products.stream()
                    .map(product -> product.getBrand()).collect(Collectors.toList()));
            List<Object[]> objects = productRepository.findByJoinProductVariation(id);
            if(objects.get(0)[1] != null || objects.get(0)[0] != null) {
                categoryFilteringResponse.setMaxPrice((float) objects.get(0)[1]);
                categoryFilteringResponse.setMinPrice((float) objects.get(0)[0]);
                return categoryFilteringResponse;
            } else {
                JsonFormatExceptionClass.getJsonMessage("Category doesn't have a variation / product",request,response);
            }
        }
        else {
            JsonFormatExceptionClass.getJsonMessage("Not a leaf category",request,response);
        }
        return null;
    }


    }

