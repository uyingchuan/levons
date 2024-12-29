import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormItemComponent } from '@levons/ng';

@Component({
  selector: 'app-layout',
  imports: [CommonModule, RouterLink, FormItemComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss',
  standalone: true,
})
export class LayoutComponent {}
