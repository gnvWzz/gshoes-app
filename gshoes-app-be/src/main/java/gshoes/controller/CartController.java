package gshoes.controller;

import gshoes.model.CartDetailDTOListAndCustomerName;
import gshoes.model.ProductNameAndCustomerName;
import gshoes.model.dto.CartDTO;
import gshoes.model.entity.CustomerName;
import gshoes.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/")
    public ResponseEntity<?> showCart(@RequestBody CustomerName customerName) {
        try {
            CartDTO cartDTO = cartService.getCartDTO(customerName.getCustomerName());
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/first_item_to_cart")
    public ResponseEntity<?> addToCartFirstTime(@RequestBody CartDTO cartDTO) {
        try {
            cartService.addToCartFirstTime(cartDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/quantity_of_items_in_cart")
    public ResponseEntity<?> updateQuantityOfItemsInCart(@RequestBody CartDTO cartDTO) {
        try {
            cartService.updateQuantityOfItemsInCart(cartDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/new_items_to_cart")
    public ResponseEntity<?> addNewItemsInCart(@RequestBody CartDetailDTOListAndCustomerName cartDetailDTOListAndCustomerName) {
        try {
            cartService.addNewItemsInCart(cartDetailDTOListAndCustomerName.getCustomerName(), cartDetailDTOListAndCustomerName.getCartDetailDTOList());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/removal")
    public ResponseEntity<?> deleteCartItem(@RequestBody ProductNameAndCustomerName productNameAndCustomerName) {
        try {
            cartService.deleteCartItem(productNameAndCustomerName.getName(), productNameAndCustomerName.getCustomerName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
