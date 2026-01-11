import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Product} from "../../model/product";
import {ProductService} from "../../services/product/product.service";
import {AsyncPipe, JsonPipe} from "@angular/common";
import {Router} from "@angular/router";
import {Order} from "../../model/order";
import {FormsModule} from "@angular/forms";
import {OrderService} from "../../services/order/order.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  products: Array<Product> = [];
  quantityIsNull = false;
  orderSuccess = false;
  orderFailed = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        this.productService.getProducts()
          .pipe()
          .subscribe(product => {
            this.products = product;
            console.log('Products received:', this.products);
          })
      }
    )
  }

  goToCreateProductPage() {
    this.router.navigateByUrl('/add-product');
  }

  validateQuantity(quantity: string): boolean {
    const parsedQuantity = Number(quantity);
    return !isNaN(parsedQuantity) && parsedQuantity > 0;
  }

  orderProduct(product: Product, quantity: string) {
    console.log('Product received:', product);
    console.log('Quantity:', quantity);

    if (!this.validateQuantity(quantity)) {
      this.orderFailed = true;
      this.orderSuccess = false;
      this.quantityIsNull = true;
      console.error('Invalid quantity:', quantity);
      return;
    }

    if (!product.id || !product.name) {
      console.error('Product ID or name is missing:', product);
      this.orderFailed = true;
      this.orderSuccess = false;
      return;
    }

    this.oidcSecurityService.userData$.subscribe(result => {
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.firstName,
        lastName: result.userData.lastName
      };

      const order: Order = {
        id: product.id,
        price: product.price,
        quantity: Number(quantity),
        userDetails: userDetails,
        productId: product.id as string,
        productName: product.name as string,
      };

      console.log('Order object:', order);

      this.orderService.orderProduct(order).subscribe(() => {
        this.orderSuccess = true;
        this.orderFailed = false;
      }, error => {
        this.orderFailed = true;
        this.orderSuccess = false;
        console.error('Order failed:', error);
      });
    });
  }
}
