package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.dto.ProductDTO;
import com.ecommerce.bootcampecommerce.dto.ProductVariationDTO;
import com.ecommerce.bootcampecommerce.entity.*;
import com.ecommerce.bootcampecommerce.repository.*;
import com.ecommerce.bootcampecommerce.request.UpdateProductRequest;
import com.ecommerce.bootcampecommerce.request.UpdateProductVariationRequest;
import com.ecommerce.bootcampecommerce.response.SellerViewProductById;
import com.ecommerce.bootcampecommerce.response.ViewProductVariationResponse;
import com.ecommerce.bootcampecommerce.service.impl.ProductVariationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepo productVariationRepo;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductVariationImpl productVariationImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    //Admin 1
    @GetMapping("/admin/viewproduct")
    @PreAuthorize("hasRole('ADMIN')")
    public Product getProductByID(@RequestParam Long productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()){
            byId.get().setCategory(null);
            return byId.get();
        } else {
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
            return null;
        }
    }


    //Admin 2
    @GetMapping("/admin/viewallproduct")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> findAllProduct(){
        List<Product> products = productRepository.findAll();
        for (Product p: products) {
            p.setCategory(null);
        }
        return products;
    }


    //Admin 4
    @PutMapping("/admin/deactivateproduct")
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateProduct(@RequestParam Long productId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isEmpty()){
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        } else {
            if(byId.get().getActive() == true){
                byId.get().setActive(false);
                productRepository.save(byId.get());
                JsonFormatExceptionClass.getJsonMessage("Product De-Activated Sucessfully",request,response);
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product Already Deactivated",request,response);
            }
        }
    }


    //Admin 5
    @PutMapping("/admin/activateproduct")
    @PreAuthorize("hasRole('ADMIN')")
    public void activateProduct(@RequestParam Long productId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isEmpty()){
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        } else {
            if(byId.get().getActive() == false){
                byId.get().setActive(true);
                productRepository.save(byId.get());
                JsonFormatExceptionClass.getJsonMessage("Product Activated",request,response);
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product Already Activated",request,response);
            }
        }
    }

    //Customer 1
    @GetMapping("/customer/viewproduct")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Product getCustomerProductByID(@RequestParam Long productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()){
            if(byId.get().getActive() && !byId.get().getDeleted()){
                int count = productVariationRepo.findByProductId(byId.get().getId());
                if(count >= 1) {
                    byId.get().setCategory(null);
                    return byId.get();
                } else {
                    JsonFormatExceptionClass.getJsonMessage("Product has no variations",request,response);
                }
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product is deleted or inactive",request,response);
            }
        } else {
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        }
        return null;
    }

    //Customer 2
    @GetMapping("/customer/viewallproduct")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Product> viewAllProducts(@RequestParam Long categoryId,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        } else {
            List<Category> category1 = categoryRepository.findByParentCategoryId(category.get().getId());
            if(!category1.isEmpty()){
                JsonFormatExceptionClass.getJsonMessage("This is not the leaf category",request,response);
            } else {
                List<Product> product = productRepository.findByCategoryId(categoryId);
                if(product.isEmpty()){
                    JsonFormatExceptionClass.getJsonMessage("No Product belong to this Category",request,response);
                } else {
                    for (Product p: product) {
                        p.setCategory(null);
                    }
                    return product;
                }
            }
         }
        return null;
    }

    //Customer-3
    @GetMapping("/customer/viewallsimilarproduct")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<SellerViewProductById> viewAllSimilarProducts(@RequestParam Long productId,
                                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<Product> product=productRepository.findById(productId);
        if(product.isPresent())
        {
            List<Product> productsOfSameCategory=productRepository.findByCategoryId(product.get().getCategory().getId());
            System.out.println(product.get().getCategory().getId());
            if (!productsOfSameCategory.isEmpty())
            {
                List<SellerViewProductById> sellerViewProductById = new ArrayList<>();
                for (Product products : productsOfSameCategory) {
                    SellerViewProductById s = new SellerViewProductById();
                    s.setProductDTO(productEntityToDto(products));
                    s.setCategoryName(products.getCategory().getCategoryName());
                    sellerViewProductById.add(s);
                }
                return sellerViewProductById;
            } else
                JsonFormatExceptionClass.getJsonMessage("No Product similar to this product",request,response);
        } else
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        return null;
    }


    //1. Seller
    @PostMapping("seller/addProduct")
    public void addProduct(@RequestBody() ProductDTO productDTO,
                           HttpServletRequest request, HttpServletResponse response)throws Exception {
        List<Category> leafCategory = categoryRepository.findByParentCategoryId(productDTO.getCategoryId());
        if (leafCategory.isEmpty()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByEmail(username);
            Product product = productRepository.findProductName(productDTO.getName(), productDTO.getBrand(),
                    productDTO.getCategoryId(), user.getId());
            Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
            if (product == null) {
                Product entityProduct = productDtoToEntity(productDTO, category.get());
                productRepository.save(entityProduct);
                Seller seller = sellerRepository.findByUserId(user.getId()).get();
                System.out.println(seller.getCompanyContact());
                seller.addProducts(entityProduct);
                sellerRepository.save(seller);
                JsonFormatExceptionClass.getJsonMessage("Product added Sucessfully",request,response);
            } else {
                JsonFormatExceptionClass.getJsonMessage("product already exist",request,response);
            }
        } else {
            JsonFormatExceptionClass.getJsonMessage("category not a leaf node",request,response);
        }
    }


    //Seller 2
    @PostMapping("/seller/addproductvariation")
    @PreAuthorize("hasRole('SELLER')")
    public void addProductVariation(@Valid @RequestBody ProductVariationDTO productVariationDTO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Optional<Product> product = productRepository.findById(productVariationDTO.getProductId());
        Product bySellerAndProductId = productRepository.findBySellerAndProductId(user.getId(), productVariationDTO.getProductId());
        if(bySellerAndProductId != null){
            if(product.isEmpty()){
                JsonFormatExceptionClass.getJsonMessage("Product id is invalid",request,response);
            } else {
                if((product.get().getActive()) && (!product.get().getDeleted())) {
                    ProductVariation productVariation = productVariationImpl.DTOToEntity(productVariationDTO);
                    product.get().setProductVariations((new HashSet<>(Arrays.asList(productVariation))));
                    productRepository.save(product.get());
                    JsonFormatExceptionClass.getJsonMessage("Product variation added Sucessfully",request,response);
                } else {
                    JsonFormatExceptionClass.getJsonMessage("Product is either inactive or deleted",request,response);
                }
            }
        } else {
            JsonFormatExceptionClass.getJsonMessage("Product doesn't belong to you",request,response);
        }

    }

    //3. Seller
    @GetMapping("seller/viewProduct")
    public SellerViewProductById viewProduct(@RequestParam("productId") Long id,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        SellerViewProductById sellerViewProductById = new SellerViewProductById();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            String categoryName = product.get().getCategory().getCategoryName();
            if ((productRepository.findBySellerAndProductId(user.getId(), id)) != null) {
                sellerViewProductById.setProductDTO(productEntityToDto(product.get()));
                sellerViewProductById.setCategoryName(categoryName);
                return sellerViewProductById;
            } else
                JsonFormatExceptionClass.getJsonMessage("Product doesn't belong to you",request,response);
        } else
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        return null;
    }

    //Seller 4- Veiw Product Variation with product
    @GetMapping("/seller/productvariation")
    @PreAuthorize("hasRole('SELLER')")
    public ViewProductVariationResponse viewProductVariation
            (@RequestParam Long productVariationId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Optional<ProductVariation> productVariation = productVariationRepo.findById(productVariationId);
        if(productVariation.isPresent()) {
            Long productId = productVariationRepo.findByProduct(productVariationId);
            Product product = productRepository.findById(productId).get();
            Product bySellerAndProductId = productRepository.findBySellerAndProductId(user.getId(), product.getId());

            if (bySellerAndProductId != null){
                ViewProductVariationResponse viewProductVariationResponse = new ViewProductVariationResponse();
               product.setCategory(null);
                viewProductVariationResponse.setProduct(product);
                viewProductVariationResponse.setProductVariation(productVariation.get());
                return viewProductVariationResponse;
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product doesn't belong to you",request,response);
            }
        } else {
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        }
        return null;
    }

    //5. Seller
    @GetMapping("/seller/viewAllProducts")
    public List<SellerViewProductById> viewAllProducts() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        List<Product> products = productRepository.findBySellerId(user.getId());
        List<SellerViewProductById> sellerViewProductById = new ArrayList<>();
        for (Product product : products) {
            SellerViewProductById s = new SellerViewProductById();
            s.setProductDTO(productEntityToDto(product));
            s.setCategoryName(product.getCategory().getCategoryName());
            sellerViewProductById.add(s);
        }
        return sellerViewProductById;
    }

    //Seller-6
    @GetMapping("/seller/viewproductvariation")
    @PreAuthorize("hasRole('SELLER')")
    public List<ProductVariation> veiwAllProductVariation(@RequestParam Long productId,
                                                          HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Product> product = productRepository.findById(productId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        if(product.isPresent()) {
            Product bySellerAndProductId = productRepository.findBySellerAndProductId(user.getId(), productId);
            if (bySellerAndProductId != null) {
                List<ProductVariation> productVariations = productVariationRepo.findVariationByProductId(productId);
                if(productVariations.isEmpty()){
                    JsonFormatExceptionClass.getJsonMessage("No variation found",request,response);
                }
                return productVariations;
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product doesn't belong to you",request,response);
            }

        } else {
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        }

        return null;
    }

    //7. Seller
    @DeleteMapping("/seller/deleteproduct")
    @Transactional
    void deleteProduct(@RequestParam("productId") Long id,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        SellerViewProductById sellerViewProductById = new SellerViewProductById();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isEmpty()) {
            if ((productRepository.findBySellerAndProductId(user.getId(), id)) != null) {
                productRepository.deleteById(id);
                JsonFormatExceptionClass.getJsonMessage("Product deleted sucessfully",request,response);
            } else
                JsonFormatExceptionClass.getJsonMessage("Product does not belong to you",request,response);
        } else
            JsonFormatExceptionClass.getJsonMessage("Product id is not available",request,response);
    }

    //8. Seller
    @PutMapping("seller/updateProduct")
    public void updateProduct(@RequestBody UpdateProductRequest updateProductRequest,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Product product = productRepository.findById(updateProductRequest.getId()).get();
        if (product != null)
        {
            if (productRepository.findBySellerAndProductId(user.getId(), updateProductRequest.getId()) != null)
            {
                if (updateProductRequest.getName() != null)
                {
                    Product productExist = productRepository.findProductName(updateProductRequest.getName(), product.getBrand(),
                            product.getCategory().getId(), user.getId());
                    if (productExist == null)
                        product.setName(updateProductRequest.getName());
                    else
                        JsonFormatExceptionClass.getJsonMessage("product name already exist",request,response);
                }
                if (updateProductRequest.getDescription() != null)
                    product.setDescription(updateProductRequest.getDescription());
                if (updateProductRequest.getCancellable() != null)
                    product.setCancellable(updateProductRequest.getCancellable());
                if (updateProductRequest.getReturnable() != null)
                    product.setReturnable(updateProductRequest.getReturnable());
                productRepository.save(product);
            }
            else
                JsonFormatExceptionClass.getJsonMessage("product does not belong to you",request,response);
        }
        else
            JsonFormatExceptionClass.getJsonMessage("product is invalid",request,response);
    }

    //Seller-9
    @PatchMapping("/seller/updateProductVariation")
    @PreAuthorize("hasRole('SELLER')")
    public void updateProductVariation(@Valid @RequestBody UpdateProductVariationRequest updateProductVariationRequest,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Optional<ProductVariation> productVariation =
                productVariationRepo.findById(updateProductVariationRequest.getProductVariationId());
        boolean needUpdate = false;
        if(productVariation.isPresent()) {
            Long productId = productVariationRepo.findByProduct(updateProductVariationRequest.getProductVariationId());
            Product product = productRepository.findById(productId).get();
            Product bySellerAndProductId = productRepository.findBySellerAndProductId(user.getId(), product.getId());
            if (bySellerAndProductId != null){

                if ((updateProductVariationRequest.getMetadata()) != null) {
                    productVariation.get().setMetadata(updateProductVariationRequest.getMetadata());
                    needUpdate = true;
                }

                if (updateProductVariationRequest.getPrice() != null) {
                    productVariation.get().setPrice(updateProductVariationRequest.getPrice());
                    needUpdate = true;
                }

                if (updateProductVariationRequest.getQuantityAvailable() != null) {
                    productVariation.get().setQuantityAvailable(updateProductVariationRequest.getQuantityAvailable());
                    needUpdate = true;
                }

                if (updateProductVariationRequest.getActive() != null) {
                    productVariation.get().setActive((boolean) updateProductVariationRequest.getActive());
                    needUpdate = true;
                }

                if (needUpdate) {
                    productVariationRepo.save(productVariation.get());
                    JsonFormatExceptionClass.getJsonMessage("Variation Updated Sucessfully",request,response);
                }
            } else {
                JsonFormatExceptionClass.getJsonMessage("Product doesn't belong to you",request,response);
            }
        } else {
            JsonFormatExceptionClass.getJsonMessage("Invalid Id",request,response);
        }


    }

    Product productDtoToEntity(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setBrand(productDTO.getBrand());
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setCancellable(productDTO.getCancellable());
        product.setReturnable(productDTO.getReturnable());
        product.setName(productDTO.getName());
        return product;

    }

    private ProductDTO productEntityToDto(Product product) {
        return new ProductDTO(product.getName(), product.getBrand(),
                product.getCategory().getId(), product.getDescription()
                , product.getReturnable(), product.getCancellable());
    }


}

















