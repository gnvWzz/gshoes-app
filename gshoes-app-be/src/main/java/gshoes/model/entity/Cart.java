package gshoes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<CartDetail> cartDetailList;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "total_price")
    private Double totalPrice;

    public Cart(List<CartDetail> cartDetailList, String customerName, Double totalPrice) {
        this.cartDetailList = cartDetailList;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }
}
