package gshoes.service;

import gshoes.model.dto.CartDTO;
import gshoes.model.dto.CartDetailDTO;
import gshoes.model.entity.Cart;
import gshoes.repository.ICartRepository;
import gshoes.util.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Override
    public CartDTO getCartDTO(String customerName) {
        if (cartRepository.findByCustomerName(customerName).isPresent()) {
            Cart cart = cartRepository.findByCustomerName(customerName).get();
            return thirdPartyService.convertCartToCartDTO(cart);
        } else {
            return null;
        }
    }

    @Override
    public void addToCartFirstTime(CartDTO cartDTO) {
       thirdPartyService.addToCartFirstTime(cartDTO);
    }

    @Override
    public void updateQuantityOfItemsInCart(CartDTO cartDTO) {
        thirdPartyService.updateQuantityOfItemsInCart(cartDTO);
    }

    @Override
    public void deleteCartItem(String name, String customerName) {
        thirdPartyService.deleteCartItem(name, customerName);
    }

    @Override
    public void addNewItemsInCart(String customerName, List<CartDetailDTO> cartDetailDTOList) {
        thirdPartyService.addNewItemsInCart(customerName, cartDetailDTOList);
    }
}
