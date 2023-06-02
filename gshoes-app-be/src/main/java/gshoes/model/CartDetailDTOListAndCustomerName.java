package gshoes.model;

import gshoes.model.dto.CartDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTOListAndCustomerName {
    private String customerName;
    private List<CartDetailDTO> cartDetailDTOList;
}
