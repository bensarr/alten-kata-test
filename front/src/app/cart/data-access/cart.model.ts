import {Product} from "../../products/data-access/product.model";

export interface CartModel {
  product: Product;
  quantity: number;
}
