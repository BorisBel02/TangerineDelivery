package com.tangerinedelivery.models;
import com.tangerinedelivery.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {
        private int orderNum;
        private UserModel customerInfo;
        private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
        public CartInfo() {

        }
        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public UserModel getCustomerInfo() {
            return customerInfo;
        }

        public void setCustomerInfo(UserModel customerInfo) {
            this.customerInfo = customerInfo;
        }

        public List<CartLineInfo> getCartLines() {
            return this.cartLines;
        }

        private CartLineInfo findLineById(Long id) {
            for (CartLineInfo line : this.cartLines) {
                if (line.getProductInfo().getProductID().equals(id)) {
                    return line;
                }
            }
            return null;
        }

        public void addProduct(ProductEntity productInfo, int quantity) {
            CartLineInfo line = this.findLineById(productInfo.getProductID());

            if (line == null) {
                line = new CartLineInfo();
                line.setQuantity(0);
                line.setProductInfo(productInfo);
                this.cartLines.add(line);
            }
            int newQuantity = line.getQuantity() + quantity;
            if (newQuantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(newQuantity);
            }
        }

        public void validate() {

        }

        public void updateProduct(Long id, int quantity) {
            CartLineInfo line = this.findLineById(id);

            if (line != null) {
                if (quantity <= 0) {
                    this.cartLines.remove(line);
                } else {
                    line.setQuantity(quantity);
                }
            }
        }

        public void removeProduct(ProductEntity productInfo) {
            CartLineInfo line = this.findLineById(productInfo.getProductID());
            if (line != null) {
                this.cartLines.remove(line);
            }
        }

        public boolean isEmpty() {
            return this.cartLines.isEmpty();
        }
//проверка на существование пользователя
//        public boolean isValidCustomer() {
//            return this.customerInfo != null && this.customerInfo.isValid();
//        }

        public int getQuantityTotal() {
            int quantity = 0;
            for (CartLineInfo line : this.cartLines) {
                quantity += line.getQuantity();
            }
            return quantity;
        }

        public double getAmountTotal() {
            double total = 0;
            for (CartLineInfo line : this.cartLines) {
                total += line.getAmount();
            }
            return total;
        }

        public void updateQuantity(CartInfo cartForm) {
            if (cartForm != null) {
                List<CartLineInfo> lines = cartForm.getCartLines();
                for (CartLineInfo line : lines) {
                    this.updateProduct(line.getProductInfo().getProductID(), line.getQuantity());
                }
            }

        }
}
