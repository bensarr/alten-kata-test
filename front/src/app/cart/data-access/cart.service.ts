import { Injectable, signal } from '@angular/core';
import {CartModel} from "./cart.model";
import {Product} from "../../products/data-access/product.model";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private readonly _cartItems = signal<CartModel[]>([]);

  public readonly cartItems = this._cartItems.asReadonly();

  public get totalItems(): number {
    return this._cartItems().reduce((total, item) => total + item.quantity, 0);
  }

  public get totalPrice(): number {
    return this._cartItems().reduce((total, item) => total + (item.product.price * item.quantity), 0);
  }

  public addToCart(product: Product, quantity: number = 1): void {
    this._cartItems.update(items => {
      const existingItemIndex = items.findIndex(item => item.product.id === product.id);

      if (existingItemIndex >= 0) {
        const newItems = [...items];
        newItems[existingItemIndex] = {
          ...newItems[existingItemIndex],
          quantity: newItems[existingItemIndex].quantity + quantity
        };
        return newItems;
      } else {
        return [...items, { product, quantity }];
      }
    });
  }

  public removeFromCart(productId: number): void {
    this._cartItems.update(items => items.filter(item => item.product.id !== productId));
  }

  public updateQuantity(productId: number, quantity: number): void {
    if (quantity <= 0) {
      this.removeFromCart(productId);
      return;
    }

    this._cartItems.update(items => {
      const existingItemIndex = items.findIndex(item => item.product.id === productId);

      if (existingItemIndex >= 0) {
        const newItems = [...items];
        newItems[existingItemIndex] = {
          ...newItems[existingItemIndex],
          quantity
        };
        return newItems;
      }

      return items;
    });
  }

  public clearCart(): void {
    this._cartItems.set([]);
  }
}
