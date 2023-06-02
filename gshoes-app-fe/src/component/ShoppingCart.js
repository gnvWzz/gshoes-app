import React, { useState, useEffect } from "react";
import axios from "axios";
import shoes from "./shoes.json";
import "../component/style.css"
import nike from "../component/app/assets/nike.png"
import plus from "../component/app/assets/plus.png"
import minus from "../component/app/assets/minus.png"
import trash from "../component/app/assets/trash.png"
import check from "../component/app/assets/check.png"

export default function ShoppingCart() {
    const [cart, setCart] = useState({});
    const [products, setProducts] = useState([]);
    const [cartDetailListLength, setCartDetailListLength] = useState(0);
    const [cartDTO, setCartDTO] = useState({
        customerName: "Quang",
        cartDetailDTOList: [],
        totalPrice: 0
    });
    const [cartDetailDTO, setCartDetailDTO] = useState({
        name: "",
        price: 0,
        img: "",
        quantity: 0
    })
    const [cartDetailDTOs, setCartDetailDTOs] = useState([]);
    const [differentCartDetailDTOs, setDifferentCartDetailDTOs] = useState([]);
    const customerName = {
        customerName: "Quang"
    };
    const [cursor, setCursor] = useState("");
    const [disabled, setDisabled] = useState("false");

    useEffect(() => {
        getCart();
        setProducts(shoes.shoes);
    }, [])

    const getCart = async () => {
        await axios
            .post(`http://localhost:8080/api/cart/`, customerName)
            .then((res) => {
                setCart(res.data);
                setCartDetailListLength(res.data.cartDetailDTOList.length);
            })
            .catch((err) => {
                console.log(err);
            })
    }

    function showProductsList() {
        if (products) {
            return (
                products.map((product) => (
                    <div className="mt-3">
                        <img src={product.image} />
                        <div className="body">
                            <h3>{product.name}</h3>
                            <p className="text">{product.description}</p>
                        </div>
                        <div className="footer">
                            <div style={{ display: "inline-block" }}><h3>$ {product.price}</h3></div>
                            <div style={{ display: "inline-block", marginLeft: 250 }}>
                                <button className="btn btn-warning ml-3 " type="button" onClick={handleAddToCart} value={product.id}>
                                    ADD TO CART
                                </button>
                            </div>
                        </div>
                    </div>
                ))
            )
        } else {
            return (
                undefined
            )
        }
    }

    const updateCartIncreaseQuantity = (e) => {
        const productName = e.currentTarget.getAttribute("value");
        if (cart.cartDetailDTOList) {
            cart.cartDetailDTOList.map((ele) => {
                if (ele.name === productName) {
                    ele.quantity = ele.quantity + 1
                }
            })
            let tempList = [];
            tempList = cart.cartDetailDTOList;
            cartDTO.cartDetailDTOList = tempList;
            axios
                .put(`http://localhost:8080/api/cart/quantity_of_items_in_cart`, cartDTO)
                .then((res) => {
                    getCart();
                    showProductsList();
                    cartDTO.cartDetailDTOList = [];
                })
                .catch((error) => {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                })
        }
    }

    const updateCartDecreaseQuantity = (e) => {
        const productName = e.currentTarget.getAttribute("value");
        if (cart.cartDetailDTOList) {
            cart.cartDetailDTOList.map((ele) => {
                if (ele.name === productName) {
                    if (ele.quantity > 1) {
                        ele.quantity = ele.quantity - 1
                    }
                }
            })
            let tempList = [];
            tempList = cart.cartDetailDTOList;
            cartDTO.cartDetailDTOList = tempList;
            axios
                .put(`http://localhost:8080/api/cart/quantity_of_items_in_cart`, cartDTO)
                .then((res) => {
                    getCart();
                    showProductsList();
                    cartDTO.cartDetailDTOList = [];
                })
                .catch((error) => {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                })
        }
    }

    const handleAddToCart = (e) => {
        const productId = e.currentTarget.getAttribute("value");
        if (cart.cartDetailDTOList) {
            products.map((product) => {
                if (product.id == productId) {
                    cartDetailDTO.name = product.name;
                    cartDetailDTO.price = product.price;
                    cartDetailDTO.img = product.image;
                    cartDetailDTO.quantity = 1
                }
            })
            let tempList = [];
            tempList.push(cartDetailDTO);
            cartDTO.cartDetailDTOList = tempList;
            if (cart.cartDetailDTOList.length < 1) {
                axios
                    .post(`http://localhost:8080/api/cart/first_item_to_cart`, cartDTO)
                    .then((res) => {
                        getCart();
                        showProductsList();
                        cartDTO.cartDetailDTOList = [];
                    })
                    .catch((err) => {
                        console.log(err);
                    })
            } else {
                let productName = "";
                let check = false;
                products.map((product) => {
                    if (product.id == productId) {
                        productName = product.name;
                    }
                })
                cartDTO.cartDetailDTOList.map((ele) => {
                    if (ele.name === productName) {
                        check = true;
                    }
                })
                if (check) {
                    products.map((product) => {
                        if (product.id == productId) {
                            cartDetailDTO.name = product.name;
                            cartDetailDTO.price = product.price;
                            cartDetailDTO.img = product.image;
                            cartDetailDTO.quantity = 1
                        }
                    })
                    let tempList = [];
                    tempList.push(cartDetailDTO);
                    const cartDetailDTOListAndCustomerName = {
                        cartDetailDTOList: tempList,
                        customerName: customerName.customerName
                    }
                    axios
                        .put(`http://localhost:8080/api/cart/new_items_to_cart`, cartDetailDTOListAndCustomerName)
                        .then((res) => {
                            getCart();
                            showProductsList();
                            cartDTO.cartDetailDTOList = [];
                        })
                        .catch((err) => {
                            console.log(err);
                        })
                }
            }
        }
    }

    const handleDeleteCartItem = (e) => {
        const productNameAndCustomerName = {
            customerName: "Quang",
            name: e.currentTarget.getAttribute("value"),
        }
        axios
            .post(`http://localhost:8080/api/cart/removal`, productNameAndCustomerName)
            .then((res) => {
                getCart();
                showProductsList();
            })
            .catch((err) => {
                console.log(err);
            })
    }

    const handleChangeCursorOver = () => {
        setCursor("pointer")
    }

    const showEmptyCart = () => {
        if (cartDetailListLength == 0) {
            return (
                <div>
                    <p>Your cart is empty</p>
                </div>
            )
        }
    }

    function showCart() {
        if (cart) {
            return (
                <div className="cart-detail">
                    <table>
                        {cart.cartDetailDTOList ? cart.cartDetailDTOList.map((ele) => (
                            <>
                                <tr className="mt-3">
                                    <td rowSpan={4}>
                                        <img className="img-cart" src={ele.img}></img>
                                    </td>
                                    <td rowSpan={2}>
                                        <h4 className="ml-3">{ele.name}</h4>
                                    </td>
                                </tr>
                                <tr>
                                </tr>
                                <tr>
                                    <td>
                                        <h3 className="ml-3">$ {ele.price}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div className="ml-3">
                                            <button className="button-quantity btn btn-warning rounded" type="button" style={{ cursor: cursor }} onMouseOver={handleChangeCursorOver} onClick={updateCartDecreaseQuantity} value={ele.name}>
                                                <img className="image-quantity" src={minus} />
                                            </button>
                                            <p className="ml-3 mr-3" style={{ display: "inline-block" }}>{ele.quantity}</p>
                                            <button className="button-quantity btn btn-warning rounded" type="button" style={{ cursor: cursor }} onClick={updateCartIncreaseQuantity} value={ele.name}>
                                                <img className="image-quantity" src={plus} />
                                            </button>
                                            <button className="button-trash btn btn-warning rounded" type="button" style={{ cursor: cursor }} onMouseOver={handleChangeCursorOver} onClick={handleDeleteCartItem} value={ele.name}>
                                                <img className="image-trash" src={trash} />
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </>
                        )) : undefined}
                    </table>
                </div>
            )
        } else {
            return (
                undefined
            )
        }
    }

    return (
        <div className="container">
            <div className="row justify-content">
                <div className="col-xl-6 shopping-cart pr-2">
                    <div className="header-products">
                        <img className="nike" src={nike} />
                        <h3>Our products</h3>
                    </div>
                    <div className="scrollable-content">
                        {showProductsList()}
                    </div>
                </div>
                <div className="col-xl-6 shopping-cart pl-2">
                    <div>
                        <img className="nike" src={nike} />
                    </div>
                    <div className="header-products" style={{ paddingTop: 30 }}>
                        <div style={{ display: "inline-block" }}>
                            <h3>Your cart</h3>
                        </div>
                        <div style={{ display: "inline-block", marginLeft: 300 }}>
                            <h3>$ {cart.totalPrice ? cart.totalPrice.toFixed(2) : 0.0}</h3>
                        </div>
                    </div>
                    <div className="scrollable-content">
                        {showCart()}
                        {showEmptyCart()}
                    </div>
                </div>
            </div>
        </div>
    )
}