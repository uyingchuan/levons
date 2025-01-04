import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormControlComponent } from '@levons/ng';
import { HttpClient } from '@angular/common/http';

@Component({
  imports: [RouterModule, FormControlComponent],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  standalone: true,
})
export class AppComponent {
  title = 'admin';

  constructor(private httpClient: HttpClient) {}

  getData() {
    this.httpClient.get('');
  }
}
