package gshoes.service;

import gshoes.model.dto.CartDTO;
import gshoes.model.dto.CartDetailDTO;


import java.util.List;

public interface ICartService {
    CartDTO getCartDTO(String customerName);

    void addToCartFirstTime(CartDTO cartDTO);

    void updateQuantityOfItemsInCart(CartDTO cartDTO);

    void addNewItemsInCart(String customerName, List<CartDetailDTO> cartDetailDTOList);

    void deleteCartItem(String name, String customerName);
}
