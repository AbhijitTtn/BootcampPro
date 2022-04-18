package com.ecommerce.bootcampecommerce;

import com.ecommerce.bootcampecommerce.entity.*;
import com.ecommerce.bootcampecommerce.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootTest
class BootcampecommerceApplicationTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetaDataFieldRepository categoryMetaDataFieldRepository;

    @Autowired
    CategoryMetaDataValueRepository categoryMetaDataValueRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    //	@Test
    void createCustomer() {

        Role role = roleRepository.findByAuthority("customer");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName("Rahul");
        user.setLastName("Singh");
        user.setEmail("test@gmail.com");
        user.setPassword("pass@1234");
        user.setRoles(roles);

        Address address1 = new Address();
        address1.setCity("London");
        address1.setState("los vegas");
        address1.setCountry("Us");
        address1.setZipcode("842001");
        address1.setAddressline("E street");
        address1.setLabel("home");

        Address address2 = new Address();
        address2.setCity("delhi");
        address2.setState("delhi 6");
        address2.setCountry("india");
        address2.setZipcode("842001");
        address2.setAddressline("E street");
        address2.setLabel("home");

        Set<Address> addressSet = new HashSet<>();
        addressSet.add(address1);
        addressSet.add(address2);

        Customer customer = new Customer();
        customer.setContactNumber("8090151253");
        user.setAddresses(addressSet);
        customer.setUser(user);

        customerRepository.save(customer);
    }


 //   @Test
    public void testCategory() {
        Category category1 = new Category();
        category1.setCategoryName("fashion");
        category1.setCategory(null);
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setCategoryName("shoes");
        category2.setCategory(category1);
        categoryRepository.save(category2);
        Category category3 = new Category();
        category3.setCategoryName("electronics");
        categoryRepository.save(category3);
        Category category4 = new Category();
        category4.setCategoryName("samsung");
        category4.setCategory(category3);
        categoryRepository.save(category4);
        Category category5 = new Category();
        category5.setCategoryName("shirt");
        category5.setCategory(category1);
        categoryRepository.save(category5);
        Category category6 = new Category();
        category6.setCategoryName("tshirt");
        category6.setCategory(category1);
        categoryRepository.save(category6);
    }

  //  @Test
    void enterData() throws JSONException {
        Role role = roleRepository.findByAuthority("ROLE_SELLER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doug");
        user.setEmail("cseabhijit01@gmail.com");
        user.setPassword(passwordEncoder.encode("App@1234"));
        user.setRoles(roles);

        Seller seller = new Seller();
        seller.setGST("24AAACC4175D1Z4");
        seller.setCompanyContact("9670684098");
        seller.setCompanyName("india Pvt. Ltd.");
        seller.setUser(user);

        Product product = new Product();
        //product.setCategory(category2);
        product.setBrand("Mufti");
        product.setDescription("bad brand");

        Product product1 = new Product();
        //product1.setCategory(category3);
        product1.setBrand("Puma");
        product1.setDescription("Good brand");
        seller.setProducts(new HashSet<Product>(Arrays.asList(product, product1)));


        Category category = new Category();
        category.setCategoryName("Fashion");
        category.setCategory(null);

        Category category1 = new Category();
        category1.setCategoryName("Upperwear");
        category1.setCategory(category);

        Category category2 = new Category();
        category2.setCategoryName("Shirt");
        category2.setCategory(category1);

        Category category3 = new Category();
        category3.setId(4L);
        category3.setCategoryName("T-Shirt");
        category3.setCategory(category1);

        category2.setProducts(new HashSet<>(Arrays.asList(product, product1)));
        category3.setProducts(new HashSet<>(Arrays.asList(product, product1)));

        CategoryMetaDataField categoryMetaDataField = new CategoryMetaDataField();
        categoryMetaDataField.setName("size");

        CategoryMetaDataField categoryMetaDataField1 = new CategoryMetaDataField();
        categoryMetaDataField1.setName("color");

        CategoryMetaDataValue categoryMetaDataValue = new CategoryMetaDataValue();
        categoryMetaDataValue.setCategoryMetaDataField(categoryMetaDataField);
        categoryMetaDataValue.setCategory(category2);
        categoryMetaDataValue.setValues("S, M, L, XL, XXL");

        CategoryMetaDataValue categoryMetaDataValue1 = new CategoryMetaDataValue();
        categoryMetaDataValue1.setCategoryMetaDataField(categoryMetaDataField1);
        categoryMetaDataValue1.setCategory(category2);
        categoryMetaDataValue1.setValues("red, blue, green, yellow");

        CategoryMetaDataValue categoryMetaDataValue2 = new CategoryMetaDataValue();
        categoryMetaDataValue2.setCategoryMetaDataField(categoryMetaDataField);
        categoryMetaDataValue2.setCategory(category3);
        categoryMetaDataValue2.setValues("S, M, L, XL, XXL");

        CategoryMetaDataValue categoryMetaDataValue3 = new CategoryMetaDataValue();
        categoryMetaDataValue3.setCategoryMetaDataField(categoryMetaDataField1);
        categoryMetaDataValue3.setCategory(category3);
        categoryMetaDataValue3.setValues("red, blue, green, yellow");


        ProductVariation productVariation = new ProductVariation();
        productVariation.setPrice(1200);
        Map<String, Object> stringObject = new HashMap<>();
        stringObject.put("size", "XL");
        stringObject.put("color", "red");
        productVariation.setMetadata(stringObject);
        productVariation.setQuantityAvailable(3);

        ProductVariation productVariation1 = new ProductVariation();
        productVariation1.setPrice(1000);
        Map<String, Object> jsonObject2 = new HashMap<>();
        jsonObject2.put("size", "XL");
        jsonObject2.put("color", "red");
        productVariation1.setMetadata(jsonObject2);
        productVariation1.setQuantityAvailable(2);

        product.setProductVariations(new HashSet<>(Arrays.asList(productVariation1, productVariation)));

        categoryRepository.save(category);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryMetaDataFieldRepository.saveAll(Arrays.asList(categoryMetaDataField, categoryMetaDataField1));
        categoryMetaDataValueRepository.saveAll
                (Arrays.asList(categoryMetaDataValue, categoryMetaDataValue1,
                        categoryMetaDataValue2, categoryMetaDataValue3));
        sellerRepository.save(seller);
    }

}
