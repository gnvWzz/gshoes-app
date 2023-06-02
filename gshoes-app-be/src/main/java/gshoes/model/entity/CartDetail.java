package gshoes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity(name = "cart_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    private Cart cart;

    private String img;

    private String name;

    private Double price;

    private Long quantity;

    public CartDetail(Cart cart, String img, String name, Double price, Long quantity) {
        this.cart = cart;
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
