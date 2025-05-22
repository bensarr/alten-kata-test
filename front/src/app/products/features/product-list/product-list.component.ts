import {Component, OnInit, inject, signal, computed} from "@angular/core";
import {Product} from "app/products/data-access/product.model";
import {ProductsService} from "app/products/data-access/products.service";
import {ProductFormComponent} from "app/products/ui/product-form/product-form.component";
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {DataViewModule} from 'primeng/dataview';
import {DialogModule} from 'primeng/dialog';
import {CurrencyPipe, NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {RatingModule} from "primeng/rating";
import {CartService} from "../../../cart/data-access/cart.service";
import {MessageService} from "primeng/api";
import {FilterOptions} from "../../../shared/data-access/filter.options";
import {DropdownModule} from "primeng/dropdown";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";

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
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, NgClass, CurrencyPipe, FormsModule, RatingModule,
    PaginatorModule,
    InputTextModule,
    DropdownModule],
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

  public readonly currentPage = signal(0);
  public readonly itemsPerPage = signal(12);
  public readonly totalItems = computed(() => this.filteredProducts().length);

  public readonly filters = signal<FilterOptions>({
    searchText: '',
    selectedCategory: '',
    selectedInventoryStatus: '',
    minPrice: null,
    maxPrice: null
  });

  public readonly categoryOptions = computed(() => {
    const categories = [...new Set(this.products().map(p => p.category))];
    return [
      {label: 'Toutes les catégories', value: ''},
      ...categories.map(cat => ({label: cat, value: cat}))
    ];
  });

  public readonly inventoryStatusOptions = [
    {label: 'Tous les statuts', value: ''},
    {label: 'En stock', value: 'INSTOCK'},
    {label: 'Stock bas', value: 'LOWSTOCK'},
    {label: 'Épuisé', value: 'OUTOFSTOCK'}
  ];

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

  public readonly filteredProducts = computed(() => {
    const allProducts = this.products();
    const currentFilters = this.filters();

    return allProducts.filter(product => {
      if (currentFilters.searchText) {
        const searchLower = currentFilters.searchText.toLowerCase();
        const matchesSearch =
          product.name.toLowerCase().includes(searchLower) ||
          product.description.toLowerCase().includes(searchLower) ||
          product.code.toLowerCase().includes(searchLower) ||
          product.category.toLowerCase().includes(searchLower);

        if (!matchesSearch) return false;
      }

      if (currentFilters.selectedCategory && product.category !== currentFilters.selectedCategory) {
        return false;
      }

      if (currentFilters.selectedInventoryStatus && product.inventoryStatus !== currentFilters.selectedInventoryStatus) {
        return false;
      }

      if (currentFilters.minPrice !== null && product.price < currentFilters.minPrice) {
        return false;
      }

      return !(currentFilters.maxPrice !== null && product.price > currentFilters.maxPrice);


    });
  });

  public readonly paginatedProducts = computed(() => {
    const filtered = this.filteredProducts();
    const page = this.currentPage();
    const itemsPerPage = this.itemsPerPage();
    const startIndex = page * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;

    return filtered.slice(startIndex, endIndex);
  });

  public onPageChange(event: any) {
    this.currentPage.set(event.page);
  }

  public onSearchChange(searchText: string) {
    this.filters.update(filters => ({ ...filters, searchText }));
    this.currentPage.set(0); // Reset à la première page
  }

  public onCategoryChange(selectedCategory: string) {
    this.filters.update(filters => ({ ...filters, selectedCategory }));
    this.currentPage.set(0);
  }

  public onInventoryStatusChange(selectedInventoryStatus: string) {
    this.filters.update(filters => ({ ...filters, selectedInventoryStatus }));
    this.currentPage.set(0);
  }

  public onMinPriceChange(minPrice: number | null) {
    this.filters.update(filters => ({ ...filters, minPrice }));
    this.currentPage.set(0);
  }

  public onMaxPriceChange(maxPrice: number | null) {
    this.filters.update(filters => ({ ...filters, maxPrice }));
    this.currentPage.set(0);
  }

  public clearFilters() {
    this.filters.set({
      searchText: '',
      selectedCategory: '',
      selectedInventoryStatus: '',
      minPrice: null,
      maxPrice: null
    });
    this.currentPage.set(0);
  }

  protected readonly Math = Math;
}
