import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { CartService } from 'app/cart/data-access/cart.service';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-cart-icon',
  templateUrl: './cart-icon.component.html',
  styleUrls: ['./cart-icon.component.scss'],
  standalone: true,
  imports: [CommonModule, BadgeModule, OverlayPanelModule, ButtonModule, TableModule]
})
export class CartIconComponent {
  public readonly cartService = inject(CartService);
}
