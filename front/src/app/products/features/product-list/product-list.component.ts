import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import {CurrencyPipe, NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {RatingModule} from "primeng/rating";
import {CartService} from "../../../cart/data-access/cart.service";
import {MessageService} from "primeng/api";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, NgClass, CurrencyPipe, FormsModule, RatingModule],
  providers: [MessageService],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);
  private readonly messageService = inject(MessageService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  ngOnInit() {
    this.productsService.get().subscribe();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public getInventoryStatusLabel(status: string): string {
    switch (status) {
      case 'INSTOCK':
        return 'En stock';
      case 'LOWSTOCK':
        return 'Stock bas';
      case 'OUTOFSTOCK':
        return 'Épuisé';
      default:
        return status;
    }
  }

  public addToCart(product: Product): void {
    this.cartService.addToCart(product);
    this.messageService.add({
      severity: 'success',
      summary: 'Produit ajouté',
      detail: `${product.name} a été ajouté au panier`,
      life: 3000
    });
  }

  public getProductQuantityInCart(productId: number): number {
    const cartItem = this.cartService.cartItems().find(item => item.product.id === productId);
    return cartItem ? cartItem.quantity : 0;
  }

  public isProductInCart(productId: number): boolean {
    return this.getProductQuantityInCart(productId) > 0;
  }

  public increaseQuantity(product: Product): void {
    this.cartService.addToCart(product, 1);
    this.messageService.add({
      severity: 'success',
      summary: 'Quantité augmentée',
      detail: `Quantité de ${product.name} augmentée dans le panier`,
      life: 2000
    });
  }

  public decreaseQuantity(product: Product): void {
    const currentQuantity = this.getProductQuantityInCart(product.id);
    if (currentQuantity > 1) {
      this.cartService.updateQuantity(product.id, currentQuantity - 1);
      this.messageService.add({
        severity: 'info',
        summary: 'Quantité diminuée',
        detail: `Quantité de ${product.name} diminuée dans le panier`,
        life: 2000
      });
    } else if (currentQuantity === 1) {
      this.cartService.removeFromCart(product.id);
      this.messageService.add({
        severity: 'warn',
        summary: 'Produit retiré',
        detail: `${product.name} a été retiré du panier`,
        life: 2000
      });
    }
  }

  public removeFromCart(product: Product): void {
    this.cartService.removeFromCart(product.id);
    this.messageService.add({
      severity: 'warn',
      summary: 'Produit retiré',
      detail: `${product.name} a été retiré du panier`,
      life: 2000
    });
  }
}
