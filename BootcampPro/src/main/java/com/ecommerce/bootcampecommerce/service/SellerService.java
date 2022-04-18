package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.dto.SellerDTO;
import com.ecommerce.bootcampecommerce.entity.Seller;
import com.ecommerce.bootcampecommerce.entity.User;
import java.util.List;

public interface SellerService {
    
    public List<SellerDTO> getSellers();

    SellerDTO sellerConvertEntityToDto(Seller Seller);

    Seller sellerConvertDtoToEntity(SellerDTO SellerDTO);

    public SellerDTO saveSeller(SellerDTO SellerDTO);
    
    void sendEmail(User user);
    
}
