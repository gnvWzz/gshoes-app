package gshoes.util;

import gshoes.model.dto.CartDTO;
import gshoes.model.dto.CartDetailDTO;
import gshoes.model.entity.Cart;
import gshoes.model.entity.CartDetail;
import gshoes.repository.ICartDetailRepository;
import gshoes.repository.ICartRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdPartyService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ICartDetailRepository cartDetailRepository;

    public CartDTO convertCartToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(cart, cartDTO);
        List<CartDetail> cartDetailList = cart.getCartDetailList();
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
        for (CartDetail cartDetail: cartDetailList) {
            CartDetailDTO cartDetailDTO = new CartDetailDTO();
            BeanUtils.copyProperties(cartDetail, cartDetailDTO);
            cartDetailDTOList.add(cartDetailDTO);
        }
        cartDTO.setCartDetailDTOList(cartDetailDTOList);
        return cartDTO;
    }

    public void addToCartFirstTime(CartDTO cartDTO) {
        String customerName = cartDTO.getCustomerName();
        if (cartRepository.findByCustomerName(customerName).isPresent()) {
            Cart cart = cartRepository.findByCustomerName(customerName).get();
            if (cart.getCartDetailList().isEmpty()) {
                List<CartDetailDTO> cartDetailDTOList = cartDTO.getCartDetailDTOList();
                List<CartDetail> newCartDetailList = new ArrayList<>();
                Double totalPrice = 0.0;
                for (CartDetailDTO cartDetailDTO: cartDetailDTOList) {
                    CartDetail cartDetail = new CartDetail();
                    BeanUtils.copyProperties(cartDetailDTO, cartDetail);
                    cartDetail.setCart(cart);
                    newCartDetailList.add(cartDetail);
                    Double subTotal = cartDetailDTO.getPrice() * cartDetailDTO.getQuantity();
                    totalPrice += subTotal;
                }
                cart.setTotalPrice(totalPrice);
                cart.setCartDetailList(newCartDetailList);
                cartRepository.save(cart);
            }
        }
    }

    public void updateQuantityOfItemsInCart(CartDTO cartDTO) {
        String customerName = cartDTO.getCustomerName();
        if (cartRepository.findByCustomerName(customerName).isPresent()) {
            Cart cart = cartRepository.findByCustomerName(customerName).get();
            List<CartDetail> cartDetailList = cart.getCartDetailList();
            Double totalPrice = 0.0;
            if (!cart.getCartDetailList().isEmpty()) {
                List<CartDetailDTO> cartDetailDTOList = cartDTO.getCartDetailDTOList();
                for (CartDetail cartDetail: cartDetailList) {
                    for (CartDetailDTO cartDetailDTO : cartDetailDTOList) {
                        if (cartDetail.getName().equals(cartDetailDTO.getName())) {
                            cartDetail.setQuantity(cartDetailDTO.getQuantity());
                        }
                    }
                    Double subTotal = cartDetail.getPrice() * cartDetail.getQuantity();
                    totalPrice += subTotal;
                }
                cart.setTotalPrice(totalPrice);
                cartRepository.save(cart);
            }
        }
    }

    public void addNewItemsInCart(String customerName, List<CartDetailDTO> cartDetailDTOList) {
        if (cartRepository.findByCustomerName(customerName).isPresent()) {
            Cart cart = cartRepository.findByCustomerName(customerName).get();
            List<CartDetail> cartDetailList = cart.getCartDetailList();
            List<CartDetail> tempCartDetailList = new ArrayList<>();
            for (CartDetailDTO cartDetailDTO: cartDetailDTOList) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                BeanUtils.copyProperties(cartDetailDTO, cartDetail);
                tempCartDetailList.add(cartDetail);
            }
            List<CartDetail> newCartDetailList = new ArrayList<>();
            newCartDetailList.addAll(cartDetailList);
            newCartDetailList.addAll(tempCartDetailList);
            Double totalPrice = 0.0;
            for (CartDetail cartDetail: newCartDetailList) {
                Double subTotal = cartDetail.getPrice() * cartDetail.getQuantity();
                totalPrice += subTotal;
            }
            cart.setCartDetailList(newCartDetailList);
            cart.setTotalPrice(totalPrice);
            cartRepository.save(cart);
        }
    }

    public void deleteCartItem(String name, String customerName) {
        if (cartRepository.findByCustomerName(customerName).isPresent()) {
            Cart cart = cartRepository.findByCustomerName(customerName).get();
            long cartId = cart.getId();
            Double totalPrice = 0.0;
            cartDetailRepository.deleteByCart_IdAndName(cartId, name);
            for (CartDetail cartDetail: cart.getCartDetailList()) {
                Double subTotal = cartDetail.getPrice() * cartDetail.getQuantity();
                totalPrice += subTotal;
            }
            cart.setTotalPrice(totalPrice);
            cartRepository.save(cart);
        }
    }
}
